package com.alethio.orderservice;

import com.alethio.orderservice.config.AppConfig;
import com.alethio.orderservice.exceptions.APIException;
import com.alethio.orderservice.exceptions.ResourceNotAvailableException;
import com.alethio.orderservice.model.ContactInfo;
import com.alethio.orderservice.model.Currency;
import com.alethio.orderservice.model.OperationCountry;
import com.alethio.orderservice.model.Order;
import com.alethio.orderservice.model.OrderItem;
import com.alethio.orderservice.model.dto.ContactInfoDTO;
import com.alethio.orderservice.model.dto.ItemDTO;
import com.alethio.orderservice.model.dto.OrderDTO;
import com.alethio.orderservice.model.dto.SaleCountryDTO;
import com.alethio.orderservice.repository.ItemSaleCountryRepository;
import com.alethio.orderservice.repository.OrderItemRepository;
import com.alethio.orderservice.repository.OrderRepository;
import com.alethio.orderservice.service.CopyBeanService;
import com.alethio.orderservice.service.CurrencyConversionService;
import com.alethio.orderservice.service.ItemWarehouseService;
import com.alethio.orderservice.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class OrderServiceApplicationTests {
	@InjectMocks
	private OrderService orderService = new OrderService();

	@Mock
	private ItemSaleCountryRepository itemSaleCountryRepository;

	@Mock
	private ItemWarehouseService warehouseService;

	@Mock
	private AppConfig appConfig;

	@Mock
	private OrderRepository orderRepository;

	@Spy
	private CopyBeanService copyBeanService;

	@Mock
	private CurrencyConversionService currencyConversionService;

	@Mock
	private OrderItemRepository orderItemRepository;


	@Test
	void testCreateOrderSuccess() throws APIException, ResourceNotAvailableException {
		OrderDTO orderDTO = createOrderRequest();
		Mockito.when(warehouseService.getItem(Mockito.any(), Mockito.anyInt())).thenReturn(getItemFromWarehouseService());
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(updateStockAndGetOrder(50));
		Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(getOrderITem());
		Mockito.when(warehouseService.updateItemStock(Mockito.any(), Mockito.anyInt(), Mockito.anyLong())).thenReturn(getItemFromWarehouseService());
		OrderDTO orderActual = orderService.createOrder(orderDTO);
		Assertions.assertEquals(orderDTO.getContactInfoDTO(), orderActual.getContactInfoDTO());
		Assertions.assertEquals(1, orderDTO.getOrderId());
	}

	@Test
	void testCreateOrderSuccessWithUpdatedStock() throws APIException, ResourceNotAvailableException {
		OrderDTO orderDTO = createOrderRequest();
		ItemDTO itemDTO = orderDTO.getItems().get(0);
		itemDTO.setQuantity(50L);
		ItemDTO item = getItemFromWarehouseService();
		item.setQuantity(55L);
		Mockito.when(warehouseService.getItem(Mockito.any(), Mockito.anyInt())).thenReturn(item);
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(updateStockAndGetOrder(50));
		Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(getOrderITem());
		ArgumentCaptor<List<ItemDTO>> updatedItemCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.doNothing().when(warehouseService).updateItemsStock(Mockito.any(), updatedItemCaptor.capture());
		OrderDTO orderActual = orderService.createOrder(orderDTO);
		Assertions.assertEquals(orderDTO.getContactInfoDTO(), orderActual.getContactInfoDTO());
		Assertions.assertEquals(1, orderDTO.getOrderId());
		Assertions.assertEquals(5L, updatedItemCaptor.getValue().get(0).getQuantity());
	}

	@Test
	void testCreateOrderSuccessWithUpdatStockWhenLessThanThreshold() throws APIException, ResourceNotAvailableException {
		OrderDTO orderDTO = createOrderRequest();
		ItemDTO itemDTO = orderDTO.getItems().get(0);
		itemDTO.setQuantity(50L);
		ItemDTO item = getItemFromWarehouseService();
		item.setQuantity(55L);
		Mockito.when(warehouseService.getItem(Mockito.any(), Mockito.anyInt())).thenReturn(item);
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(updateStockAndGetOrder(50));
		Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(getOrderITem());
		Mockito.when(appConfig.getThresholdStock()).thenReturn(10L);
		Mockito.when(appConfig.getReplStock()).thenReturn(100L);
		ArgumentCaptor<List<ItemDTO>> updatedItemCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.doNothing().when(warehouseService).updateItemsStock(Mockito.any(), updatedItemCaptor.capture());
		OrderDTO orderActual = orderService.createOrder(orderDTO);
		Assertions.assertEquals(orderDTO.getContactInfoDTO(), orderActual.getContactInfoDTO());
		Assertions.assertEquals(1, orderDTO.getOrderId());
		Assertions.assertEquals(105L, updatedItemCaptor.getValue().get(0).getQuantity());
	}

	@Test
	void testCreateOrderFailedWhenWareHouseServiceFails() throws APIException, ResourceNotAvailableException {
		OrderDTO orderDTO = createOrderRequest();
		Mockito.when(warehouseService.getItem(Mockito.any(), Mockito.anyInt())).thenReturn(getItemFromWarehouseService());
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(updateStockAndGetOrder(50));
		Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(getOrderITem());
		Mockito.doThrow(APIException.class).when(warehouseService).updateItemsStock(Mockito.any(),Mockito.any());
		Assertions.assertThrows(APIException.class, () -> orderService.createOrder(orderDTO));
	}

	@Test
	void testCreateOrderFailedWhenItemNotAvailable() throws APIException {
		OrderDTO orderDTO = createOrderRequest();
		Mockito.when(warehouseService.getItem(Mockito.any(), Mockito.anyInt())).thenThrow(APIException.class);
		Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(updateStockAndGetOrder(50));
		Mockito.when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(getOrderITem());
		Mockito.when(warehouseService.updateItemStock(Mockito.any(), Mockito.anyInt(), Mockito.anyLong())).thenReturn(getItemFromWarehouseService());
		Assertions.assertThrows(APIException.class, () -> orderService.createOrder(orderDTO));
	}

	private OrderDTO createOrderRequest() {
		OrderDTO orderDTO = new OrderDTO();

		ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
		contactInfoDTO.setContactEmail("test@gmail.com");
		contactInfoDTO.setContactName("Test");
		contactInfoDTO.setCountry(OperationCountry.SGP);
		contactInfoDTO.setMobile("+0012345678");
		orderDTO.setContactInfoDTO(contactInfoDTO);

		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemType("food");
		itemDTO.setQuantity(10L);
		itemDTO.setId(1);
		orderDTO.setItems(List.of(itemDTO));
		return orderDTO;
	}

	private ItemDTO getItemFromWarehouseService() {
		ItemDTO itemDTO = new ItemDTO();
		itemDTO.setItemType("food");
		itemDTO.setQuantity(10L);
		itemDTO.setItemPrice(5D);
		itemDTO.setCurrencyName(Currency.SGD);
		itemDTO.setMfgCountry(OperationCountry.SGP.getName());
		SaleCountryDTO saleCountryDTO = new SaleCountryDTO();
		saleCountryDTO.setId(1);
		saleCountryDTO.setOperationCountry(OperationCountry.SGP);
		itemDTO.setSaleCountries(Set.of(saleCountryDTO));
		itemDTO.setId(1);
		return itemDTO;
	}

	private Order updateStockAndGetOrder(long qty) {
		Order order = new Order();

		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setContactEmail("test@gmail.com");
		contactInfo.setContactName("Test");
		contactInfo.setCountry(OperationCountry.SGP);
		contactInfo.setMobile("+0012345678");
		order.setContactInfo(contactInfo);
		order.setId(1);

		OrderItem item = new OrderItem();
		item.setItemType("food");
		item.setQty(10L + qty);
		item.setId(1);
		item.setExpectedArrivalDate("2023-20-01");
		order.setItems(Set.of(item));
		return order;
	}

	public OrderItem getOrderITem() {
		OrderItem item = new OrderItem();
		item.setItemType("food");
		item.setQty(10L);
		item.setId(1);
		item.setExpectedArrivalDate("2023-20-01");
		return item;
	}

}

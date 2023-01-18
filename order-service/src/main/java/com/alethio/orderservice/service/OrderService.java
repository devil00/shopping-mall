package com.alethio.orderservice.service;

import com.alethio.orderservice.config.AppConfig;
import com.alethio.orderservice.exceptions.APIException;
import com.alethio.orderservice.exceptions.ResourceNotAvailableException;
import com.alethio.orderservice.model.ItemType;
import com.alethio.orderservice.model.OperationCountry;
import com.alethio.orderservice.model.Order;
import com.alethio.orderservice.model.OrderItem;
import com.alethio.orderservice.model.dto.ItemDTO;
import com.alethio.orderservice.model.dto.OrderDTO;
import com.alethio.orderservice.model.dto.SaleCountryDTO;
import com.alethio.orderservice.repository.ItemSaleCountryRepository;
import com.alethio.orderservice.repository.OrderItemRepository;
import com.alethio.orderservice.repository.OrderRepository;
import com.alethio.orderservice.utils.CurrencyConverterUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Service
@Slf4j
public class OrderService {
    @Autowired
    private ItemSaleCountryRepository itemSaleCountryRepository;

    @Autowired
    private ItemWarehouseService warehouseService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CopyBeanService copyBeanService;

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @Autowired
    private OrderItemRepository orderItemRepository;

//    public OrderService(ItemSaleCountryRepository itemSaleCountryRepository) {
//        this.itemSaleCountryRepository = itemSaleCountryRepository;
//    }


    /**
     * Prepare and create order and reduce the item stock by the requested quantity.
     * @param orderRequest {@link OrderDTO} order request
     * @return order response.
     */
    @Transactional(rollbackFor = {ResourceNotAvailableException.class, APIException.class})
    public OrderDTO createOrder(OrderDTO orderRequest) throws ResourceNotAvailableException, APIException {
        Map<ItemType, List<ItemDTO>> itemsToUpdateByType = prepareOrder(orderRequest);

        Order order = copyBeanService.toOrderEntity(orderRequest);
        log.debug("Saving order to DB for contact-name {}", order.getContactInfo().getContactName());
        Set<OrderItem> orderItems = order.getItems();
        order.setItems(null);
        order = orderRepository.save(order);
        orderRequest.setOrderId(order.getId());
        saveOrderItems(orderItems, order);

        if(!Objects.isNull(order.getId())) {
            log.info("Order Successfully created");
            if(!CollectionUtils.isEmpty(itemsToUpdateByType)) {
                log.debug("Updating item stock");
                updateItemStock(itemsToUpdateByType);
            }
        } else {
            log.debug("Order not created, hence skipping item stock update for order contact-name {}", orderRequest.getContactInfoDTO().getContactName());
        }

        return orderRequest;
    }

    private Map<ItemType, List<ItemDTO>> prepareOrder(OrderDTO orderRequest) throws APIException, ResourceNotAvailableException {
        log.info("Preparing order for {}", orderRequest.getContactInfoDTO().getContactName());
        Map<ItemType, List<ItemDTO>> itemsToUpdateByType = new HashMap<>();
        double totalPrice = 0D;
        for(ItemDTO requestedItem : orderRequest.getItems()) {
            ItemDTO itemDb = warehouseService.getItem(ItemType.findItemTypeByName(requestedItem.getItemType()), requestedItem.getId());
            if(Objects.isNull(itemDb) || Objects.requireNonNullElse(itemDb.getQuantity(), 0).longValue() < Objects.requireNonNullElse(requestedItem.getQuantity(), 0).longValue()) {
                throw new ResourceNotAvailableException(String.format("Item with id %s is not available with item-type %s", requestedItem.getId(), requestedItem.getItemType()));
            }
            ItemDTO item = itemDb;
            OperationCountry orderCountry = orderRequest.getContactInfoDTO().getCountry();

            if(item.getSaleCountries().stream().map(SaleCountryDTO::getOperationCountry).noneMatch(operationCountry -> operationCountry == orderCountry)) {
                throw new ResourceNotAvailableException(String.format("Item with id %s is not available for item-type: %s", requestedItem.getId(), requestedItem.getItemType()));
            }

            requestedItem.setPrice(CurrencyConverterUtil.generateCurrencyStr(item.getItemPrice(), item.getCurrencyName().name()));
            requestedItem.setItemName(item.getItemName());
            ItemType itemType = ItemType.findItemTypeByName(requestedItem.getItemType());
            totalPrice += (requestedItem.getQuantity() * currencyConversionService.convertToCurrency(item.getItemPrice(), item.getCurrencyName(), orderCountry.getCurrency()));
            item.setQuantity(item.getQuantity() - requestedItem.getQuantity());
            log.debug("Item with {}, stock  updated {}", item.getId(), item.getQuantity());

            List<ItemDTO> itemsToUpdate = itemsToUpdateByType.getOrDefault(ItemType.findItemTypeByName(requestedItem.getItemType()), new ArrayList<>());
            itemsToUpdate.add(item);
            itemsToUpdateByType.put(itemType, itemsToUpdate);

            if(item.getQuantity() <= appConfig.getThresholdStock()) {
                log.info("Found item less than the threshold, hence revising stock for item {} type-{} ", item.getItemName(), item.getItemType());
                item.setQuantity(item.getQuantity() + appConfig.getReplStock());
            }

            setExpectedOrderArrivalDate(requestedItem, item.getMfgCountry(), orderRequest.getContactInfoDTO().getCountry().getName(),
                    orderRequest.getOrderDate());
        }

        orderRequest.setTotalPrice(String.format("%s %s", totalPrice, orderRequest.getContactInfoDTO().getCountry().getCurrency().name()));
        log.info("Successfully prepared order for {} ", orderRequest.getContactInfoDTO().getContactName());
        return itemsToUpdateByType;
    }


    private void saveOrderItems(Set<OrderItem> items, Order order) {
        if(CollectionUtils.isEmpty(items) || Objects.isNull(order)) {
            log.info("Skip saving order-items since no items are available  for order {} or order is null", order.getId());
            return;
        }

        items.forEach(item -> {
            item.setOrder(order);
            orderItemRepository.save(item);
        });
    }

    private void setExpectedOrderArrivalDate(final ItemDTO item, String mfgCountry, String orderedCountry, long orderDateInEpochMs) {
        if(!StringUtils.hasLength(mfgCountry) || !StringUtils.hasLength(orderedCountry)) {
            throw new IllegalArgumentException("No valid country found to calculate arrival date");
        }

        if(mfgCountry.equals(orderedCountry)) {
            item.setExpectedArrivalDate(OperationCountry.fromCountryName(orderedCountry).getDateInTz(orderDateInEpochMs + TimeUnit.DAYS.toMillis(appConfig.getSameCountryExpectedArrivalDays())));
        } else {
            item.setExpectedArrivalDate(OperationCountry.fromCountryName(orderedCountry).getDateInTz(orderDateInEpochMs + TimeUnit.DAYS.toMillis(appConfig.getDiffCountryExpectedArrivalDays())));
        }
    }

    private void updateItemStock(Map<ItemType, List<ItemDTO>> itemsToUpdateByType) throws APIException {
        for(Map.Entry<ItemType, List<ItemDTO>> itemEntry : itemsToUpdateByType.entrySet()) {
            warehouseService.updateItemsStock(itemEntry.getKey(), itemEntry.getValue());
        }
    }

    public OrderDTO getOrder(Integer orderId) throws ResourceNotAvailableException {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotAvailableException(String.format("Order not found for the given id %s", orderId)));
            return copyBeanService.toOrderDTO(order);
        } catch (Exception ex) {
            log.error("Error getting order with id {}", orderId);
            throw ex;
        }
    }
}

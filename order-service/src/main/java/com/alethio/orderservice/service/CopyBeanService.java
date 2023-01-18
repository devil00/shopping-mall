package com.alethio.orderservice.service;

import com.alethio.orderservice.model.ContactInfo;
import com.alethio.orderservice.model.Order;
import com.alethio.orderservice.model.OrderItem;
import com.alethio.orderservice.model.dto.ContactInfoDTO;
import com.alethio.orderservice.model.dto.ItemDTO;
import com.alethio.orderservice.model.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CopyBeanService {

    public Order toOrderEntity(OrderDTO orderDTO) {
        log.info("Converting to order for request {}", orderDTO);
        Order order = new Order();
        setContactInfo(order, orderDTO.getContactInfoDTO());
        setOrderItem(order, orderDTO.getItems());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotalPrice(orderDTO.getTotalPrice());
        return order;
    }

    private void setContactInfo(Order order, ContactInfoDTO contactInfoDTO) {
        ContactInfo contactInfo = new ContactInfo();
        BeanUtils.copyProperties(contactInfoDTO, contactInfo);
        order.setContactInfo(contactInfo);
        contactInfo.setOrder(order);
        order.setContactInfo(contactInfo);
    }

    private void setOrderItem(Order order, List<ItemDTO> itemDTOSet) {
        for(ItemDTO itemDTO : itemDTOSet) {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(itemDTO, orderItem);
            orderItem.setItemPrice(itemDTO.getPrice());
            orderItem.setQty(itemDTO.getQuantity());
            orderItem.setOrder(order);
            order.addItem(orderItem);
        }
    }

    public OrderDTO toOrderDTO(Order order) {
        log.info("Converting to orderResponse for order {}", order);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotalPrice(order.getTotalPrice());
        setContactInfoDTO(orderDTO, order.getContactInfo());
        setOrderItemDTO(orderDTO, order.getItems());
        log.debug("Converted orderDTO {}", orderDTO);
        return orderDTO;
    }

    private void setContactInfoDTO(OrderDTO order, ContactInfo contactInfo) {
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        BeanUtils.copyProperties(contactInfo, contactInfoDTO);
        order.setContactInfoDTO(contactInfoDTO);
    }

    private void setOrderItemDTO(OrderDTO order, Set<OrderItem> itemList) {
        List<ItemDTO> orderItems = new ArrayList<>();
        for(OrderItem orderItem : itemList) {
            ItemDTO itemDTO = new ItemDTO();
//            BeanUtils.copyProperties(itemDTO, orderItem);
            itemDTO.setQuantity(orderItem.getQty());
            itemDTO.setId(orderItem.getId());
            itemDTO.setItemType(orderItem.getItemType());
            itemDTO.setItemName(orderItem.getItemName());
            itemDTO.setPrice(orderItem.getItemPrice());
            itemDTO.setExpectedArrivalDate(orderItem.getExpectedArrivalDate());
            orderItems.add(itemDTO);
        }
        order.setItems(orderItems);
    }

//
//    public FoodItemDTO mapToFoodItemDTO(FoodItem item) {
//        FoodItemDTO itemDTO = new FoodItemDTO();
//        itemDTO.setCurrencyName(item.getCurrencyName());
//        itemDTO.setCurrentStock(item.getCurrentStock());
//        itemDTO.setId(item.getId());
//        itemDTO.setMfgCountry(item.getMfgCountry());
//        itemDTO.setName(item.getName());
//        itemDTO.setPrice(item.getPrice());
//        Set<FoodSaleCountryDTO> saleCountryDTOSet = new HashSet<>();
//        item.getSaleCountries().forEach(itemSaleCountry -> {
//            FoodSaleCountryDTO saleCountryDTO = new FoodSaleCountryDTO();
//            BeanUtils.copyProperties(itemSaleCountry, saleCountryDTO);
//            saleCountryDTOSet.add(saleCountryDTO);
//        });
//        itemDTO.setSaleCountries(saleCountryDTOSet);
//        return itemDTO;
//    }
//
//    public ClothesItemDTO mapToClothesItemDTO(ClothesItem item) {
//        ClothesItemDTO itemDTO = new ClothesItemDTO();
//        itemDTO.setCurrencyName(item.getCurrencyName());
//        itemDTO.setCurrentStock(item.getCurrentStock());
//        itemDTO.setId(item.getId());
//        itemDTO.setMfgCountry(item.getMfgCountry());
//        itemDTO.setName(item.getName());
//        itemDTO.setPrice(item.getPrice());
//        Set<SaleCountryDTO> saleCountryDTOSet = new HashSet<>();
//        item.getSaleCountries().forEach(itemSaleCountry -> {
//            SaleCountryDTO saleCountryDTO = new SaleCountryDTO();
//            BeanUtils.copyProperties(itemSaleCountry, saleCountryDTO);
//            saleCountryDTOSet.add(saleCountryDTO);
//        });
//        itemDTO.setSaleCountries(saleCountryDTOSet);
//        return itemDTO;
//    }


}

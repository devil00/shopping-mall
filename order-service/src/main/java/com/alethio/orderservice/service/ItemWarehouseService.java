package com.alethio.orderservice.service;

import com.alethio.orderservice.client.ItemWarehouseClient;
import com.alethio.orderservice.exceptions.APIException;
import com.alethio.orderservice.model.ItemType;
import com.alethio.orderservice.model.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class ItemWarehouseService {

    @Autowired
    private ItemWarehouseClient itemWarehouseClient;
    @Autowired
    private CopyBeanService copyBeanService;

    public void updateItem(ItemType itemType, ItemDTO item, Long quantity) throws APIException {
        try {
            itemWarehouseClient.updateItem(itemType.name().toLowerCase(Locale.ROOT), item);
            log.info("Updated item {} with stock {}", item.getId(), item.getQuantity());
        } catch(Exception ex) {
            log.error("Error updating item with id {}", item.getId(), ex);
            throw new APIException(ex);
        }

    }

    public void updateItems(ItemType itemType, List<ItemDTO> items) throws APIException {
        for(ItemDTO item : items) {
            updateItem(itemType, item, item.getQuantity());
        }
    }

    public void updateItemsStock(ItemType itemType, List<ItemDTO> items) throws APIException {
        for(ItemDTO item : items) {
            updateItemStock(itemType, item.getId(), item.getQuantity());
        }
    }

    public ItemDTO getItem(ItemType itemType, Integer itemId) throws APIException {
        try {
            return itemWarehouseClient.getItem(itemType.name().toLowerCase(Locale.ROOT), itemId);
        } catch (Exception ex) {
            log.error("Error getting item by id {}", itemId);
            throw new APIException(ex);
        }
    }

    public ItemDTO updateItemStock(ItemType itemType, Integer itemId, Long qty) throws APIException {
        try {
            return itemWarehouseClient.updateItemStock(itemType.name().toLowerCase(Locale.ROOT), itemId, qty);
        } catch (Exception ex) {
            log.error("Error updating stock qty for item-id {} and item-type - {}", itemId, itemType);
            throw new APIException(ex);
        }
    }

}

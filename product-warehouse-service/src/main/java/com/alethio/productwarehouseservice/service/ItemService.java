package com.alethio.productwarehouseservice.service;

import com.alethio.productwarehouseservice.exception.ResourceNotFoundException;
import com.alethio.productwarehouseservice.model.ClothesItem;
import com.alethio.productwarehouseservice.model.FoodItem;
import com.alethio.productwarehouseservice.model.Item;
import com.alethio.productwarehouseservice.model.ItemType;
import com.alethio.productwarehouseservice.model.dto.ItemDTO;
import com.alethio.productwarehouseservice.repository.ClothesItemRepository;
import com.alethio.productwarehouseservice.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private FoodItemRepository foodItemRepository;
    @Autowired
    private ClothesItemRepository clothesItemRepository;
    @Autowired
    private CopyBeanService copyBeanService;

    public Item findItem(ItemType itemType, int id) {
        if(ItemType.FOOD == itemType) {
            return foodItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Not found item with id %s", id)));
        } else {
            return clothesItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Not found item with id %s", id)));
        }
    }

    @Transactional
    public Item createItem(ItemType itemType, ItemDTO item) {
        if(ItemType.FOOD == itemType) {
           // FoodItem foodItem = (FoodItem) copyBeanService.mapToItem(itemType, null, item);

            return foodItemRepository.save((FoodItem) copyBeanService.mapToItem(itemType, null, item));
        } else if(ItemType.CLOTHES == itemType) {
            return clothesItemRepository.save((ClothesItem) copyBeanService.mapToItem(itemType, null, item));
        }
        throw new IllegalArgumentException(String.format("Given item type -%s not supported yet, creation failed", itemType));
    }

    public<R> List<R> updateItem(ItemType itemType, List<? extends ItemDTO> items) {
        if(ItemType.FOOD == itemType) {
            List<FoodItem> foodItems = items.stream().map(FoodItem.class::cast).collect(Collectors.toList());
            return (List<R>) foodItemRepository.saveAll(foodItems);
        } else {
            List<ClothesItem> foodItems = items.stream().map(ClothesItem.class::cast).collect(Collectors.toList());
            return (List<R>) clothesItemRepository.saveAll(foodItems);
        }
    }

    @Transactional
    public Item updateItem(ItemType itemType, ItemDTO item, Integer itemId) {
        if(ObjectUtils.isEmpty(item)) {
            throw new IllegalArgumentException();
        }
        if(ItemType.FOOD == itemType) {
            FoodItem itemDB = (FoodItem) findItem(itemType, itemId);
            return foodItemRepository.save((FoodItem) copyBeanService.mapToItem(itemType, itemDB, item));
        } else if(ItemType.CLOTHES == itemType) {
            ClothesItem itemDB = (ClothesItem) findItem(itemType, item.getId());
            return clothesItemRepository.save((ClothesItem) copyBeanService.mapToItem(itemType, itemDB, item));
        }
        throw new IllegalArgumentException(String.format("Given item type -%s not supported yet", itemType));
    }

    public Item updateStockQuantity(ItemType itemType, Integer itemId, Long quantity) {
        Item itemDB = findItem(itemType, itemId);
        if(!Objects.isNull(itemDB)) {
            itemDB.setCurrentStock(quantity);
            if(ItemType.FOOD == itemType) {
                FoodItem foodItem = (FoodItem) itemDB;
                return foodItemRepository.save(foodItem);
            } else if(ItemType.CLOTHES == itemType) {
                ClothesItem clothesItem = (ClothesItem) itemDB;
                return clothesItemRepository.save(clothesItem);
            }
        }
        return itemDB;
    }

}

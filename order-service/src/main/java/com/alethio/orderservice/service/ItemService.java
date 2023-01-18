package com.alethio.orderservice.service;

import com.alethio.orderservice.model.ClothesItem;
import com.alethio.orderservice.model.FoodItem;
import com.alethio.orderservice.model.Item;
import com.alethio.orderservice.model.ItemType;
import com.alethio.orderservice.repository.ClothesItemRepository;
import com.alethio.orderservice.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private FoodItemRepository foodItemRepository;
    private ClothesItemRepository clothesItemRepository;

    public ItemService(FoodItemRepository foodItemRepository, ClothesItemRepository clothesItemRepository) {
        this.foodItemRepository = foodItemRepository;
        this.clothesItemRepository = clothesItemRepository;
    }

    public Optional<Item> findItem(ItemType itemType, int id) {
        if(ItemType.FOOD == itemType) {
            return Optional.of(foodItemRepository.getReferenceById(id));
        } else {
            return Optional.of(clothesItemRepository.getReferenceById(id));
        }
    }

    public<R> List<R> updateItems(ItemType itemType, List<? extends Item> items) {
        if(ItemType.FOOD == itemType) {
            List<FoodItem> foodItems = items.stream().map(FoodItem.class::cast).collect(Collectors.toList());
            return (List<R>) foodItemRepository.saveAll(foodItems);
        } else {
            List<ClothesItem> foodItems = items.stream().map(ClothesItem.class::cast).collect(Collectors.toList());
            return (List<R>) clothesItemRepository.saveAll(foodItems);
        }
    }
}

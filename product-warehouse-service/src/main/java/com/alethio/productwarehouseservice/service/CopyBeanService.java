package com.alethio.productwarehouseservice.service;

import com.alethio.productwarehouseservice.exception.ItemTypeNotsupportedException;
import com.alethio.productwarehouseservice.model.ClothesItem;
import com.alethio.productwarehouseservice.model.ClothesSaleCountry;
import com.alethio.productwarehouseservice.model.FoodItem;
import com.alethio.productwarehouseservice.model.FoodSaleCountry;
import com.alethio.productwarehouseservice.model.Item;
import com.alethio.productwarehouseservice.model.ItemType;
import com.alethio.productwarehouseservice.model.dto.ClothesItemDTO;
import com.alethio.productwarehouseservice.model.dto.ClothesSaleCountryDTO;
import com.alethio.productwarehouseservice.model.dto.FoodItemDTO;
import com.alethio.productwarehouseservice.model.dto.FoodSaleCountryDTO;
import com.alethio.productwarehouseservice.model.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CopyBeanService {

    public FoodItemDTO mapToFoodItemDTO(FoodItem item) {
        FoodItemDTO itemDTO = new FoodItemDTO();
        itemDTO.setCurrencyName(item.getCurrencyName());
        itemDTO.setCurrentStock(item.getCurrentStock());
        itemDTO.setId(item.getId());
        itemDTO.setMfgCountry(item.getMfgCountry());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        Set<FoodSaleCountryDTO> saleCountryDTOSet = new HashSet<>();
        item.getSaleCountries().forEach(itemSaleCountry -> {
            FoodSaleCountryDTO saleCountryDTO = new FoodSaleCountryDTO();
            BeanUtils.copyProperties(itemSaleCountry, saleCountryDTO);
            saleCountryDTOSet.add(saleCountryDTO);
        });
        itemDTO.setSaleCountries(saleCountryDTOSet);
        return itemDTO;
    }

    public ClothesItemDTO mapToClothesItemDTO(ClothesItem item) {
        ClothesItemDTO itemDTO = new ClothesItemDTO();
        itemDTO.setCurrencyName(item.getCurrencyName());
        itemDTO.setCurrentStock(item.getCurrentStock());
        itemDTO.setId(item.getId());
        itemDTO.setMfgCountry(item.getMfgCountry());
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        Set<ClothesSaleCountryDTO> saleCountryDTOSet = new HashSet<>();
        item.getSaleCountries().forEach(itemSaleCountry -> {
            ClothesSaleCountryDTO saleCountryDTO = new ClothesSaleCountryDTO();
            BeanUtils.copyProperties(itemSaleCountry, saleCountryDTO);
            saleCountryDTOSet.add(saleCountryDTO);
        });
        itemDTO.setSaleCountries(saleCountryDTOSet);
        return itemDTO;
    }

    public ItemDTO mapToItemDTO(ItemType itemType, Item item) {
        if(ItemType.FOOD == itemType && item instanceof FoodItem) {
            return mapToFoodItemDTO((FoodItem) item);
        } else if (ItemType.CLOTHES == itemType && item instanceof ClothesItem) {
            return mapToClothesItemDTO((ClothesItem) item);
        }
        throw new ItemTypeNotsupportedException();
    }


    public FoodItem mapToFoodItem(FoodItem item, FoodItemDTO itemDTO) {
        if(!ObjectUtils.isEmpty(itemDTO.getCurrentStock())) {
            item.setCurrentStock(itemDTO.getCurrentStock());
        }
        if(!ObjectUtils.isEmpty(itemDTO.getMfgCountry())) {
            item.setMfgCountry(itemDTO.getMfgCountry());
        }
        if(!ObjectUtils.isEmpty(itemDTO.getName())) {
            item.setName(itemDTO.getName());
        }
        if(!ObjectUtils.isEmpty(itemDTO.getPrice())) {
            item.setPrice(itemDTO.getPrice());
        }

        if(!ObjectUtils.isEmpty(itemDTO.getCurrencyName())) {
            item.setCurrencyName(itemDTO.getCurrencyName());
        }

        if(!Objects.isNull(itemDTO.getSaleCountries())) {
            Set<FoodSaleCountry> saleCountrySet = new HashSet<>();
            Map<Integer, FoodSaleCountry> foodSaleCountryMap = Collections.emptyMap();
            if(!CollectionUtils.isEmpty(item.getSaleCountries())) {
                foodSaleCountryMap = item.getSaleCountries().stream()
                        .collect(Collectors.toMap(FoodSaleCountry::getId, foodItem -> foodItem));
            }
            Map<Integer, FoodSaleCountry> finalfoodSaleCountryMap = foodSaleCountryMap;
            itemDTO.getSaleCountries().forEach(itemSaleCountry -> {
                FoodSaleCountry saleCountryToUpdate = finalfoodSaleCountryMap.get(itemSaleCountry.getId());
                if(!Objects.isNull(saleCountryToUpdate)) {
                    if(!ObjectUtils.isEmpty(itemSaleCountry.getOperationCountry())) {
                        saleCountryToUpdate.setOperationCountry(itemSaleCountry.getOperationCountry());
                        saleCountrySet.add(saleCountryToUpdate);
                    }
                }  else {
                    saleCountryToUpdate = new FoodSaleCountry();

                    saleCountryToUpdate.setFoodItem(item);
                    saleCountryToUpdate.setId(itemSaleCountry.getId());
                    saleCountryToUpdate.setOperationCountry(itemSaleCountry.getOperationCountry());

                    saleCountrySet.add(saleCountryToUpdate);
                }

            });
            if(!saleCountrySet.isEmpty()) {
                item.setSaleCountries(saleCountrySet);
            }
        }

        return item;
    }

    public ClothesItem mapToClothesItem(ClothesItem item, ClothesItemDTO itemDTO) {
        if(!ObjectUtils.isEmpty(itemDTO.getCurrentStock())) {
            item.setCurrentStock(itemDTO.getCurrentStock());
        }
        if(!ObjectUtils.isEmpty(itemDTO.getMfgCountry())) {
            item.setMfgCountry(itemDTO.getMfgCountry());
        }
        if(!ObjectUtils.isEmpty(itemDTO.getName())) {
            item.setName(itemDTO.getName());
        }
        if(!ObjectUtils.isEmpty(itemDTO.getPrice())) {
            item.setPrice(itemDTO.getPrice());
        }

        if(!ObjectUtils.isEmpty(itemDTO.getCurrencyName())) {
            item.setCurrencyName(itemDTO.getCurrencyName());
        }

        if(!Objects.isNull(itemDTO.getSaleCountries())) {
            Set<ClothesSaleCountry> saleCountrySet = new HashSet<>();
            Map<Integer, ClothesSaleCountry> clothesSaleCountryMap = Collections.EMPTY_MAP;
            if(!CollectionUtils.isEmpty(item.getSaleCountries())) {
                clothesSaleCountryMap = item.getSaleCountries().stream()
                        .collect(Collectors.toMap(ClothesSaleCountry::getId, foodItem -> foodItem));
            }
            Map<Integer, ClothesSaleCountry> finalClothesSaleCountryMap = clothesSaleCountryMap;
            itemDTO.getSaleCountries().forEach(itemSaleCountry -> {
                ClothesSaleCountry saleCountryToUpdate = finalClothesSaleCountryMap.get(itemSaleCountry.getId());
                if(!Objects.isNull(saleCountryToUpdate)) {
                    if(!ObjectUtils.isEmpty(itemSaleCountry.getOperationCountry())) {
                        saleCountryToUpdate.setOperationCountry(itemSaleCountry.getOperationCountry());
                        saleCountrySet.add(saleCountryToUpdate);
                    }
                }  else {
                    saleCountryToUpdate = new ClothesSaleCountry();
                    saleCountryToUpdate.setClothesItem(item);
                    saleCountryToUpdate.setId(itemSaleCountry.getId());
                    saleCountryToUpdate.setOperationCountry(itemSaleCountry.getOperationCountry());

                    saleCountrySet.add(saleCountryToUpdate);
                }

            });
            if(!saleCountrySet.isEmpty()) {
                item.setSaleCountries(saleCountrySet);
            }
        }

        return item;
    }

    public Item mapToItem(ItemType itemType, Item itemDB, ItemDTO item) {
        if(ItemType.FOOD == itemType) {
            FoodItem foodItem = (FoodItem) itemDB;
            if(Objects.isNull(foodItem)) {
                foodItem = new FoodItem();
            }
            return mapToFoodItem(foodItem, (FoodItemDTO) item);
        } else if (ItemType.CLOTHES == itemType) {
            ClothesItem clothesItem = (ClothesItem) itemDB;
            if(Objects.isNull(clothesItem)) {
                clothesItem = new ClothesItem();
            }
            return mapToClothesItem(clothesItem, (ClothesItemDTO) item);
        }
        throw new ItemTypeNotsupportedException();
    }
}

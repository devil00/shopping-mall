package com.alethio.productwarehouseservice.model;

import java.util.HashMap;
import java.util.Map;

public enum ItemType {
    FOOD("food"),
    CLOTHES("clothes");

    private String key;
    private static final Map<String, ItemType> itemMap = new HashMap<>();

    static {
        for(ItemType itemType : ItemType.values()) {
            itemMap.put(itemType.key, itemType);
        }
    }

    ItemType(String key) {
        this.key = key;
    }

    public static ItemType findItemTypeByName(String itemType) {
        return itemMap.get(itemType);
    }
}

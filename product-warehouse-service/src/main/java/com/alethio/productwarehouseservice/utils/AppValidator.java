package com.alethio.productwarehouseservice.utils;

import com.alethio.productwarehouseservice.model.ItemType;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@UtilityClass
public final class AppValidator {

    public static boolean validateItemType(String itemType) {
        return Objects.isNull(ItemType.findItemTypeByName(itemType));
    }
}

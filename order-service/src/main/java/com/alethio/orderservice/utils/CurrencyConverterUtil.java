package com.alethio.orderservice.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

@UtilityClass
public final class CurrencyConverterUtil {

    public static String generateCurrencyStr(Double value, String currency) {
        if(!ObjectUtils.isEmpty(value)) {
            return String.format("%s %s", value, currency);
        }
        return String.format("0 %s", currency);
    }



}

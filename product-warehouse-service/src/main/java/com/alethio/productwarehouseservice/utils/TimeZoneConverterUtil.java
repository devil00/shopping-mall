package com.alethio.productwarehouseservice.utils;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class TimeZoneConverterUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateInKSTTz(Long epochInMillis) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return dateFormat.format(new Date(epochInMillis));
    }

    public static String getDateInSGTTz(Long epochInMillis) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        return dateFormat.format(new Date(epochInMillis));
    }
}

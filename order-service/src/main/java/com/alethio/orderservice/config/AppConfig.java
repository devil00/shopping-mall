package com.alethio.orderservice.config;

import com.alethio.orderservice.utils.AppConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

@Configuration
@ConfigurationProperties(value = "app")
@Data
public class AppConfig {
    private Integer sameCountryExpectedArrivalDays;
    private Integer diffCountryExpectedArrivalDays;
    private Double sgdToKRWFactor;
    private Double krwToSGDFactor;
    private Long replStock;
    private Long thresholdStock;

    public int getDiffCountryExpectedArrivalDays() {
        return !ObjectUtils.isEmpty(diffCountryExpectedArrivalDays) ? diffCountryExpectedArrivalDays :  AppConstants.DIFF_COUNTRY_DELIVERY_ARRIVAL_DAYS;
    }

    public int getSameCountryExpectedArrivalDays() {
        return !ObjectUtils.isEmpty(sameCountryExpectedArrivalDays) ? sameCountryExpectedArrivalDays :  AppConstants.SAME_COUNTRY_DELIVERY_ARRIVAL_DAYS;
    }
}

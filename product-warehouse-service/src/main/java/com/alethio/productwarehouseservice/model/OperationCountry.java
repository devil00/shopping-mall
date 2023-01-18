package com.alethio.productwarehouseservice.model;

import com.alethio.productwarehouseservice.utils.TimeZoneConverterUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OperationCountry {
    SK("South Korea", Currency.KRW) {
        @Override
        public String getDateInTz(long epochInMs) {
            return TimeZoneConverterUtil.getDateInKSTTz(epochInMs);
        }
    },
    SGP("Singapore", Currency.SGD) {
        @Override
        public String getDateInTz(long epochInMs) {
            return TimeZoneConverterUtil.getDateInSGTTz(epochInMs);
        }
    };

    @JsonValue
    private String name;
    private Currency currency;
    public abstract String getDateInTz(long epochInMillis);

    OperationCountry(String countryName, Currency currency) {
        this.name = countryName;
        this.currency = currency;
    }

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public static String fromCountryName(String countryName) {
//        return Arrays.stream(OperationCountry.values()).filter(r -> r.getName().equals(countryName))
//                .findFirst().orElseThrow(() -> new IllegalArgumentException(String.format("Not a valid operation country %s", countryName))).getName();
//    }
}

package com.alethio.orderservice.model;

import com.alethio.orderservice.utils.TimeZoneConverterUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

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

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OperationCountry fromCountryName(String countryName) {
        for (OperationCountry r : OperationCountry.values()) {
            if (r.getName().equals(countryName)) {
                return r;
            }
        }
        throw new IllegalArgumentException();
    }
}

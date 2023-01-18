package com.alethio.orderservice.service;

import com.alethio.orderservice.config.AppConfig;
import com.alethio.orderservice.exceptions.ConversionNotSupportedException;
import com.alethio.orderservice.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class CurrencyConversionService {

    @Autowired
    private AppConfig appConfig;

    public double convertToCurrency(Double value, Currency fromCurrency, Currency toCurrency) {
        if(ObjectUtils.isEmpty(value)) {
            return 0L;
        }
        if(areSourceTargetCurrencySame(fromCurrency, toCurrency)) {
            return value;
        }
        if(Currency.SGD == fromCurrency && Currency.KRW == toCurrency) {
            return value * appConfig.getSgdToKRWFactor();
        } else if(Currency.KRW == fromCurrency && Currency.SGD == toCurrency) {
            return value * appConfig.getKrwToSGDFactor();
        }
        throw new ConversionNotSupportedException(String.format("Conversion not supported from currency - %s to  currency - %s", fromCurrency, toCurrency));
    }


    private boolean areSourceTargetCurrencySame(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency == toCurrency;
    }
}

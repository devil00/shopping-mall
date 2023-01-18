package com.alethio.productwarehouseservice.converters;

import com.alethio.productwarehouseservice.model.OperationCountry;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;
import java.util.stream.Stream;

@Converter(autoApply = true)
@Component
public class OperationCountryConverter implements AttributeConverter<OperationCountry, String> {
    @Override
    public String convertToDatabaseColumn(OperationCountry operationCountry) {
        if(Objects.isNull(operationCountry)) {
            return null;
        }
        return operationCountry.getName();
    }

    @Override
    public OperationCountry convertToEntityAttribute(String operationCountryDbVal) {
        if(ObjectUtils.isEmpty(operationCountryDbVal)) {
            throw new IllegalArgumentException("DB val can't be null for country name");
        }
        return Stream.of(OperationCountry.values())
                .filter(c -> c.getName().equals(operationCountryDbVal))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

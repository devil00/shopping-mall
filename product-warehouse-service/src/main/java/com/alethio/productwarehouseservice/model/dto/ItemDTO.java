package com.alethio.productwarehouseservice.model.dto;

import com.alethio.productwarehouseservice.converters.OperationCountryConverter;
import com.alethio.productwarehouseservice.model.Currency;
import com.alethio.productwarehouseservice.model.OperationCountry;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClothesItemDTO.class, name = "ClothesItemDTO"),
        @JsonSubTypes.Type(value = FoodItemDTO.class, name = "FoodItemDTO"),
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO  {
    @NotNull
    private Integer id;
    @NotNull
    @JsonProperty("item_name")
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private Currency currencyName;
    @NotNull
    @JsonProperty("quantity")
    private Long currentStock;
    @NotNull
    private OperationCountry mfgCountry;
}

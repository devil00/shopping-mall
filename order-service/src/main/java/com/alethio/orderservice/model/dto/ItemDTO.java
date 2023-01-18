package com.alethio.orderservice.model.dto;

import com.alethio.orderservice.model.Currency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDTO {
    @NotNull
    private Integer id;
    @NotNull
    private String itemType;
    @NotNull
    private Long quantity;

    @JsonProperty("itemPrice")
    private String price;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty(value = "price", access = JsonProperty.Access.WRITE_ONLY)
    private Double itemPrice;
    private String expectedArrivalDate;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<SaleCountryDTO> saleCountries;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Currency currencyName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String mfgCountry;
}

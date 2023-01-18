package com.alethio.productwarehouseservice.model.dto;

import com.alethio.productwarehouseservice.model.FoodSaleCountry;
import com.alethio.productwarehouseservice.model.Item;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("FoodItemDTO")
@ToString(callSuper = true)
public class FoodItemDTO extends ItemDTO {
    private Set<FoodSaleCountryDTO> saleCountries;
}

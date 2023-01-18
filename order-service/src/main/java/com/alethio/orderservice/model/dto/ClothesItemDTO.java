package com.alethio.orderservice.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("ClothesItemDTO")
@ToString(callSuper = true)
public class ClothesItemDTO extends ItemDTO implements Serializable {
    private static final long serialVersionUID = -8344891046421679846L;
    private Set<SaleCountryDTO> saleCountries;

}

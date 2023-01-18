package com.alethio.productwarehouseservice.model.dto;

import com.alethio.productwarehouseservice.model.ClothesSaleCountry;
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
@JsonTypeName("ClothesItemDTO")
@ToString(callSuper = true)
public class ClothesItemDTO extends ItemDTO implements Serializable {
    private static final long serialVersionUID = -8344891046421679846L;
    private Set<ClothesSaleCountryDTO> saleCountries;
}

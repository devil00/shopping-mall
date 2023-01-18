package com.alethio.productwarehouseservice.model.dto;

import com.alethio.productwarehouseservice.model.OperationCountry;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FoodSaleCountryDTO implements Serializable {
    private static final long serialVersionUID = 7188453903113578799L;
    @NotNull
    private Integer id;
    private OperationCountry operationCountry;
}

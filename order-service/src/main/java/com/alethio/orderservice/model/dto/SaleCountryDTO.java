package com.alethio.orderservice.model.dto;

import com.alethio.orderservice.model.OperationCountry;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NotNull
public class SaleCountryDTO implements Serializable {
    private static final long serialVersionUID = 7188453903113578799L;
    @NotNull
    private Integer id;
    @NotNull
    private OperationCountry operationCountry;
}
package com.alethio.productwarehouseservice.model.dto;

import com.alethio.productwarehouseservice.model.OperationCountry;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NotNull
public class ClothesSaleCountryDTO implements Serializable {
    private static final long serialVersionUID = 7188453903113578799L;
    @NotNull
    private Integer id;
    @NotNull
    private OperationCountry operationCountry;
}

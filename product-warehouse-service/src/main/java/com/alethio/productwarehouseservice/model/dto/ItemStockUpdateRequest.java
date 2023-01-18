package com.alethio.productwarehouseservice.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemStockUpdateRequest {
    @NotNull
    private Long quantity;
}

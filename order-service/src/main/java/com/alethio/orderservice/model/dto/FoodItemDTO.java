package com.alethio.orderservice.model.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("FoodItemDTO")
@ToString(callSuper = true)
public class FoodItemDTO extends ItemDTO {
}
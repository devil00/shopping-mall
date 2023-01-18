package com.alethio.orderservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    @JsonProperty("contactInfo")
    private ContactInfoDTO contactInfoDTO;
    private List<ItemDTO> items;
    private String totalPrice;
    // Epoch UTC in ms
    private Long orderDate = Instant.now().toEpochMilli();
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer orderId;
}

package com.alethio.orderservice.model.dto;

import com.alethio.orderservice.model.OperationCountry;
import lombok.Data;

@Data
public class ContactInfoDTO {
    private String contactEmail;
    private String contactName;
    private String mobile;
    private OperationCountry country;
}

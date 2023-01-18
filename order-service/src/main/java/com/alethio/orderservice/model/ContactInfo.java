package com.alethio.orderservice.model;


import com.alethio.orderservice.converters.OperationCountryConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_contact_info")
@Getter
@Setter
public class ContactInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "country")
    @Convert(converter = OperationCountryConverter.class)
    private OperationCountry country;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "contactInfo")
    private Order order;
}

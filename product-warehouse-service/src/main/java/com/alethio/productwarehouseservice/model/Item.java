package com.alethio.productwarehouseservice.model;

import com.alethio.productwarehouseservice.converters.OperationCountryConverter;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@MappedSuperclass
@Data
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "item_name")
    private String name;
    private double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currencyName;
    @Column(name = "current_stock_qty")
    private Long currentStock;
    @Column(name = "manufacturing_country")
    @Convert(converter = OperationCountryConverter.class)
    private OperationCountry mfgCountry;
}

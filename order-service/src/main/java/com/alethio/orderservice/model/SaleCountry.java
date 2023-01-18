package com.alethio.orderservice.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_sale_country")
@Data
public class SaleCountry {
    @Id
    private Integer id;
    @Column(name = "item_id")
    private Integer itemId;
    @Column(name = "sale_country_name")
    private OperationCountry operationCountry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;
}

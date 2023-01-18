package com.alethio.orderservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "ClothesItem")
public class ClothesItem extends Item {

    @OneToMany(mappedBy = "clothesItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClothesItemSaleCountry> saleCountries;
}

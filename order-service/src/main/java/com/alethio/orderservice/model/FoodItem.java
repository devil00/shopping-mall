package com.alethio.orderservice.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity(name = "FoodItem")
public class FoodItem extends Item {

    @OneToMany(mappedBy = "foodItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FoodItemSaleCountry> saleCountries;
}

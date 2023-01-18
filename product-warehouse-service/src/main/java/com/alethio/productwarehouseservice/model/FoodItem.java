package com.alethio.productwarehouseservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "FoodItem")
@Table(name = "food_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class FoodItem extends Item implements Serializable {

    private static final long serialVersionUID = -8867028643073553255L;

    @OneToMany(mappedBy = "foodItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FoodSaleCountry> saleCountries;
}

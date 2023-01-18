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

@Entity(name = "ClothesItem")
@Table(name = "clothes_item")
@Data
@EqualsAndHashCode(callSuper = true)
public class ClothesItem extends Item implements Serializable {
    private static final long serialVersionUID = -8344891046421679846L;

    @OneToMany(mappedBy = "clothesItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClothesSaleCountry> saleCountries;
}

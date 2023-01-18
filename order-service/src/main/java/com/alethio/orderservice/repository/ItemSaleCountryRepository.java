package com.alethio.orderservice.repository;

import com.alethio.orderservice.model.SaleCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleCountryRepository extends JpaRepository<SaleCountry, Integer> {
}

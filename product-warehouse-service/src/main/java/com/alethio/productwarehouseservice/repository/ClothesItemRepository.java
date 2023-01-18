package com.alethio.productwarehouseservice.repository;

import com.alethio.productwarehouseservice.model.ClothesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesItemRepository extends JpaRepository<ClothesItem, Integer> {
}

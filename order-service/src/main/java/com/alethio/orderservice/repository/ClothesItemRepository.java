package com.alethio.orderservice.repository;

import com.alethio.orderservice.model.ClothesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesItemRepository extends JpaRepository<ClothesItem, Integer> {
}

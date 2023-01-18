package com.alethio.orderservice.repository;

import com.alethio.orderservice.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
}

package com.example.testapi.repository;

import com.example.testapi.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE p.date > ?1")
    List<Purchase> findLastThreeMonths(String threeMonthsAgo);

    @Query("SELECT p FROM Purchase p WHERE p.customerName = ?1 AND p.date > ?2")
    List<Purchase> findByCustomerName(String name, String threeMonthsAgo);
}

package com.example.testapi.repository;

import com.example.testapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Resource;

@Resource
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

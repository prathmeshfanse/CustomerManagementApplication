package com.CustomerManagement.CustomerManagementApplication.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CustomerManagement.CustomerManagementApplication.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,UUID>{
    boolean existsByEmail(String email);
    Customer getCustomerByName(String name);
    Customer getCustomerByEmail(String email);

}

package com.CustomerManagement.CustomerManagementApplication.service;

import java.util.List;
import java.util.UUID;

import com.CustomerManagement.CustomerManagementApplication.dto.CustomerDto;
import com.CustomerManagement.CustomerManagementApplication.entity.Customer;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    List<CustomerDto> getAllCustomers();
    public CustomerDto getCustomerById(UUID id);
    CustomerDto getCustomerByName(String name);
    CustomerDto getCustomerByEmail(String email);
    boolean updateCustomerById(UUID id, Customer customer);
    boolean deleteById(UUID id);

}

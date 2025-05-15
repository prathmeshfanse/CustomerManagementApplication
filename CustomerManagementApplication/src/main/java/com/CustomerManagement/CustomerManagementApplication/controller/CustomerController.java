package com.CustomerManagement.CustomerManagementApplication.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CustomerManagement.CustomerManagementApplication.dto.CustomerDto;
import com.CustomerManagement.CustomerManagementApplication.entity.Customer;
import com.CustomerManagement.CustomerManagementApplication.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public CustomerDto createCustomer(@RequestBody @Valid CustomerDto customerDto) throws Exception {
        CustomerDto savedCustomer = service.createCustomer(customerDto);
        savedCustomer.setTier(customerDto.getTier());
        return savedCustomer;
    }

    @GetMapping("/allCustomers")
    public List<CustomerDto> getAllCustomer() {
        return service.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable UUID id) {
        return service.getCustomerById(id);
    }

    @GetMapping(params = "name")
    public CustomerDto getCustomerByName(@RequestParam String name) {
        return service.getCustomerByName(name);
    }

    @GetMapping(params = "email")
    public CustomerDto getCustomerByEmail(@RequestParam String email) {
        return service.getCustomerByEmail(email);
    }

    @PutMapping("/{id}")
    public boolean updateCustomerById(@PathVariable UUID id, @RequestBody Customer customer) {

        return service.updateCustomerById(id, customer);

    }

    @DeleteMapping("/{id}")
    public boolean deleteCustomerById(@PathVariable UUID id) {
        CustomerDto customer = service.getCustomerById(id);

        if (customer != null) {
            return service.deleteById(id);
        } else {
            return false;
        }

    }
}

package com.CustomerManagement.CustomerManagementApplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CustomerManagement.CustomerManagementApplication.dto.CustomerDto;
import com.CustomerManagement.CustomerManagementApplication.entity.Customer;
import com.CustomerManagement.CustomerManagementApplication.exception.CustomerNotFoundException;
import com.CustomerManagement.CustomerManagementApplication.exception.CustomerWithEmailAlreadyExists;
import com.CustomerManagement.CustomerManagementApplication.exception.InvalidEmailException;
import com.CustomerManagement.CustomerManagementApplication.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    // Entity -> DTO
    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getAnnualSpend(),
                customer.getLastPurchedDate());
    }

    // DTO -> Entity
    private Customer convertToEntity(CustomerDto customerDto) {
        return new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail(),
                customerDto.getAnnualSpend(), customerDto.getLastPurchedDate());
    }

    private CustomerDto getPurchasedDate(Customer customer) {
        CustomerDto customerDto = convertToDto(customer);
        Date purchesDate = customer.getLastPurchedDate();
        LocalDate purchasedDate = LocalDate.of(purchesDate.getYear(), purchesDate.getMonth(), purchesDate.getDay());
        Double spend = customer.getAnnualSpend();

        if (spend >= 10000 && !purchasedDate.isBefore(LocalDate.now().minusMonths(6))) {
            customerDto.setTier("Platinum");
        } else if (spend >= 1000 && !purchasedDate.isBefore(LocalDate.now().minusMonths(12))) {
            customerDto.setTier("Gold");
        } else {
            customerDto.setTier("Silver");
        }
        return customerDto;
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = convertToEntity(customerDto);

        if (customerDto == null) {
            throw new IllegalArgumentException("Missing Required fields");
        } else if (customerDto.getName() == null || customerDto.getName().equals("")) {
            throw new IllegalArgumentException("Missing required field: name");
        } else if (customerDto.getEmail() == null || customerDto.getEmail().equals("")) {
            throw new IllegalArgumentException("Missing required field: email");
        }

        if (!customerDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidEmailException("Invalid email format");
        }else if(repository.existsByEmail(customerDto.getEmail())){
            throw new CustomerWithEmailAlreadyExists("Email Already Exists");
        }

        return convertToDto(repository.save(customer));
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = repository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto customerDto = convertToDto(customer);
            Date purchesDate = customer.getLastPurchedDate();
            LocalDate purchasedDate = LocalDate.of(purchesDate.getYear(), purchesDate.getMonth() + 1,
                    purchesDate.getDay() + 1);
            Double spend = customer.getAnnualSpend();

            if (spend >= 10000 && purchasedDate.isBefore(LocalDate.now().minusMonths(6))) {
                customerDto.setTier("Platinum");
            } else if (spend >= 1000 && purchasedDate.isBefore(LocalDate.now().minusMonths(12))) {
                customerDto.setTier("Gold");
            } else {
                customerDto.setTier("Silver");
            }

            customerDtos.add(customerDto);
        }

        return customerDtos;

    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not Found with id : " + id));

        return getPurchasedDate(customer);
    }

    public CustomerDto getCustomerByName(String name) {
        Customer customer = repository.getCustomerByName(name);
        return getPurchasedDate(customer);
    }

    public CustomerDto getCustomerByEmail(String email) {
        Customer customer = repository.getCustomerByEmail(email);
        return getPurchasedDate(customer);
    }

    @Override
    public boolean updateCustomerById(UUID id, Customer customer) {
        Customer existingCustomer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        // Update the fields
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setAnnualSpend(customer.getAnnualSpend());
        existingCustomer.setLastPurchedDate(customer.getLastPurchedDate());

        return repository.save(existingCustomer) != null;

    }

    @Override
    public boolean deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }

        repository.deleteById(id);
        return true;
    }


}

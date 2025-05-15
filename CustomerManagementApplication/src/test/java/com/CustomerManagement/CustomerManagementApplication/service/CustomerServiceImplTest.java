package com.CustomerManagement.CustomerManagementApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.CustomerManagement.CustomerManagementApplication.dto.CustomerDto;
import com.CustomerManagement.CustomerManagementApplication.entity.Customer;
import com.CustomerManagement.CustomerManagementApplication.exception.CustomerWithEmailAlreadyExists;
import com.CustomerManagement.CustomerManagementApplication.exception.CustomerWithNameAlreadyExists;
import com.CustomerManagement.CustomerManagementApplication.exception.InvalidEmailException;
import com.CustomerManagement.CustomerManagementApplication.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository repository;

    @InjectMocks 
    private CustomerServiceImpl service;

    private CustomerDto customer1;
    private UUID id;
        
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @BeforeEach
    public void setup(){
        id=UUID.randomUUID();
        customer1 = new CustomerDto();
        customer1.setId(id);
        customer1.setName("John");
        customer1.setAnnualSpend(2222.2);
        customer1.setEmail("John@gmail.com");
    }

    @Test
    public void testValidEmail_createCustomer(){
        customer1.setEmail("Invalid-Email");

        InvalidEmailException exception = assertThrows(
            InvalidEmailException.class, () -> service.createCustomer(customer1));
        
        assertEquals("Invalid email format", exception.getMessage());
        
    }

    @Test
    public void testDuplicateEmail_createCustomer(){
        when(repository.existsByEmail(customer1.getEmail())).thenReturn(true);

        CustomerWithEmailAlreadyExists exception = assertThrows(
            CustomerWithEmailAlreadyExists.class, () -> service.createCustomer(customer1));
        
        assertEquals("Email Already Exists", exception.getMessage());

    }

}

package com.CustomerManagement.CustomerManagementApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
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
        customer1.setAnnualSpend(1000.1d);
        customer1.setEmail("John@gmail.com");
        customer1.setLastPurchedDate(new Date());

    }




    @Test
    public void testPlatinumTier_sixMonthAgo(){
        customer1.setTier("Platinum");
        customer1.setAnnualSpend(10000.1);
        customer1.setLastPurchedDate(java.sql.Date.valueOf(LocalDate.now().minusMonths(6)));

        when(service.getCustomerByName("John")).thenReturn(customer1);

        CustomerDto customerDto = service.getCustomerByName("John");

        assertEquals("Platinum", customerDto.getTier());

        logger.info("Test case testPlatinumTier_sixMonthAgo passed");
        
    }

    @Test
    public void testGoldTier_twelveMonthAgo(){
        customer1.setTier("Gold");
        customer1.setAnnualSpend(1000.1);
        customer1.setLastPurchedDate(java.sql.Date.valueOf(LocalDate.now().minusMonths(12)));

        when(service.getCustomerByName("John")).thenReturn(customer1);

        CustomerDto dto =  service.getCustomerByName("John");

        assertEquals("Gold", dto.getTier());
        
        logger.info("Test case testGoldTier_twelveMonthAgo passed");
    }

    @Test
    public void testSilverTier(){
        customer1.setTier("Silver");
        
        when(service.getCustomerByName("John")).thenReturn(customer1);

        CustomerDto dto = service.getCustomerByName("John");

        assertEquals("Silver", dto.getTier()); 

        logger.info("Test case testSilverTier passed");

    }

     @Test
    public void testMissingEmail() throws Exception{
        customer1.setEmail(null);

        try{
            service.createCustomer(customer1);
            fail("Expected IllegalArgumentException to be thrown");
        }catch(IllegalArgumentException ex){
            assertEquals("Missing required field: email", ex.getMessage());
            logger.info("testMissingEmail passed");
        }
    }

    @Test
    public void testInvalidEmail() throws Exception{
        customer1.setEmail("Invalidalid");

        try{
            service.createCustomer(customer1);
        }catch(InvalidEmailException e){
            assertEquals("Invalid email format", e.getMessage());
            logger.info("testInvalidEmail passed");
        }
    }

    @Test
    public void testDuplicateEmail() throws Exception{
        customer1.setEmail("John@gmail.com");

        when(repository.existsByEmail("John@gmail.com")).thenReturn(true);

        try{
            service.createCustomer(customer1);
            fail("Expected CustomerWithEmailAlreadyExists to be thrown");
        }catch(CustomerWithEmailAlreadyExists ex){
            assertEquals("Email Already Exists", ex.getMessage());
            logger.info("testDuplicateEmail passed");
        }

        
    }

}

package com.CustomerManagement.CustomerManagementApplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import com.CustomerManagement.CustomerManagementApplication.repository.CustomerRepository;
import com.CustomerManagement.CustomerManagementApplication.service.CustomerService;
import com.CustomerManagement.CustomerManagementApplication.service.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private CustomerDto customer1;
    private CustomerDto customer2;

    private UUID id;

    @BeforeEach
    void setup() {
        id=UUID.randomUUID();
        customer1 = new CustomerDto();
        customer1.setId(id);
        customer1.setName("John");
        customer1.setAnnualSpend(2222.2);
        customer1.setEmail("John@gmail.com");
        
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 1, 0, 0, 0); // Year, Month (0-based), Day, Hour, Min, Sec
        cal.set(Calendar.MILLISECOND, 0); // Optional: remove milliseconds
        Date date = cal.getTime();

        customer1.setLastPurchedDate(date);

        customer2 = new CustomerDto();
        customer2.setId(id);
        customer2.setName("Rohn");
        customer2.setAnnualSpend(22422.2);
        customer2.setEmail("Rohn@gmail.com");
        customer2.setLastPurchedDate(new Date(2024 - 01 - 21));
    }

    @Test
    public void testcreateCustomer() throws Exception {
        when(service.createCustomer(customer1)).thenReturn(customer1);

        CustomerDto result = controller.createCustomer(customer1);

        assertEquals("John", result.getName());
        assertEquals(2222.2, result.getAnnualSpend());
        assertEquals("John@gmail.com", result.getEmail());

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 1, 0, 0, 0); // Year, Month (0-based), Day, Hour, Min, Sec
        cal.set(Calendar.MILLISECOND, 0); // Optional: remove milliseconds
        Date expectedDate = cal.getTime();

        assertEquals(expectedDate, result.getLastPurchedDate());

        logger.info("Test case testcreateCustomer passed");

    }

    @Test
    public void testgetAllCustomer(){
        List<CustomerDto> expected = Arrays.asList(customer1,customer2);
        when(service.getAllCustomers()).thenReturn(expected);

        List<CustomerDto> actual = controller.getAllCustomer();

        assertEquals(2, actual.size());
        assertEquals("John", actual.get(0).getName());
        assertEquals("Rohn", actual.get(1).getName());

        
        logger.info("Test case testgetAllCustomer passed");
    }

    @Test
    public void testgetCustomerById(){
        when(service.getCustomerById(id)).thenReturn(customer1);

        CustomerDto actual = controller.getCustomerById(id);

        assertEquals(id, actual.getId());
        assertEquals("John", actual.getName());
        assertEquals("John@gmail.com", actual.getEmail());
        assertEquals(2222.2, actual.getAnnualSpend());

        
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MAY, 1, 0, 0, 0); // Year, Month (0-based), Day, Hour, Min, Sec
        cal.set(Calendar.MILLISECOND, 0); // Optional: remove milliseconds
        Date expectedDate = cal.getTime();

        assertEquals(expectedDate, actual.getLastPurchedDate());
                
        logger.info("Test case testgetCustomerById passed");

    } 

    @Test
    public void testupdateCustomerById(){
        Customer updated = new Customer();

        updated.setName("Updated Name");
        updated.setEmail("Updated@gmail.com");
        updated.setAnnualSpend(23245.5);

        when(service.updateCustomerById(id, updated)).thenReturn(true);

        boolean result = controller.updateCustomerById(id, updated);

        assertEquals(true, result);

        logger.info("Test case testupdateCustomerById passed");

    }

    @Test
    public void testdeleteById_success(){
        when(service.getCustomerById(id)).thenReturn(customer1);
        when(service.deleteById(id)).thenReturn(true);

        boolean result = controller.deleteCustomerById(id);

        assertEquals(true, result);

        logger.info("Test case testdeleteById_success passed");
    }

    @Test
    public void testdeleteById_notFound(){
        when(service.getCustomerById(id)).thenReturn(null);

        boolean result = controller.deleteCustomerById(id);

        assertEquals(false, result);

        logger.info("Test case testdeleteById_notFound passed");
    }
}

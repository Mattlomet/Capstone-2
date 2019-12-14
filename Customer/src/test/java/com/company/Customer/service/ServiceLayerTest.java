package com.company.Customer.service;

import com.company.Customer.dao.CustomerDao;
import com.company.Customer.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceLayer serviceLayer;

    @MockBean
    CustomerDao customerDao;

    @Before
    public void setUp() throws Exception {
        customerDaoMocks();
    }

    @Test
    @Transactional
    public void saveCustomerGetCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("Neo");
        customer.setLastName("Anderson");
        customer.setStreet("123 Underground Cave");
        customer.setCity("Machine City");
        customer.setZip("12345");
        customer.setEmail("the_one@freeyourmind.com");
        customer.setPhone("987-654-3210");

        customer = serviceLayer.saveCustomer(customer);

        assertEquals(customer, serviceLayer.getCustomerById(customer.getCustomerId()));
    }

    @Test
    public void getAllInventory() {
        Customer customer = new Customer();
        customer.setFirstName("Neo");
        customer.setLastName("Anderson");
        customer.setStreet("123 Underground Cave");
        customer.setCity("Machine City");
        customer.setZip("12345");
        customer.setEmail("the_one@freeyourmind.com");
        customer.setPhone("987-654-3210");

        customer = serviceLayer.saveCustomer(customer);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        assertEquals(customerList, serviceLayer.getAllCustomers());
    }


    public void customerDaoMocks(){
        Customer customer = new Customer();
        customer.setFirstName("Neo");
        customer.setLastName("Anderson");
        customer.setStreet("123 Underground Cave");
        customer.setCity("Machine City");
        customer.setZip("12345");
        customer.setEmail("the_one@freeyourmind.com");
        customer.setPhone("987-654-3210");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Neo");
        customer1.setLastName("Anderson");
        customer1.setStreet("123 Underground Cave");
        customer1.setCity("Machine City");
        customer1.setZip("12345");
        customer1.setEmail("the_one@freeyourmind.com");
        customer1.setPhone("987-654-3210");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer1);

        doReturn(customer1).when(customerDao).saveCustomer(customer);
        doReturn(customer1).when(customerDao).getCustomerById(1);
        doReturn(customerList).when(customerDao).getAllCustomers();
    }
}
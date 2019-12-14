package com.company.Customer.dao;

import com.company.Customer.model.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;


    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = customerDao.getAllCustomers();
        for (Customer customer:
                customerList) {
            customerDao.deleteCustomer(customer.getCustomerId());
        }
    }


    @Test
    @Transactional
    public void createGetGetAllDeleteCustomer(){
        Customer customer = new Customer();
        customer.setFirstName("Neo");
        customer.setLastName("Anderson");
        customer.setStreet("123 Underground Cave");
        customer.setCity("Machine City");
        customer.setZip("12345");
        customer.setEmail("the_one@freeyourmind.com");
        customer.setPhone("987-654-3210");

        customer = customerDao.saveCustomer(customer);

        assertEquals(customer, customerDao.getCustomerById(customer.getCustomerId()));

        assertEquals(1,customerDao.getAllCustomers().size());

        customerDao.deleteCustomer(customer.getCustomerId());

        assertEquals(0,customerDao.getAllCustomers().size());
    }

    @Test
    public void updateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("Neo");
        customer.setLastName("Anderson");
        customer.setStreet("123 Underground Cave");
        customer.setCity("Machine City");
        customer.setZip("12345");
        customer.setEmail("the_one@freeyourmind.com");
        customer.setPhone("987-654-3210");

        customer = customerDao.saveCustomer(customer);

        customer.setFirstName("Morpheus");

        customerDao.updateCustomer(customer);

        assertEquals(customer.getFirstName(), customerDao.getCustomerById(customer.getCustomerId()).getFirstName());
    }
}

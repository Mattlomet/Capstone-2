package com.company.Customer.service;

import com.company.Customer.dao.CustomerDao;
import com.company.Customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    @Autowired
    CustomerDao customerDao;

    public Customer saveCustomer(Customer customer) {
        return customerDao.saveCustomer(customer);
    }

    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    public List<Customer> getAllCustomers() { return customerDao.getAllCustomers(); }

    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerDao.deleteCustomer(id);
    }
}

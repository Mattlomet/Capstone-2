package com.company.Customer.dao;

import com.company.Customer.model.Customer;

import java.util.List;

public interface CustomerDao {

    Customer saveCustomer(Customer customer);
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);

}

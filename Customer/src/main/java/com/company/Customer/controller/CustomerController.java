package com.company.Customer.controller;

import com.company.Customer.model.Customer;
import com.company.Customer.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"customer"})
public class CustomerController {

    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

    @Cacheable
    @GetMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomerById(@PathVariable int id){ return serviceLayer.getCustomerById(id); }

    @Cacheable
    @GetMapping("/customer/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        return serviceLayer.getAllCustomers();
    }


    // POST METHODS

    @CachePut(key = "#result.getCustomerId()")
    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer saveCustomer(@RequestBody Customer customer){
        return serviceLayer.saveCustomer(customer);
    }

    // UPDATE METHODS

    @CacheEvict(key = "#result.getCustomerId()")
    @PutMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody Customer customer){
        serviceLayer.updateCustomer(customer);
    }

    // DELETE METHODS

    @CacheEvict
    @DeleteMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable int id){
        serviceLayer.deleteCustomer(id);
    }

}

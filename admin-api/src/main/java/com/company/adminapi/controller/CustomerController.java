package com.company.adminapi.controller;

import com.company.adminapi.exception.NotFoundException;
import com.company.adminapi.model.Customer;
import com.company.adminapi.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customer")
@CacheConfig(cacheNames = {"customer"})
public class CustomerController {
    @Autowired
    private ServiceLayer service;

    @CachePut(key = "#result.getId()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody @Valid Customer customer){
        return service.addCustomer(customer);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id){
        Customer customer = service.getCustomer(id);
        if(customer == null)
            throw new NotFoundException("There is no customer with this id: " + id + ".");
        return customer;
    }

    @Cacheable
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers(){
        List<Customer> customers = service.getAllCustomers();
        if(customers.size() == 0)
            throw new NotFoundException("No customers found.");
        return customers;
    }

    @CacheEvict(key = "#customer.getId()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@PathVariable int id,@RequestBody @Valid Customer customer){
        if(customer.getId() == 0)
            customer.setId(id);
        if(customer.getId() != id)
            throw new IllegalArgumentException("Customer ID must match.");

        service.updateCustomer(id,customer);
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id){
        service.deleteCustomer(id);
    }
}

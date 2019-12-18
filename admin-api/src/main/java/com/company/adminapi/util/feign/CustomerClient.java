package com.company.adminapi.util.feign;

import com.company.adminapi.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {
    @RequestMapping(value = "/customer",method = RequestMethod.POST)
    public Customer addCustomer(@RequestBody Customer customer);

    @RequestMapping(value = "/customer/{id}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id);


    @RequestMapping(value = "/customer",method = RequestMethod.GET)
    public List<Customer> getAllCustomers();

    @RequestMapping(value = "/customer/{id}",method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable int id, @RequestBody Customer customer);

    @RequestMapping(value = "/customer/{id}",method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable int id);
}

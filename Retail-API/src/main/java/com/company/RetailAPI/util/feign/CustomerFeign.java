package com.company.RetailAPI.util.feign;

import com.company.RetailAPI.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerFeign {

    @GetMapping("/customer/{id}")
    Customer getCustomerById(@PathVariable int id);
}

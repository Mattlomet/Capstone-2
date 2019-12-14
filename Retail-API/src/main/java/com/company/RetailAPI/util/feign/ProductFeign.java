package com.company.RetailAPI.util.feign;


import com.company.RetailAPI.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductFeign {

    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable int id);
}

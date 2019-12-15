package com.company.RetailAPI.util.feign;


import com.company.RetailAPI.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeign {

    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable int id);

//    @GetMapping("/product/invoice/{invoiceId}")
//    List<Product> getProductByInvoiceId(@PathVariable int invoiceId);

    @GetMapping("/product/all")
    List<Product> getAllProducts();
}

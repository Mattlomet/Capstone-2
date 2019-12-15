package com.company.Product.controller;

import com.company.Product.model.Product;
import com.company.Product.service.ServiceLayer;
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
@CacheConfig(cacheNames = {"product"})
public class ProductController {

    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

    @Cacheable
    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id){
        return serviceLayer.getProductById(id);
    }

//    @GetMapping("/product/invoice/{invoiceId}")
//    @ResponseStatus(HttpStatus.OK)
//    public List<Product> getProductByInvoiceId(@PathVariable int invoiceId){
//        return serviceLayer.getProductByInvoiceId(invoiceId);
//    }

    @Cacheable
    @GetMapping("/product/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        return serviceLayer.getAllProducts();
    }


    // POST METHODS

    @CachePut(key = "#result.getProductId()")
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product){
        return serviceLayer.saveProduct(product);
    }

    // UPDATE METHODS

    @CacheEvict(key = "#result.getProductId()")
    @PutMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product product){
        serviceLayer.updateProduct(product);
    }

    // DELETE METHODS

    @CacheEvict
    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id){
        serviceLayer.deleteProduct(id);
    }

}

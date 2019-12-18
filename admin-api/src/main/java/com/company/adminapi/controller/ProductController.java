package com.company.adminapi.controller;

import com.company.adminapi.exception.NotFoundException;
import com.company.adminapi.model.Product;
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
@RequestMapping("/product")
@CacheConfig(cacheNames = {"product"})
public class ProductController {
    @Autowired
    private ServiceLayer service;

    @CachePut(key = "#result.getId()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody @Valid Product product){
        return service.addProduct(product);
    }

    @Cacheable
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        List<Product> products = service.getAllProducts();
        if(products.size() == 0)
            throw new NotFoundException("No products found.");
        return products;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable int id){
        Product product = service.getProduct(id);
        if(product == null)
            throw new NotFoundException("There is no product with this id " + id + ".");
        return product;
    }

    @CacheEvict(key = "#product.getId()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable int id, @RequestBody @Valid Product product){
        if(product.getId() == 0)
            product.setId(0);
        if(product.getId() != id)
            throw new IllegalArgumentException("Product ID must match");
        service.updateProduct(id,product);
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable int id){
        service.deleteProduct(id);
    }



}

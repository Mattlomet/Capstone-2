package com.company.Product.controller;

import com.company.Product.model.Product;
import com.company.Product.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class ProductController {

    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable int id){
        return serviceLayer.getProductById(id);
    }

    @GetMapping("/product/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        return serviceLayer.getAllProducts();
    }


    // POST METHODS

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product){
        return serviceLayer.saveProduct(product);
    }

    // UPDATE METHODS

    @PutMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@RequestBody Product product){
        serviceLayer.updateProduct(product);
    }

    // DELETE METHODS

    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable int id){
        serviceLayer.deleteProduct(id);
    }

}

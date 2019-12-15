package com.company.Inventory.controller;

import com.company.Inventory.model.Inventory;
import com.company.Inventory.service.ServiceLayer;
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
//@CacheConfig(cacheNames = {"inventory"})
public class InventoryController {

    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

//    @Cacheable
    @GetMapping("/inventory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable int id){
        return serviceLayer.getInventoryById(id);
    }

//    @Cacheable
    @GetMapping("/inventory")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventory(){
        return serviceLayer.getAllInventory();
    }

//    @Cacheable
    @GetMapping("/inventory/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getInventoryByProductId(@PathVariable int id){
        return serviceLayer.getInventoryByProductId(id);
    }

    // POST METHODS

//    @CachePut(key = "#result.getInventoryId()")
    @PostMapping("/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory saveInventory(@RequestBody Inventory inventory){
        return serviceLayer.saveInventory(inventory);
    }

    // UPDATE METHODS

//    @CacheEvict(key = "#result.getInventoryId()")
    @PutMapping("/inventory")
    @ResponseStatus(HttpStatus.OK)
    public void updateInventory(@RequestBody Inventory inventory){
        serviceLayer.updateInventory(inventory);
    }

    // DELETE METHODS

//    @CacheEvict
    @DeleteMapping("/inventory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInventory(@PathVariable int id){
        serviceLayer.deleteInventory(id);
    }

}

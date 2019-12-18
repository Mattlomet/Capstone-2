package com.company.adminapi.controller;

import com.company.adminapi.exception.NotFoundException;
import com.company.adminapi.model.Inventory;
import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.util.feign.InventoryClient;
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
@RequestMapping("/inventory")
@CacheConfig(cacheNames = {"inventory"})
public class InventoryController {
    @Autowired
    private ServiceLayer service;

    @CachePut(key = "#result.getId()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory addInventory(@RequestBody @Valid Inventory inventory){
        return service.addInventory(inventory);
    }

    @Cacheable
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventory(@PathVariable int id){
        Inventory inventory = service.getInventory(id);
        if(inventory == null)
            throw new NotFoundException("There is no inventory with this id: " + id + ".");
        return inventory;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventories(){
        List<Inventory> inventories  = service.getAllInventories();
        if(inventories.size() == 0)
            throw new NotFoundException("Inventory not found.");
        return inventories;
    }

    @CacheEvict(key = "#inventory.getId()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInventory(@PathVariable int id,@RequestBody @Valid  Inventory inventory){
        if(inventory.getId() == 0)
            inventory.setId(id);
        if(inventory.getId() != id)
            throw new IllegalArgumentException("Inventory ID must match,");
        service.updateInventory(id,inventory);
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable int id){
        service.deleteInventory(id);
    }

}

package com.company.Inventory.controller;

import com.company.Inventory.model.Inventory;
import com.company.Inventory.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class InventoryController {

    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

    @GetMapping("/inventory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory getInventoryById(@PathVariable int id){
        return serviceLayer.getInventoryById(id);
    }

    @GetMapping("/inventory")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> getAllInventory(){
        return serviceLayer.getAllInventory();
    }


    // POST METHODS

    @PostMapping("/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public Inventory saveInventory(@RequestBody Inventory inventory){
        return serviceLayer.saveInventory(inventory);
    }

    // UPDATE METHODS

    @PutMapping("/inventory")
    @ResponseStatus(HttpStatus.OK)
    public void updateInventory(@RequestBody Inventory inventory){
        serviceLayer.updateInventory(inventory);
    }

    // DELETE METHODS

    @DeleteMapping("/inventory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInventory(@PathVariable int id){
        serviceLayer.deleteInventory(id);
    }

}

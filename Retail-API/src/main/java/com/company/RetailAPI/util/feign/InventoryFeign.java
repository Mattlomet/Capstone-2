package com.company.RetailAPI.util.feign;


import com.company.RetailAPI.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryFeign {

    @GetMapping("/inventory/{id}")
    Inventory getInventoryById(@PathVariable int id);

    @GetMapping("/inventory")
    List<Inventory> getAllInventory();

    @PutMapping("/inventory")
    void updateInventory(@RequestBody Inventory inventory);
}

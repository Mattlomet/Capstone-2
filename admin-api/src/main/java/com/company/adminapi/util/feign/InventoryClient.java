package com.company.adminapi.util.feign;

import com.company.adminapi.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {

    @RequestMapping(value = "/inventory",method = RequestMethod.POST)
    public Inventory addInventory(@RequestBody Inventory inventory);

    @RequestMapping(value = "/inventory/{id}",method = RequestMethod.GET)
    public Inventory getInventory(@PathVariable int id);


    @RequestMapping(value = "/inventory",method = RequestMethod.GET)
    public List<Inventory> getAllInventories();

    @RequestMapping(value = "/inventory/{id}",method = RequestMethod.PUT)
    public void updateInventory(@PathVariable int id, @RequestBody Inventory inventory);



    @RequestMapping(value = "/inventory/{id}",method = RequestMethod.DELETE)
    public void deleteInventory(@PathVariable int id);
}

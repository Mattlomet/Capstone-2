package com.company.Inventory.service;

import com.company.Inventory.dao.InventoryDao;
import com.company.Inventory.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    @Autowired
    InventoryDao inventoryDao;

    public Inventory saveInventory(Inventory inventory) {
        return inventoryDao.saveInventory(inventory);
    }

    public Inventory getInventoryById(int id) {
        return inventoryDao.getInventoryById(id);
    }

    public List<Inventory> getInventoryByProductId(int id) {
        return inventoryDao.getInventoryByProductId(id);
    }

    public List<Inventory> getAllInventory() {
        return inventoryDao.getAllInventory();
    }

    public void updateInventory(Inventory inventory) {
       inventoryDao.updateInventory(inventory);
    }

    public void deleteInventory(int id) {
        inventoryDao.deleteInventory(id);
    }
}

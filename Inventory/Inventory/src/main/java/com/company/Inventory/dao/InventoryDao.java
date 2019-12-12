package com.company.Inventory.dao;

import com.company.Inventory.model.Inventory;

import java.util.List;

public interface InventoryDao {
    Inventory saveInventory(Inventory inventory);
    Inventory getInventoryById(int id);
    List<Inventory> getAllInventory();
    void updateInventory(Inventory inventory);
    void deleteInventory(int id);
}

package com.company.Inventory.dao;

import com.company.Inventory.dao.InventoryDao;
import com.company.Inventory.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InventoryDaoTest {

    @Autowired
    InventoryDao inventoryDao;


    @Before
    public void setUp() throws Exception {
        List<Inventory> inventoryList = inventoryDao.getAllInventory();
        for (Inventory inventory:
             inventoryList) {
            inventoryDao.deleteInventory(inventory.getInventoryId());
        }
    }

    @Test
    @Transactional
    public void createGetGetAllDeleteInventory(){
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        inventory = inventoryDao.saveInventory(inventory);

        assertEquals(inventory, inventoryDao.getInventoryById(inventory.getInventoryId()));

        assertEquals(1,inventoryDao.getAllInventory().size());

        inventoryDao.deleteInventory(inventory.getInventoryId());

        assertEquals(0,inventoryDao.getAllInventory().size());
    }

    @Test
    public void updateInventory() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        inventory = inventoryDao.saveInventory(inventory);

        inventory.setQuantity(100);

        inventoryDao.updateInventory(inventory);

        assertEquals(inventory.getQuantity(), inventoryDao.getInventoryById(inventory.getInventoryId()).getQuantity());

    }

    @Test
    public void getInventoryByProductId(){
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        inventory = inventoryDao.saveInventory(inventory);



        assertEquals(inventory, inventoryDao.getInventoryByProductId(inventory.getProductId()).get(0));

    }

}
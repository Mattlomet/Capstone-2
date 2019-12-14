package com.company.Inventory.service;

import com.company.Inventory.dao.InventoryDao;
import com.company.Inventory.model.Inventory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceLayer serviceLayer;

    @MockBean
    InventoryDao inventoryDao;

    @Before
    public void setUp() throws Exception {
        inventoryDaoMocks();
    }

    @Test
    @Transactional
    public void saveInventoryGetInventoryById() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        inventory = serviceLayer.saveInventory(inventory);

        assertEquals(inventory, serviceLayer.getInventoryById(inventory.getInventoryId()));
    }

    @Test
    public void getAllInventory() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        inventory = serviceLayer.saveInventory(inventory);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory);

        assertEquals(inventoryList, serviceLayer.getAllInventory());
    }

    @Test
    @Transactional
    public void getInventoryByProductId() {
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        inventory = serviceLayer.saveInventory(inventory);

        assertEquals(inventory, serviceLayer.getInventoryByProductId(inventory.getProductId()).get(0));
    }


    public void inventoryDaoMocks(){
        Inventory inventory = new Inventory();
        inventory.setQuantity(10);
        inventory.setProductId(1);

        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setQuantity(10);
        inventory1.setProductId(1);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory1);

        doReturn(inventory1).when(inventoryDao).saveInventory(inventory);
        doReturn(inventory1).when(inventoryDao).getInventoryById(1);
        doReturn(inventoryList).when(inventoryDao).getInventoryByProductId(inventory1.getProductId());
        doReturn(inventoryList).when(inventoryDao).getAllInventory();
    }
}
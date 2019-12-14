package com.company.Product.service;

import com.company.Product.dao.ProductDao;
import com.company.Product.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceLayer serviceLayer;

    @MockBean
    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        ProductDaoMocks();
    }

    @Test
    @Transactional
    public void saveProductGetProductById() {
        Product product = new Product();
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("999.99").setScale(2));
        product.setUnitCost(new BigDecimal("111.11").setScale(2));

        product = serviceLayer.saveProduct(product);

        assertEquals(product, serviceLayer.getProductById(product.getProductId()));
    }

    @Test
    public void getAllProducts() {
        Product product = new Product();
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("999.99").setScale(2));
        product.setUnitCost(new BigDecimal("111.11").setScale(2));

        product = serviceLayer.saveProduct(product);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        assertEquals(productList, serviceLayer.getAllProducts());
    }


    public void ProductDaoMocks(){
        Product product = new Product();
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("999.99").setScale(2));
        product.setUnitCost(new BigDecimal("111.11").setScale(2));

        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductName("Kung Fu Download");
        product1.setProductDescription("Martial Art");
        product1.setListPrice(new BigDecimal("999.99").setScale(2));
        product1.setUnitCost(new BigDecimal("111.11").setScale(2));

        List<Product> productList = new ArrayList<>();
        productList.add(product1);

        doReturn(product1).when(productDao).saveProduct(product);
        doReturn(product1).when(productDao).getProductById(1);
        doReturn(productList).when(productDao).getAllProducts();
    }
}
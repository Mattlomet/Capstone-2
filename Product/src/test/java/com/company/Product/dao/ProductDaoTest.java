package com.company.Product.dao;

import com.company.Product.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductDaoTest {

    @Autowired
    ProductDao productDao;

    @Before
    public void setUp() throws Exception {
        List<Product> productList = productDao.getAllProducts();
        for (Product product:
            productList) {
            productDao.deleteProduct(product.getProductId());
        }
    }

    @Test
    @Transactional
    public void createGetGetAllDeleteProduct() {
        Product product = new Product();
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("999.99").setScale(2));
        product.setUnitCost(new BigDecimal("111.11").setScale(2));

        product = productDao.saveProduct(product);

        assertEquals(product, productDao.getProductById(product.getProductId()));

        assertEquals(1,productDao.getAllProducts().size());

        productDao.deleteProduct(product.getProductId());

        assertEquals(0,productDao.getAllProducts().size());

    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("999.99").setScale(2));
        product.setUnitCost(new BigDecimal("111.11").setScale(2));

        product = productDao.saveProduct(product);

        product.setProductName("Jiu Jitsu Download");

        productDao.updateProduct(product);

        assertEquals(product.getProductName(), productDao.getProductById(product.getProductId()).getProductName());
    }
}
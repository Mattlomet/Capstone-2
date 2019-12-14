package com.company.Product.service;

import com.company.Product.dao.ProductDao;
import com.company.Product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ServiceLayer {

    @Autowired
    private ProductDao productDao;

    public Product saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }




}

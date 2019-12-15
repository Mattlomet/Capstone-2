package com.company.Product.dao;

import com.company.Product.model.Product;

import java.util.List;

public interface ProductDao {

    Product saveProduct(Product product);
    Product getProductById(int id);
//    List<Product> getProductByInvoiceId(int invoiceId);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int id);

}

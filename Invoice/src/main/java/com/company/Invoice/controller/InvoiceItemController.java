package com.company.Invoice.controller;


import com.company.Invoice.model.InvoiceItem;
import com.company.Invoice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"invoiceItem"})
public class InvoiceItemController {
    
    @Autowired
    ServiceLayer serviceLayer;
    
    // GET METHODS

    @Cacheable
    @GetMapping("/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceItem getInvoiceItemById(@PathVariable int id){
        return serviceLayer.getInvoiceItemById(id);
    }

    @Cacheable
    @GetMapping("/invoiceItem/all")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceItem> getAllInvoiceItem(){
        return serviceLayer.getAllInvoiceItem();
    }


    // POST METHODS

    @CachePut(key = "#result.getInvoiceItemId()")
    @PostMapping("/invoiceItem")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceItem saveInvoiceItem(@RequestBody InvoiceItem invoiceItem){
        return serviceLayer.saveInvoiceItem(invoiceItem);
    }

    // UPDATE METHODS

    @CacheEvict(key = "#result.getInvoiceItemId()")
    @PutMapping("/invoiceItem")
    @ResponseStatus(HttpStatus.OK)
    public void updateInvoiceItem(@RequestBody InvoiceItem invoiceItem){
        serviceLayer.updateInvoiceItem(invoiceItem);
    }

    // DELETE METHODS

    @CacheEvict
    @DeleteMapping("/invoiceItem/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvoiceItem(@PathVariable int id){
        serviceLayer.deleteInvoiceItem(id);
    }
}

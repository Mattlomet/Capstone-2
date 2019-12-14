package com.company.Invoice.controller;


import com.company.Invoice.model.Invoice;
import com.company.Invoice.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class InvoiceController {
    
    @Autowired
    ServiceLayer serviceLayer;

    // GET METHODS

    @GetMapping("/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Invoice getInvoiceById(@PathVariable int id){
        return serviceLayer.getInvoiceById(id);
    }

    @GetMapping("/invoice")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoice(){
        return serviceLayer.getAllInvoice();
    }

    @GetMapping("/invoice/customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getInvoiceByCustomerId(@PathVariable int id){
        return serviceLayer.getInvoicesByCustomerId(id);
    }


    // POST METHODS

    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice saveInvoice(@RequestBody Invoice invoice){
        return serviceLayer.saveInvoice(invoice);
    }

    // UPDATE METHODS

    @PutMapping("/invoice")
    @ResponseStatus(HttpStatus.OK)
    public void updateInvoice(@RequestBody Invoice invoice){
        serviceLayer.updateInvoice(invoice);
    }

    // DELETE METHODS

    @DeleteMapping("/invoice/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvoice(@PathVariable int id){
        serviceLayer.deleteInvoice(id);
    }
}

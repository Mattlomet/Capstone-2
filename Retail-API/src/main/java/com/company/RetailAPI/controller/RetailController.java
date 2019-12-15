package com.company.RetailAPI.controller;


import com.company.RetailAPI.service.ServiceLayer;
import com.company.RetailAPI.viewmodel.InvoiceViewModel;
import com.company.RetailAPI.viewmodel.ProductViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
public class RetailController {

    @Autowired
    ServiceLayer serviceLayer;

    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    public InvoiceViewModel submitInvoice(@RequestBody @Valid InvoiceViewModel invoiceViewModel) {
        return serviceLayer.submitInvoice(invoiceViewModel);
    }

    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public InvoiceViewModel getInvoiceById(@PathVariable int id) {
        return serviceLayer.getInvoiceById(id);
    }

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public List<InvoiceViewModel> getAllInvoices() {
        return serviceLayer.getAllInvoices();
    }

    @RequestMapping(value = "/invoices/customer/{id}", method = RequestMethod.GET)
    public List<InvoiceViewModel> getInvoicesByCustomerId(@PathVariable int id) {
        return serviceLayer.getInvoicesByCustomerId(id);
    }

    @RequestMapping(value = "/products/inventory", method = RequestMethod.GET)
    public List<ProductViewModel> getProductsInInventory() {
        return serviceLayer.getAllProducts();
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductViewModel getProductById(@PathVariable int id){
        return serviceLayer.getProductById(id);
    }

//    @RequestMapping(value = "/products/invoice/{invoiceId}", method = RequestMethod.GET)
//    public List<ProductViewModel> getProductByInvoiceId(@PathVariable int invoiceId) {
//        return serviceLayer.getProductByInvoiceId(invoiceId);
//    }

    @RequestMapping(value = "/levelup/customer/{id}", method = RequestMethod.GET)
    public int getLevelUpPointsByCustomerId(int id) {
        return serviceLayer.getLevelUpPointsByCustomerId(id);
    }

}

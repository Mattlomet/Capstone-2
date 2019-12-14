package com.company.RetailAPI.util.feign;


import com.company.RetailAPI.model.Invoice;
import com.company.RetailAPI.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "invoice-service")
public interface InvoiceFeign {

    @GetMapping("/invoice/{id}")
    Invoice getInvoiceById(@PathVariable int id);

    @GetMapping("/invoice")
    List<Invoice> getAllInvoice();

    @GetMapping("/invoice/customer/{id}")
    List<Invoice> getInvoiceByCustomerId(@PathVariable int id);

    @PostMapping("/invoice")
    Invoice createInvoice(@RequestBody Invoice invoice);

    @GetMapping("/invoiceItem/{id}")
    InvoiceItem getInvoiceItemById(@PathVariable int id);
}

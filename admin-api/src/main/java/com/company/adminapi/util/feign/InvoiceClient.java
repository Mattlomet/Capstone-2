package com.company.adminapi.util.feign;

import com.company.adminapi.model.Invoice;
import com.company.adminapi.model.InvoiceItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "INVOICE-SERVICE")
public interface InvoiceClient {

    @RequestMapping(value = "/invoice",method = RequestMethod.POST)
    public Invoice createInvoice(@RequestBody Invoice invoice);

    @RequestMapping(value = "/invoice/{id}",method = RequestMethod.GET)
    public Invoice getInvoice(@PathVariable int id);

    @RequestMapping(value = "/invoice",method = RequestMethod.GET)
    public List<Invoice> getAllInvoices();

    @RequestMapping(value = "/invoice/{id}",method = RequestMethod.PUT)
    public void updateInvoice(@PathVariable int id, @RequestBody Invoice invoice);

    @RequestMapping(value = "/invoice/{id}",method = RequestMethod.DELETE)
    public void deleteInvoice(@PathVariable int id);

    @RequestMapping(value = "/invoiceitem",method = RequestMethod.POST)
    public InvoiceItem createInvoiceItem(@RequestBody InvoiceItem invoiceItem);

    @RequestMapping(value = "/invoiceitem/{id}",method = RequestMethod.GET)
    public InvoiceItem getInvoiceItem(@PathVariable int id);

    @RequestMapping(value = "/invoiceitem/invoice/{id}",method = RequestMethod.GET)
    public List<InvoiceItem> getInvoiceItemsByInvoiceId(@PathVariable int id);

    @RequestMapping(value = "/invoiceitem",method = RequestMethod.GET)
    public List<InvoiceItem> getAllInvoiceItems();

    @RequestMapping(value = "/invoiceitem/{id}",method = RequestMethod.PUT)
    public void updateInvoiceItem(@PathVariable int id, @RequestBody InvoiceItem invoiceItem);

    @RequestMapping(value = "/invoiceitem/{id}",method = RequestMethod.DELETE)
    public void deleteInvoiceItem(@PathVariable int id);

}

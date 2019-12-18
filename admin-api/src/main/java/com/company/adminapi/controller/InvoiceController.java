package com.company.adminapi.controller;

import com.company.adminapi.exception.NotFoundException;
import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.viewmodel.OrderViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/invoice")
@CacheConfig(cacheNames = {"invoice"})
public class InvoiceController {
    @Autowired
    ServiceLayer service;

    @CachePut(key = "#result.getInvoiceId()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderViewModel createOrder(@RequestBody @Valid OrderViewModel ovm) {
        return service.createOrder(ovm);
    }

    @Cacheable
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderViewModel getOrder(@PathVariable int id) {
        OrderViewModel ovm = service.getOrder(id);
        if(ovm == null)
            throw new NotFoundException("There is no invoice with this id " + id + ".");

        return ovm;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderViewModel> getAllOrders() {
        List<OrderViewModel> ovmList = service.getAllOrders();
        if(ovmList.size() == 0)
            throw new NotFoundException("No invoices found.");

        return ovmList;
    }

    @CacheEvict(key = "#invoice.getInvoiceId()")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrder(@PathVariable int id, @RequestBody @Valid OrderViewModel ovm) {
        if(ovm.getInvoiceId() == 0)
            ovm.setInvoiceId(id);
        if(ovm.getInvoiceId() != id)
            throw new IllegalArgumentException("Invoice ID must match.");

        service.updateOrder(id,ovm);
    }

    @CacheEvict
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable int id) {
        service.deleteOrder(id);
    }
}

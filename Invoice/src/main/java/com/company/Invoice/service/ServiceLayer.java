package com.company.Invoice.service;

import com.company.Invoice.dao.InvoiceDao;
import com.company.Invoice.dao.InvoiceItemDao;
import com.company.Invoice.model.Invoice;
import com.company.Invoice.model.InvoiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceLayer {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    InvoiceItemDao invoiceItemDao;

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceDao.saveInvoice(invoice);
    }

    public Invoice getInvoiceById(int id) {
        return invoiceDao.getInvoiceById(id);
    }

    public List<Invoice> getAllInvoice() {
        return invoiceDao.getAllInvoices();
    }

    public List<Invoice> getInvoicesByCustomerId(int id){
        return invoiceDao.getInvoiceByCustomerId(id);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceDao.updateInvoice(invoice);
    }

    public void deleteInvoice(int id) {
        invoiceDao.deleteInvoice(id);
    }

    public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {
        return invoiceItemDao.saveInvoiceItem(invoiceItem);
    }

    public InvoiceItem getInvoiceItemById(int id) {
        return invoiceItemDao.getInvoiceItemById(id);
    }

    public List<InvoiceItem> getAllInvoiceItem() {
        return invoiceItemDao.getAllInvoiceItems();
    }

    public void updateInvoiceItem(InvoiceItem invoiceItem) {
        invoiceItemDao.updateInvoiceItem(invoiceItem);
    }

    public void deleteInvoiceItem(int id) {
        invoiceItemDao.deleteInvoiceItem(id);
    }
}

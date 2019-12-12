package com.company.Invoice.dao;

import com.company.Invoice.model.InvoiceItem;

import java.util.List;

public interface InvoiceItemDao {
    InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem);
    InvoiceItem getInvoiceItemById(int id);
    List<InvoiceItem> getAllInvoiceItems();
    void updateInvoiceItem(InvoiceItem invoiceItem);
    void deleteInvoiceItem(int id);
}

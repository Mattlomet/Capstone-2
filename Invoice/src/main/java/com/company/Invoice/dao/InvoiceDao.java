package com.company.Invoice.dao;

import com.company.Invoice.model.Invoice;

import java.util.List;

public interface InvoiceDao {
    Invoice saveInvoice(Invoice invoice);
    Invoice getInvoiceById(int id);
    List<Invoice> getAllInvoices();
    void updateInvoice(Invoice invoice);
    void deleteInvoice(int id);
    List<Invoice> getInvoiceByCustomerId(int customerId);
}

package com.company.Invoice.dao;

import com.company.Invoice.model.Invoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceDaoTest {

    @Autowired
    InvoiceDao invoiceDao;

    @Before
    public void setUp() throws Exception {
        List<Invoice> invoiceList = invoiceDao.getAllInvoices();
        for (Invoice invoice:
             invoiceList) {
            invoiceDao.deleteInvoice(invoice.getInvoiceId());
        }
    }

    @Test
    @Transactional
    public void createGetGetAllDeleteInvoice(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = invoiceDao.saveInvoice(invoice);

        assertEquals(invoice, invoiceDao.getInvoiceById(invoice.getInvoiceId()));

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        assertEquals(invoiceList, invoiceDao.getAllInvoices());

        invoiceDao.deleteInvoice(invoice.getInvoiceId());

        assertEquals(0, invoiceDao.getAllInvoices().size());
    }

    @Test
    public void updateInvoice() {
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = invoiceDao.saveInvoice(invoice);

        invoice.setPurchaseDate(LocalDate.of(10,10,11));

        invoiceDao.updateInvoice(invoice);

        assertEquals(invoice,invoiceDao.getInvoiceById(invoice.getInvoiceId()));
    }

    @Test
    public void getInvoiceByCustomerId(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = invoiceDao.saveInvoice(invoice);

        assertEquals(invoice, invoiceDao.getInvoiceByCustomerId(invoice.getCustomerId()).get(0));
    }

}
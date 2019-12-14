package com.company.Invoice.dao;


import com.company.Invoice.model.Invoice;
import com.company.Invoice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceItemDaoTest {

    @Autowired
    InvoiceItemDao invoiceItemDao;

    @Autowired
    InvoiceDao invoiceDao;


    @Before
    public void setUp() throws Exception {

        List<InvoiceItem> invoiceItemList = invoiceItemDao.getAllInvoiceItems();
        for (InvoiceItem invoiceItem:
                invoiceItemList) {
            invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());
        }

        invoiceDao.getAllInvoices().stream().forEach(invoice -> invoiceDao.deleteInvoice(invoice.getInvoiceId()));
    }

    @Test
    public void createGetGetAllDeleteInvoiceItem(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = invoiceDao.saveInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setUnitPrice(new BigDecimal("10.00"));
        invoiceItem.setQuantity(10);


        invoiceItem = invoiceItemDao.saveInvoiceItem(invoiceItem);

        assertEquals(invoiceItem, invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId()));

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem);

        assertEquals(invoiceItemList, invoiceItemDao.getAllInvoiceItems());



        invoiceItemDao.deleteInvoiceItem(invoiceItem.getInvoiceItemId());

        assertEquals(0,invoiceItemDao.getAllInvoiceItems().size());
    }

    @Test
    @Transactional
    public void updateInvoiceItem() {
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = invoiceDao.saveInvoice(invoice);

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(invoice.getInvoiceId());
        invoiceItem.setInventoryId(1);
        invoiceItem.setUnitPrice(BigDecimal.valueOf(10.00));
        invoiceItem.setQuantity(10);

        invoiceItem = invoiceItemDao.saveInvoiceItem(invoiceItem);

        invoiceItem.setQuantity(100);

        invoiceItemDao.updateInvoiceItem(invoiceItem);

        assertEquals(invoiceItem.getQuantity(),invoiceItemDao.getInvoiceItemById(invoiceItem.getInvoiceItemId()).getQuantity());
    }
}

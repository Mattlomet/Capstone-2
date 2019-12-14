package com.company.Invoice.service;


import com.company.Invoice.dao.InvoiceDao;
import com.company.Invoice.dao.InvoiceItemDao;
import com.company.Invoice.dao.InvoiceItemDaoImpl;
import com.company.Invoice.model.Invoice;
import com.company.Invoice.model.InvoiceItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceLayer serviceLayer;

    @MockBean
    InvoiceDao invoiceDao;

    @MockBean
    InvoiceItemDao invoiceItemDao;

    @Before
    public void setUp(){
        setUpInvoiceItemsMocks();
        setUpInvoiceMocks();
    }

    @Test
    public void saveGetGetAllInvoice(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = serviceLayer.saveInvoice(invoice);

        assertEquals(invoice, serviceLayer.getInvoiceById(invoice.getInvoiceId()));

        assertEquals(1, serviceLayer.getAllInvoice().size());
    }

    @Test
    public void getInvoiceByCustomerId(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        invoice = serviceLayer.saveInvoice(invoice);

        assertEquals(invoice, serviceLayer.getInvoicesByCustomerId(invoice.getInvoiceId()).get(0));
    }

    @Test
    public void saveGetGetAllInvoiceItem(){
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setUnitPrice(new BigDecimal("10.00"));
        invoiceItem.setQuantity(10);


        invoiceItem = serviceLayer.saveInvoiceItem(invoiceItem);
        System.out.println(invoiceItem);


        assertEquals(invoiceItem, serviceLayer.getInvoiceItemById(invoiceItem.getInvoiceItemId()));

        assertEquals(1, serviceLayer.getAllInvoiceItem().size());
    }


    public void setUpInvoiceMocks(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.of(10,10,10));
        invoice.setCustomerId(1);

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setPurchaseDate(LocalDate.of(10,10,10));
        invoice1.setCustomerId(1);

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice1);

        doReturn(invoice1).when(invoiceDao).saveInvoice(invoice);
        doReturn(invoice1).when(invoiceDao).getInvoiceById(invoice1.getInvoiceId());
        doReturn(invoiceList).when(invoiceDao).getAllInvoices();
        doReturn(invoiceList).when(invoiceDao).getInvoiceByCustomerId(invoice1.getCustomerId());

    }

    public void setUpInvoiceItemsMocks(){

        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setUnitPrice(new BigDecimal("10.00"));
        invoiceItem.setQuantity(10);

        InvoiceItem invoiceItem1 = new InvoiceItem();
        invoiceItem.setInvoiceItemId(1);
        invoiceItem.setInvoiceId(1);
        invoiceItem.setInventoryId(1);
        invoiceItem.setUnitPrice(new BigDecimal("10.00"));
        invoiceItem.setQuantity(10);

        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        invoiceItemList.add(invoiceItem1);

        doReturn(invoiceItem1).when(invoiceItemDao).saveInvoiceItem(invoiceItem);
        doReturn(invoiceItem1).when(invoiceItemDao).getInvoiceItemById(invoiceItem1.getInvoiceItemId());
        doReturn(invoiceItemList).when(invoiceItemDao).getAllInvoiceItems();
    }
}

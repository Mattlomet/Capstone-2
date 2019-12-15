package com.company.RetailAPI.service;

import com.company.RetailAPI.model.*;
import com.company.RetailAPI.util.feign.*;
import com.company.RetailAPI.viewmodel.InventoryViewModel;
import com.company.RetailAPI.viewmodel.InvoiceViewModel;
import com.company.RetailAPI.viewmodel.LevelUpViewModel;
import com.company.RetailAPI.viewmodel.ProductViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceLayerTest {

    @Autowired
    ServiceLayer serviceLayer;

    @MockBean
    CustomerFeign customerFeign;

    @MockBean
    LevelUpFeign levelUpFeign;

    @MockBean
    ProductFeign productFeign;

    @MockBean
    InvoiceFeign invoiceFeign;

    @MockBean
    InventoryFeign inventoryFeign;

    @MockBean
    RabbitTemplate rabbitTemplate;


    @Before
    public void setUp(){
        customerFeignMocks();
        inventoryFeignMocks();
        invoiceFeignMocks();
        levelUpFeignMocks();
        productFeignMocks();
    }


    @Test
    public void submitInvoiceTest(){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice.setCustomerId(1);


        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("50.00").setScale(2));
        product.setUnitCost(new BigDecimal("50.00").setScale(2));

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setPurchaseProduct(productList);
        ivm.setLevelUpPoints(0);

        InvoiceViewModel ivm1 = new InvoiceViewModel();
        ivm1.setInvoiceId(invoice.getInvoiceId());
        ivm1.setCustomerId(invoice.getCustomerId());
        ivm1.setPurchaseDate(invoice.getPurchaseDate());
        ivm1.setPurchaseProduct(productList);
        ivm1.setLevelUpPoints(10);


        assertEquals(ivm1,serviceLayer.submitInvoice(ivm));
    }


    @Test
    public void getInvoiceByIdTest(){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice.setCustomerId(1);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setPurchaseProduct(null);
        ivm.setLevelUpPoints(10);

        assertEquals(ivm,serviceLayer.getInvoiceById(invoice.getInvoiceId()));
    }

    @Test
    public void getAllInvoicesTest(){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice.setCustomerId(1);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setPurchaseProduct(null);
        ivm.setLevelUpPoints(10);

        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
        invoiceViewModelList.add(ivm);

        assertEquals(invoiceViewModelList,serviceLayer.getAllInvoices());
    }

    @Test
    public void getInvoicesByCustomerIdTest(){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setPurchaseDate(LocalDate.now());
        invoice.setCustomerId(1);

        InvoiceViewModel ivm = new InvoiceViewModel();
        ivm.setInvoiceId(invoice.getInvoiceId());
        ivm.setCustomerId(invoice.getCustomerId());
        ivm.setPurchaseDate(invoice.getPurchaseDate());
        ivm.setPurchaseProduct(null);
        ivm.setLevelUpPoints(10);

        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
        invoiceViewModelList.add(ivm);

        assertEquals(invoiceViewModelList,serviceLayer.getInvoicesByCustomerId(invoice.getCustomerId()));
    }


    @Test
    public void getAllProductsTest(){
        ProductViewModel pvm = new ProductViewModel();
        pvm.setProductId(1);
        pvm.setProductName("Kung Fu Download");
        pvm.setProductDescription("Martial Art");
        pvm.setListPrice(new BigDecimal("50.00").setScale(2));
        pvm.setUnitCost(new BigDecimal("50.00").setScale(2));

        List<ProductViewModel> productViewModelList = new ArrayList<>();
        productViewModelList.add(pvm);

        assertEquals(productViewModelList,serviceLayer.getAllProducts());
    }


    @Test
    public void getProductByIdTest(){
        ProductViewModel pvm = new ProductViewModel();
        pvm.setProductId(1);
        pvm.setProductName("Kung Fu Download");
        pvm.setProductDescription("Martial Art");
        pvm.setListPrice(new BigDecimal("50.00").setScale(2));
        pvm.setUnitCost(new BigDecimal("50.00").setScale(2));

        assertEquals(pvm,serviceLayer.getProductById(pvm.getProductId()));

    }


    @Test
    public void getLevelUpPointsByCustomerIdTest(){
        LevelUpViewModel luvm = new LevelUpViewModel();
        luvm.setLevelUpId(1);
        luvm.setPoints(10);
        luvm.setMemberDate(LocalDate.now());
        luvm.setCustomerId(1);

        assertEquals(luvm.getPoints(), serviceLayer.getLevelUpPointsByCustomerId(luvm.getCustomerId()));
    }





    public void customerFeignMocks(){
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Neo");
        customer1.setLastName("Anderson");
        customer1.setStreet("123 Underground Cave");
        customer1.setCity("Machine City");
        customer1.setZip("12345");
        customer1.setEmail("the_one@freeyourmind.com");
        customer1.setPhone("987-654-3210");

        doReturn(customer1).when(customerFeign).getCustomerById(customer1.getCustomerId());
    }

    public void inventoryFeignMocks(){
        Inventory inventory1 = new Inventory();
        inventory1.setInventoryId(1);
        inventory1.setQuantity(10);
        inventory1.setProductId(1);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryList.add(inventory1);

        doReturn(inventoryList).when(inventoryFeign).getInventoryByProductId(inventory1.getProductId());
    }

    public void invoiceFeignMocks(){
        Invoice invoice = new Invoice();
        invoice.setPurchaseDate(LocalDate.now());
        invoice.setCustomerId(1);

        Invoice invoice1 = new Invoice();
        invoice1.setInvoiceId(1);
        invoice1.setPurchaseDate(LocalDate.now());
        invoice1.setCustomerId(1);

        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice1);

        doReturn(invoice1).when(invoiceFeign).createInvoice(invoice);
        doReturn(invoice1).when(invoiceFeign).getInvoiceById(invoice1.getInvoiceId());
        doReturn(invoiceList).when(invoiceFeign).getInvoiceByCustomerId(invoice1.getCustomerId());
        doReturn(invoiceList).when(invoiceFeign).getAllInvoice();
    }

    public void levelUpFeignMocks(){
        LevelUp levelUp1 = new LevelUp();
        levelUp1.setLevelUpId(1);
        levelUp1.setPoints(10);
        levelUp1.setMemberDate(LocalDate.now());
        levelUp1.setCustomerId(1);

        doReturn(levelUp1).when(levelUpFeign).getLevelUpByCustomerId(levelUp1.getCustomerId());
    }

    public void productFeignMocks(){
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Kung Fu Download");
        product.setProductDescription("Martial Art");
        product.setListPrice(new BigDecimal("50.00").setScale(2));
        product.setUnitCost(new BigDecimal("50.00").setScale(2));

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        doReturn(product).when(productFeign).getProductById(product.getProductId());
        doReturn(productList).when(productFeign).getAllProducts();
    }

}

package com.company.adminapi.service;

import com.company.adminapi.model.*;
import com.company.adminapi.service.ServiceLayer;
import com.company.adminapi.util.feign.*;
import com.company.adminapi.viewmodel.OrderViewModel;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ServiceLayerTest {
    private ServiceLayer service;
    private CustomerClient customerClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;

    Customer customer;
    Customer customer1;
    List<Customer> customers;

    Inventory inventory;
    Inventory inventory1;
    List<Inventory> inventories;

    Invoice invoice;
    Invoice invoice1;
    List<Invoice> invoices;

    InvoiceItem item;
    InvoiceItem item1;
    List<InvoiceItem> items;
    List<InvoiceItem> items1;

    LevelUp levelUp;
    LevelUp levelUp1;
    List<LevelUp> levelUps;

    Product product;
    Product product1;
    List<Product> products;

    @Before
    public void setUp() throws Exception {
        setUpMockObjects();
        setUpCustomerClientMock();
        setUpInventoryClientMock();
        setUpInvoiceClientMock();
        setUpLevelUpClientMock();
        setUpProductClientMockMock();

        service = new ServiceLayer(customerClient,inventoryClient,invoiceClient,levelUpClient,productClient);

    }

    public void setUpMockObjects(){
        customer1 = new Customer();
        customer1.setFirstName("Jack");
        customer1.setLastName("Sparrow");
        customer1.setStreet("123 Ocean Lane");
        customer1.setCity("Atlantis");
        customer1.setZip("99999");
        customer1.setEmail("pirate@gmail.com");
        customer1.setPhone("1-800-123-4567");

        customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Jack");
        customer.setLastName("Sparrow");
        customer.setStreet("123 Ocean Lane");
        customer.setCity("Atlantis");
        customer.setZip("11233");
        customer.setEmail("pirate@gmail.com");
        customer.setPhone("1-800-123-4567");

        customers = Arrays.asList(customer);

        inventory = new Inventory();
        inventory.setId(1);
        inventory.setProductId(1);
        inventory.setQuantity(9);

        inventory1 = new Inventory();
        inventory1.setProductId(1);
        inventory1.setQuantity(9);

        inventories = Arrays.asList(inventory);

        invoice = new Invoice();
        invoice.setId(1);
        invoice.setCustomerId(1);
        invoice.setPurchaseDate(LocalDate.of(2019,1,9));

        invoice1 = new Invoice();
        invoice1.setCustomerId(1);
        invoice1.setPurchaseDate(LocalDate.of(2019,1,9));

        invoices = Arrays.asList(invoice);

        item = new InvoiceItem();
        item.setId(1);
        item.setInvoiceId(invoice.getId());
        item.setInventoryId(1);
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("7.0"));

        item1 = new InvoiceItem();
        item1.setInvoiceId(invoice.getId());
        item1.setInventoryId(1);
        item1.setQuantity(1);
        item1.setUnitPrice(new BigDecimal("7.0"));

        items = Arrays.asList(item);
        items1 = Arrays.asList(item1);

        levelUp = new LevelUp();
        levelUp.setId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(100);
        levelUp.setMemberDate(LocalDate.of(2019,12,12));

        levelUp1 = new LevelUp();
        levelUp1.setCustomerId(1);
        levelUp1.setPoints(100);
        levelUp1.setMemberDate(LocalDate.of(2019,12,12));

        levelUps = Arrays.asList(levelUp);

        product = new Product();
        product.setId(1);
        product.setProductName("007");
        product.setProductDescription("Shooter");
        product.setListPrice(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP));
        product.setUnitCost(BigDecimal.valueOf(120.00).setScale(2, RoundingMode.HALF_UP));

        product1 = new Product();
        product1.setProductName("007");
        product1.setProductDescription("Shooter");
        product1.setListPrice(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP));
        product1.setUnitCost(BigDecimal.valueOf(120.00).setScale(2, RoundingMode.HALF_UP));

        products = Arrays.asList(product);

    }

    public void setUpCustomerClientMock(){
        customerClient = mock(CustomerClient.class);

        doReturn(customer).when(customerClient).addCustomer(customer1);
        doReturn(customer).when(customerClient).getCustomer(1);
        doReturn(customers).when(customerClient).getAllCustomers();
    }

    public void setUpInventoryClientMock(){
        inventoryClient = mock(InventoryClient.class);

        doReturn(inventory).when(inventoryClient).addInventory(inventory1);
        doReturn(inventory).when(inventoryClient).getInventory(1);
        doReturn(inventories).when(inventoryClient).getAllInventories();
    }

    public void setUpInvoiceClientMock(){
        invoiceClient = mock(InvoiceClient.class);

        doReturn(invoice).when(invoiceClient).createInvoice(invoice1);
        doReturn(invoice).when(invoiceClient).getInvoice(1);
        doReturn(invoices).when(invoiceClient).getAllInvoices();

        doReturn(item).when(invoiceClient).createInvoiceItem(item1);
        doReturn(items).when(invoiceClient).getInvoiceItemsByInvoiceId(1);

    }

    public void setUpLevelUpClientMock(){
        levelUpClient = mock(LevelUpClient.class);

        doReturn(levelUp).when(levelUpClient).createLevelUp(levelUp1);
        doReturn(levelUp).when(levelUpClient).getLevelUp(1);
        doReturn(levelUps).when(levelUpClient).getAllLevelUps();
        doReturn(levelUp).when(levelUpClient).getLevelUpByCustomerId(1);

    }

    public void setUpProductClientMockMock(){
        productClient = mock(ProductClient.class);

        doReturn(product).when(productClient).addProduct(product1);
        doReturn(product).when(productClient).getProduct(1);
        doReturn(products).when(productClient).getAllProducts();

    }

    @Test
    public void shouldAddAndGetCustomerById(){
        customer = service.addCustomer(customer1);
        customer1 = new Customer();
        customer1 = service.getCustomer(customer.getId());

        assertEquals(customer, customer1);
    }

    @Test
    public void shouldGetAllCustomers(){
        assertEquals(customers, service.getAllCustomers());
    }

    @Test
    public void shouldAddAndGetInventoryById(){
        inventory = inventoryClient.addInventory(inventory1);
        inventory1 = new Inventory();
        inventory1 = service.getInventory(inventory.getId());

        assertEquals(inventory,inventory1);
    }

    @Test
    public void shouldGetAllInventories(){
        assertEquals(inventories, service.getAllInventories());
    }

    @Test
    public void shouldAggAndGetOrderById(){
        OrderViewModel ovm = new OrderViewModel();

        ovm.setCustomer(customer);
        ovm.setPurchaseDate(invoice.getPurchaseDate());
        ovm.setInvoiceItems(items1);

        ovm = service.createOrder(ovm);

        assertEquals(ovm, service.getOrder(ovm.getInvoiceId()));
    }


    @Test
    public void shouldReturnAllOrders(){

        assertEquals(1,service.getAllOrders().size());
    }

    @Test
    public void shouldAddAndGetLevelUpById(){
        levelUp = service.createLevelUp(levelUp1);
        levelUp1 = new LevelUp();
        levelUp1 = service.getLevelUp(levelUp.getId());

        assertEquals(levelUp, levelUp1);
    }

    @Test
    public void shouldGetAllLevelUps(){
        assertEquals(levelUps, service.getAllLevelUps());
    }

    @Test
    public void shouldAddAndGetProductById(){
        product = service.addProduct(product1);
        product1 = new Product();
        product1 = service.getProduct(product.getId());

        assertEquals(product, product1);
    }

    @Test
    public void shouldGetAllProducts(){
        assertEquals(products, service.getAllProducts());
    }
}
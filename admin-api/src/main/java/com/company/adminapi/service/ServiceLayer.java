package com.company.adminapi.service;

import com.company.adminapi.exception.InvalidOrderException;
import com.company.adminapi.model.*;
import com.company.adminapi.util.feign.*;
import com.company.adminapi.viewmodel.OrderViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {
    private CustomerClient customerClient;
    private InventoryClient inventoryClient;
    private InvoiceClient invoiceClient;
    private LevelUpClient levelUpClient;
    private ProductClient productClient;


    @Autowired
    public ServiceLayer(CustomerClient customerClient, InventoryClient inventoryClient, InvoiceClient invoiceClient, LevelUpClient levelUpClient, ProductClient productClient) {
        this.customerClient = customerClient;
        this.inventoryClient = inventoryClient;
        this.invoiceClient = invoiceClient;
        this.levelUpClient = levelUpClient;
        this.productClient = productClient;
    }
    public Customer addCustomer(@RequestBody Customer customer){
        return customerClient.addCustomer(customer);
    }

    public Customer getCustomer(int id){ return  customerClient.getCustomer(id);}

    public List<Customer> getAllCustomers(){ return customerClient.getAllCustomers();}

    public void updateCustomer(int id,Customer customer){ customerClient.updateCustomer(id,customer); }

    public void deleteCustomer(int id){
        customerClient.deleteCustomer(id);
    }

    public Inventory addInventory(Inventory inventory){
        return inventoryClient.addInventory(inventory);
    }


    public Inventory getInventory( int id){ return inventoryClient.getInventory(id); }

    public List<Inventory> getAllInventories(){ return inventoryClient.getAllInventories(); }

    public void updateInventory(int id, Inventory inventory){ inventoryClient.updateInventory(id,inventory); }

    public void deleteInventory(int id){
        inventoryClient.deleteInventory(id);
    }

    public OrderViewModel createOrder(OrderViewModel ovm) {
        BigDecimal total = new BigDecimal("0.00");
        Invoice invoice = new Invoice();
        invoice.setCustomerId(ovm.getCustomer().getId());
        invoice.setPurchaseDate(ovm.getPurchaseDate());
        invoice = invoiceClient.createInvoice(invoice);

        ovm.setInvoiceId(invoice.getId());

        List<InvoiceItem> items = new ArrayList<>();

        if(ovm.getCustomer() == null || ovm.getCustomer() != customerClient.getCustomer(ovm.getCustomer().getId()))
            throw new InvalidOrderException("Enter a valid customer");


        ovm.getInvoiceItems().stream()
                .forEach(item -> {
                    Inventory inventory = inventoryClient.getInventory(item.getInventoryId());

                    if(inventory.getQuantity() < 1)
                        throw new InvalidOrderException("Item out of stock");
                    if(inventory.getQuantity() < item.getQuantity())
                        throw new InvalidOrderException("Selected item is out of stock: " + inventory.getProductId());
                } );

        for(InvoiceItem item : ovm.getInvoiceItems()) {

            Inventory inventory = inventoryClient.getInventory(item.getInventoryId());
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());

            BigDecimal subTotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())).setScale(2);
            total = subTotal.add(subTotal);
            items.add(invoiceClient.createInvoiceItem(item));
        }

        ovm.setInvoiceItems(items);
        ovm.setOrderTotal(total);

        LevelUp levelUp = levelUpClient.getLevelUpByCustomerId(ovm.getCustomer().getId());
        int points = calculatePoints(total);
        ovm.setAwardedPoints(points);
        levelUp.setPoints(points + levelUp.getPoints());
        levelUpClient.updateLevelUp(levelUp.getId(), levelUp);
        ovm.setMemberPoints(levelUp);

        return ovm;
    }

    private int calculatePoints(BigDecimal totalCost){
        int cost = totalCost.intValue();
        int rate = cost/50;
        int points = 10 * rate;

        return points;
    }

    public OrderViewModel getOrder(int id){
        Invoice invoice = invoiceClient.getInvoice(id);
        List<InvoiceItem> items = new ArrayList<>(invoiceClient.getInvoiceItemsByInvoiceId(id));
        BigDecimal total = new BigDecimal("0.00");
        LevelUp levelUp = levelUpClient.getLevelUpByCustomerId(invoice.getCustomerId());
        Customer customer = customerClient.getCustomer(invoice.getCustomerId());

        for(InvoiceItem item : items) {
            BigDecimal subTotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())).setScale(2);
            total = subTotal.add(subTotal);
        }

        int awardedPoints = calculatePoints(total);

        return buildOrderViewModel(invoice,items,customer,total,awardedPoints,levelUp);

    }

    public List<OrderViewModel> getAllOrders(){
        List<Invoice> invoiceList = invoiceClient.getAllInvoices();
        List<OrderViewModel> orders = new ArrayList<>();

        invoiceList.stream().forEach(invoice -> {

            List<InvoiceItem> items = invoiceClient.getInvoiceItemsByInvoiceId(invoice.getId());
            BigDecimal total = new BigDecimal("0.00");
            LevelUp levelUp = levelUpClient.getLevelUpByCustomerId(invoice.getCustomerId());
            Customer customer = customerClient.getCustomer(invoice.getCustomerId());


            for(InvoiceItem item : items) {
                BigDecimal subTotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())).setScale(2);
                total = subTotal.add(subTotal);
            }

            int awardedPoints = calculatePoints(total);

            orders.add(buildOrderViewModel(invoice,items,customer,total,awardedPoints,levelUp));
        });
        return orders;
    }

    public void updateOrder(int id, OrderViewModel ovm){
        BigDecimal total = new BigDecimal("0.00");
        List<InvoiceItem> items = new ArrayList<>();

        //Verify that the order has a valid customer
        if(ovm.getCustomer() == null || ovm.getCustomer() != customerClient.getCustomer(ovm.getCustomer().getId()))
            throw new InvalidOrderException("Enter a valid customer");

        Invoice invoice = new Invoice();
        invoice.setId(ovm.getInvoiceId());
        invoice.setCustomerId(ovm.getCustomer().getId());
        invoice.setPurchaseDate(ovm.getPurchaseDate());

        ovm.getInvoiceItems().stream()
                .forEach(item -> {
                    Inventory inventory = inventoryClient.getInventory(item.getInventoryId());

                    if(inventory.getQuantity() < 1)
                        throw new InvalidOrderException("Item out of stock");
                    if(inventory.getQuantity() < item.getQuantity())
                        throw new InvalidOrderException("Selected item is out of stock: " + inventory.getProductId());
                } );

        for(InvoiceItem item : ovm.getInvoiceItems()) {

            Inventory inventory = inventoryClient.getInventory(item.getInventoryId());
            inventory.setQuantity(inventory.getQuantity() - item.getQuantity());

            BigDecimal subTotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity())).setScale(2);
            total = subTotal.add(subTotal);
            items.add(invoiceClient.createInvoiceItem(item));

            invoiceClient.updateInvoiceItem(item.getId(), item);
        }

        invoiceClient.updateInvoice(invoice.getId(),invoice);

        LevelUp levelUp = levelUpClient.getLevelUpByCustomerId(ovm.getCustomer().getId());
        int points = calculatePoints(total);
        levelUp.setPoints(points + levelUp.getPoints());
        levelUpClient.updateLevelUp(levelUp.getId(), levelUp);

    }

    public void deleteOrder(int id){
        Invoice invoice = invoiceClient.getInvoice(id);
        List<InvoiceItem> items = invoiceClient.getInvoiceItemsByInvoiceId(id);

        items.stream()
                .forEach(item ->{
                    invoiceClient.deleteInvoiceItem(item.getId());
                });
        invoiceClient.deleteInvoice(invoice.getId());
    }


    public LevelUp createLevelUp(LevelUp levelUp) {
        return levelUpClient.createLevelUp(levelUp);
    }


    public LevelUp getLevelUp(int id) { return levelUpClient.getLevelUp(id); }


    public List<LevelUp> getAllLevelUps() { return levelUpClient.getAllLevelUps(); }


    public void updateLevelUp(int id,LevelUp levelUp) { levelUpClient.updateLevelUp(id,levelUp); }


    public void deleteLevelUp(int id) { levelUpClient.deleteLevelUp(id); }


    public Product addProduct(Product product){
        return productClient.addProduct(product);
    }


    public List<Product> getAllProducts(){ return productClient.getAllProducts(); }


    public Product getProduct(int id){ return productClient.getProduct(id); }

    public void updateProduct(int id, Product product){ productClient.updateProduct(id,product); }


    public void deleteProduct(int id){
        productClient.deleteProduct(id);
    }

    public OrderViewModel buildOrderViewModel(Invoice invoice, List<InvoiceItem> items,
                                              Customer customer, BigDecimal orderTotal,
                                              int awardedPoints, LevelUp memberPoints){


        OrderViewModel ovm = new OrderViewModel();
        ovm.setInvoiceId(invoice.getId());
        ovm.setCustomer(customer);
        ovm.setPurchaseDate(invoice.getPurchaseDate());
        ovm.setInvoiceItems(items);
        ovm.setOrderTotal(orderTotal);
        ovm.setAwardedPoints(awardedPoints);
        ovm.setMemberPoints(memberPoints);


        return ovm;

    }
}

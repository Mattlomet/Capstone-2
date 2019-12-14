package com.company.RetailAPI.service;

import com.company.RetailAPI.model.*;
import com.company.RetailAPI.util.feign.*;
import com.company.RetailAPI.viewmodel.InvoiceViewModel;
import com.company.RetailAPI.viewmodel.ProductViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayer {

    private CustomerFeign customerFeign;
    private InventoryFeign inventoryFeign;
    private InvoiceFeign invoiceFeign;
    private LevelUpFeign levelUpFeign;
    private ProductFeign productFeign;


    private RabbitTemplate rabbitTemplate;

    ObjectMapper mapper = new ObjectMapper();

    private static final String EXCHANGE = "level-up-exchange";
    private static final String ROUTING_KEY = "level-up.update.points";


    @Autowired
    public ServiceLayer(CustomerFeign customerFeign,InventoryFeign inventoryFeign,InvoiceFeign invoiceFeign,LevelUpFeign levelUpFeign,ProductFeign productFeign,RabbitTemplate rabbitTemplate){
        this.customerFeign = customerFeign;
        this.inventoryFeign = inventoryFeign;
        this.invoiceFeign = invoiceFeign;
        this.levelUpFeign = levelUpFeign;
        this.productFeign = productFeign;
        this.rabbitTemplate = rabbitTemplate;
    }



    @Transactional
    public InvoiceViewModel submitInvoice(InvoiceViewModel invoiceViewModel){

        //create invoice object
        Invoice invoice = new Invoice();

        try{
            invoice.setCustomerId(customerFeign.getCustomerById(invoiceViewModel.getCustomerId()).getCustomerId());
        }catch(Exception e){
            throw new IllegalArgumentException("You have entered in a customer id that is invalid");
        }
        invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());

        //invoice save
        invoiceFeign.createInvoice(invoice);


        //get product and check quantity (and make sure product exists)  based off each invoiceItem
        invoiceViewModel.getInvoiceItemList().stream()
                .forEach(invoiceItem -> {

                    Inventory inventory = inventoryFeign.getInventoryById(invoiceItem.getInventoryId());

                    if (inventory.getQuantity() < invoiceItem.getQuantity()){
                        throw new IllegalArgumentException("Not enough inventory in stock!");
                    }

                });




        // set total price for view model & level up points
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (InvoiceItem invoiceItem:
        invoiceViewModel.getInvoiceItemList()) {
            totalPrice.add(invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuantity())));
        }

        //need to send to level up points to queue
        int pointsMultiplier = (totalPrice.divide(new BigDecimal(50)).ROUND_DOWN);

        int totalPoints = pointsMultiplier*10;

        //build levelUp for updating level points
        LevelUp levelUp = levelUpFeign.getLevelUpByCustomerId(invoiceViewModel.getCustomerId());
        levelUp.setPoints(levelUp.getPoints()+totalPoints);

        //TODO - send update queue!

        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY, levelUp);



        //build InvoiceViewModel (already have customerId, invoiceItemList)
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setPurchaseDate(LocalDate.now());
        invoiceViewModel.setLevelUpPoints(totalPoints);
        invoiceViewModel.setTotalPrice(totalPrice);

        return invoiceViewModel;

    }

    public InvoiceViewModel getInvoiceById(int id){
        Invoice invoice = invoiceFeign.getInvoiceById(id);
        return buildInvoiceViewModel(invoice);
    }

    public List<InvoiceViewModel> getAllInvoices(){
        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
        invoiceFeign.getAllInvoice().stream()
                .forEach(invoice -> {
                    invoiceViewModelList.add(buildInvoiceViewModel(invoice));
                });
        return invoiceViewModelList;
    }

    public List<InvoiceViewModel> getInvoicesByCustomerId(int id){
        List<InvoiceViewModel> invoiceViewModelList = new ArrayList<>();
        invoiceFeign.getInvoiceByCustomerId(id).stream()
                .forEach(invoice -> {
                    invoiceViewModelList.add(buildInvoiceViewModel(invoice));
                });
        return invoiceViewModelList;
    }

    @Transactional
    public List<ProductViewModel> getAllProducts(){
        List<ProductViewModel> productList = new ArrayList<>();

        inventoryFeign.getAllInventory().stream()
                .forEach(inventory -> {
                    productList.add(buildProductViewModel(productFeign.getProductById(inventory.getProductId())));
                });

        return productList;
    }


    public ProductViewModel getProductById(int id){
        Product product = productFeign.getProductById(id);
        return buildProductViewModel(product);
    }

//    @Transactional
//    public ProductViewModel getProductByInvoiceId(int id){
//        Invoice invoice = invoiceFeign.getInvoiceById(id);
//
//
//    }

    public int getLevelUpPointsByCustomerId(int id){
        LevelUp levelUp = levelUpFeign.getLevelUpByCustomerId(id);
        return levelUp.getPoints();
    }

    public InvoiceViewModel buildInvoiceViewModel(Invoice invoice){
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setCustomerId(invoice.getCustomerId());
        invoiceViewModel.setPurchaseDate(invoice.getPurchaseDate());
        invoiceViewModel.setLevelUpPoints(getLevelUpPointsByCustomerId(invoice.getCustomerId()));
        return invoiceViewModel;
    }

    public ProductViewModel buildProductViewModel(Product product){
        ProductViewModel pvm = new ProductViewModel();
        pvm.setProductId(product.getProductId());
        pvm.setProductName(product.getProductName());
        pvm.setListPrice(product.getListPrice());
        pvm.setProductDescription(product.getProductDescription());
        pvm.setUnitCost(product.getUnitCost());
        return pvm;
    }
}


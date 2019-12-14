package com.company.RetailAPI.service;

import com.company.RetailAPI.model.*;
import com.company.RetailAPI.util.feign.*;
import com.company.RetailAPI.viewmodel.InvoiceViewModel;
import com.company.RetailAPI.viewmodel.ProductViewModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    public ServiceLayer(CustomerFeign customerFeign,InventoryFeign inventoryFeign,InvoiceFeign invoiceFeign,LevelUpFeign levelUpFeign,ProductFeign productFeign,RabbitTemplate rabbitTemplate){
        this.customerFeign = customerFeign;
        this.inventoryFeign = inventoryFeign;
        this.invoiceFeign = invoiceFeign;
        this.levelUpFeign = levelUpFeign;
        this.productFeign = productFeign;
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final String EXCHANGE = "level-up-exchange";
    private static final String ROUTING_KEY = "level-up.update.points";

    @Transactional
    public InvoiceViewModel submitInvoice(InvoiceViewModel invoiceViewModel){

        //make sure customer is valid
        Customer customer = customerFeign.getCustomerById(invoiceViewModel.getCustomerId());

        //      ------------------------------------- inventory
        //check quantity for item being purchased
        //get inventory by product id
        //only one corresponding inventory to product so get the first item in array returned

        invoiceViewModel.getPurchaseProduct().stream()
                .forEach(product -> {
                    Inventory inventory = inventoryFeign.getInventoryByProductId(product.getProductId()).get(0);

                    // need one product in stock to fulfill order
                    if (inventory.getQuantity() < 1){
                        throw new IllegalArgumentException("Not enough inventory in stock!");
                    }

                    //update inventory
                    inventory.setQuantity((inventory.getQuantity()-1));
                    inventoryFeign.updateInventory(inventory);
                });

        //      ------------------------------------- level up
        //calc total & update level up points
        int totalPrice = 0;

        for (Product product:
        invoiceViewModel.getPurchaseProduct()) {
           totalPrice += product.getListPrice().intValue();
        }

        int rewardsMultiplier = totalPrice/50;
        int totalPoints = rewardsMultiplier*10;;

        // total price of product / 50 (rounded down) * 10 = total level up points for order

        //build levelUp for updating level points
        LevelUp levelUp = levelUpFeign.getLevelUpByCustomerId(invoiceViewModel.getCustomerId());

        // if hystrix fires
        if (levelUp.getLevelUpId() == 0){
            throw new DataRetrievalFailureException("Unable to connect with level up service please try again");
        }

        levelUp.setPoints(levelUp.getPoints()+totalPoints);

        //send to level up update queue
        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY, levelUp);

        //      ------------------------------------- save invoice
        // we do this at end to ensure that all else went through without errors
        Invoice invoice = new Invoice();
        invoice.setCustomerId(customer.getCustomerId());
        invoice.setPurchaseDate(LocalDate.now());

        invoice = invoiceFeign.createInvoice(invoice);

        //      ------------------------------------- build Invoice View Model to present to customer
        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
        invoiceViewModel.setPurchaseDate(LocalDate.now());
        invoiceViewModel.setLevelUpPoints(totalPoints);

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
//        Invoice invoice = productFeign.get(id);
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


//
//create invoice object
//Invoice invoice = new Invoice();
//
//        try{
//                invoice.setCustomerId(customerFeign.getCustomerById(invoiceViewModel.getCustomerId()).getCustomerId());
//                }catch(Exception e){
//                throw new IllegalArgumentException("You have entered in a customer id that is invalid");
//                }
//                invoice.setPurchaseDate(invoiceViewModel.getPurchaseDate());
//
//                //invoice save
//                invoiceFeign.createInvoice(invoice);
//
//
//                //get product and check quantity (and make sure product exists)  based off each invoiceItem
//                invoiceViewModel.getInvoiceItemList().stream()
//                .forEach(invoiceItem -> {
//
//                Inventory inventory = inventoryFeign.getInventoryById(invoiceItem.getInventoryId());
//
//                if (inventory.getQuantity() < invoiceItem.getQuantity()){
//        throw new IllegalArgumentException("Not enough inventory in stock!");
//        }
//
//        });
//
//
//
//
//        // set total price for view model & level up points
//        BigDecimal totalPrice = BigDecimal.ZERO;
//
//        for (InvoiceItem invoiceItem:
//        invoiceViewModel.getInvoiceItemList()) {
//        totalPrice.add(invoiceItem.getUnitPrice().multiply(new BigDecimal(invoiceItem.getQuantity())));
//        }
//
//        //need to send to level up points to queue
//        int pointsMultiplier = (totalPrice.divide(new BigDecimal(50)).ROUND_DOWN);
//
//        int totalPoints = pointsMultiplier*10;
//
//        //build levelUp for updating level points
//        LevelUp levelUp = levelUpFeign.getLevelUpByCustomerId(invoiceViewModel.getCustomerId());
//
//        // if hystrix fires
//        if (levelUp.getLevelUpId() == 0){
//        invoiceViewModel.setLevelUpPointsError("Level Up Service currently down. Points will be saved.");
//        }else{
//        invoiceViewModel.setLevelUpPoints(totalPoints);
//        levelUp.setPoints(levelUp.getPoints()+totalPoints);
//        }
//
//        //send to level up update queue
//        rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY, levelUp);
//
//
//        //build InvoiceViewModel (already have customerId, invoiceItemList)
//        invoiceViewModel.setInvoiceId(invoice.getInvoiceId());
//        invoiceViewModel.setPurchaseDate(LocalDate.now());
//        invoiceViewModel.setTotalPrice(totalPrice);
//
//        return invoiceViewModel;

package com.pf7.eshop.service;

import com.pf7.eshop.database.CustomerDAO;
import com.pf7.eshop.database.OrderDAO;
import com.pf7.eshop.database.ProductDAO;
import com.pf7.eshop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private ArrayList<OrderItems> productlist;
    private final Scanner scanner = new Scanner(System.in);

    public OrderService(){
        try {
            orderDAO = new OrderDAO();
            customerDAO = new CustomerDAO();
            productDAO = new ProductDAO();
            productlist = new ArrayList<>();
        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
    }

    public void createOrderMenu() {
        do{
            logger.info("Please, select category: ");
            logger.info("1. Insert order.");
            logger.info("2. Delete order.");
            logger.info("3. Show order list.");
            logger.info("4. Return to menu.");

            switch (scanner.nextInt()){
                case 1 -> insertOrder();
                case 2 -> deleteOrder();
                case 3 -> OrderDAO.showOrdersTable();
                case 4 -> {return;}
                default -> logger.info("Please give a valid category!");
            }
        }while(true);
    }

    private void insertOrder() {

        int customerID;
        boolean customerExists;

        do {
            logger.info("\nPlease select customer id:\n");
            customerDAO.showCostumersTable();
            customerID = scanner.nextInt();
            customerExists= customerDAO.customerExists(customerID);

        }while(!customerExists);

        logger.info("Please select product:\n");
        productDAO.showProductTable();
        String productSelection = "Y";
        do{
            logger.info("Give product id: \n");

            int id = scanner.nextInt();
            boolean productExists = productDAO.productExists(id);

            if (!productExists){
                continue;
            }

            logger.info("Give product quantity: \n");
            int quantity = scanner.nextInt();

            OrderItems ordersItem = new OrderItems();
            ordersItem.setProductId(id);
            ordersItem.setQuantity(quantity);

            productlist.add(ordersItem);

            logger.info("Do you want to add another product? Y/N: ");
            productSelection = scanner.next();

            while (!productSelection.toUpperCase(Locale.ROOT).startsWith("N") || !productSelection.toUpperCase(Locale.ROOT).startsWith("Y")){
                logger.info("Invalid choice...Do you want to add another product? Y/N: ");
                productSelection = scanner.next();
            }
        }while(!productSelection.toUpperCase(Locale.ROOT).startsWith("N"));

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for(OrderItems i:  productlist){
            totalPrice = totalPrice.add(productDAO.getProductPriceByID(i.getProductId()).multiply(BigDecimal.valueOf(i.getQuantity())));
        }
        Orders orders = new Orders();
        orders.setCustomerId(customerID);

        var tempCustomer = new Customer();
        tempCustomer = customerDAO.getCustomersByID(customerID);

        logger.info("Select payment method: \n1. Wire transfer \n2. Credit Card");
        int method = scanner.nextInt();

        int percentage = 0;
        if (tempCustomer.getCustomerCategory() == CustomerCategory.B2B){
            percentage = 20;
        }else if (tempCustomer.getCustomerCategory() == CustomerCategory.B2G){
            percentage = 50;
        }

        if (method == 1) {
            percentage += 10;
            orders.setPaymentMethod(PaymentMethod.WireTransfer);
        }else {
            percentage += 15;
            orders.setPaymentMethod(PaymentMethod.CreditTransfer);
        }

        totalPrice = totalPrice.subtract((totalPrice.multiply(BigDecimal.valueOf(percentage))).divide(BigDecimal.valueOf(100)));
        orders.setTotalPrice(totalPrice);

        int orderID = orderDAO.insert(orders);

        if (orderID != 0) {
            for (OrderItems i : productlist) {
                i.setOrderId(orderID);
            }

            for (OrderItems i : productlist) {
                orderDAO.insertOrderItems(i);
            }

            logger.info("Order successfully added!");
        }else{
            logger.error("Order error!");
        }
    }

    private void deleteOrder() {
    }

}

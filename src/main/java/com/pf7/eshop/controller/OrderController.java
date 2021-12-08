package com.pf7.eshop.controller;

import com.pf7.eshop.dao.CustomerDAO;
import com.pf7.eshop.dao.OrderDAO;
import com.pf7.eshop.dao.ProductDAO;
import com.pf7.eshop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private ArrayList<OrderItems> productlist;
    private final Scanner scanner = new Scanner(System.in);

    public OrderController() {
        try {
            orderDAO = new OrderDAO();
            customerDAO = new CustomerDAO();
            productDAO = new ProductDAO();
            productlist = new ArrayList<>();
        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
    }

    public void insertOrder() {

        int customerID;
        boolean customerExists;

        customerDAO.showCustomersTable();
        logger.info("Please select customer id:\n");
        do {
            customerID = scanner.nextInt();
            customerExists = customerDAO.customerExists(customerID);

            if (!customerExists)
                logger.info("Please select valid customer id:\n");
        } while (!customerExists);

        logger.info("Please select product:\n");
        productDAO.showProductTable();
        String productSelection = "Y";
        do {
            logger.info("Give product id: \n");

            int id = scanner.nextInt();
            boolean productExists = productDAO.productExists(id);

            if (!productExists) {
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

            while (!productSelection.toUpperCase(Locale.ROOT).startsWith("N") && !productSelection.toUpperCase(Locale.ROOT).startsWith("Y")) {
                logger.info("Invalid choice...Do you want to add another product? Y/N: ");
                productSelection = scanner.next();
            }
        } while (!productSelection.toUpperCase(Locale.ROOT).startsWith("N"));

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (OrderItems i : productlist) {
            totalPrice = totalPrice.add(productDAO.getProductPriceByID(i.getProductId()).multiply(BigDecimal.valueOf(i.getQuantity())));
        }
        Orders orders = new Orders();
        orders.setCustomerId(customerID);

        var tempCustomer = new Customer();
        tempCustomer = customerDAO.getCustomersByID(customerID);

        logger.info("Select payment method: \n1. Wire transfer \n2. Credit Card");
        int method = scanner.nextInt();

        int percentage = 0;
        if (tempCustomer.getCustomerCategory() == CustomerCategory.B2B) {
            percentage = 20;
        } else if (tempCustomer.getCustomerCategory() == CustomerCategory.B2G) {
            percentage = 50;
        }

        if (method == 1) {
            percentage += 10;
            orders.setPaymentMethod(PaymentMethod.WireTransfer);
        } else {
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
        } else {
            logger.error("Order error!");
        }
    }

    public void deleteOrder() {

        orderDAO.showOrdersTable();
        int deletedID;
        boolean orderExists;

        logger.info("Please give order's ID you want to delete: ");
        do {
            deletedID = scanner.nextInt();
            orderExists = orderDAO.orderExists(deletedID);
            String exitSelection;

            if (!orderExists) {
                logger.info("Please give valid order's ID you want to delete: ");
            } else {
                logger.info("Are you sure you want to delete the order Y/N? ");
                exitSelection= scanner.next();

                while (!exitSelection.toUpperCase(Locale.ROOT).startsWith("N") && !exitSelection.toUpperCase(Locale.ROOT).startsWith("Y")) {
                    logger.info("Invalid choice...Are you sure you want to delete the order Y/N? ");
                    exitSelection = scanner.next();
                }

                if (exitSelection.toUpperCase(Locale.ROOT).startsWith("Y")) {
                    orderDAO.deleteOrder(deletedID);
                    break;
                }

            }
        } while (!orderExists);

    }


    public void showOrderTable() {
        orderDAO.showOrdersTable();
    }


}

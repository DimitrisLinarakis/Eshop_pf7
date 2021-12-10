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
    private final Scanner scanner = new Scanner(System.in);

    public OrderController() {
        try {
            orderDAO = new OrderDAO();
            customerDAO = new CustomerDAO();
            productDAO = new ProductDAO();

        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
    }

    public void insertOrder() {

        ArrayList<OrderItems> productList = new ArrayList<>();
        int customerID;
        boolean customerExists;
        Orders orders = new Orders();
        String checkoutPendingOrder = "N";

        customerDAO.showCustomersTable();
        logger.info("Please select customer id:\n");
        do {
            customerID = scanner.nextInt();
            customerExists = customerDAO.customerExists(customerID);

            if (!customerExists)
                logger.info("Please select valid customer id:\n");
        } while (!customerExists);

        Orders pendingOrder;
        pendingOrder = GlobalController.getPendingOrderByCustomerId(customerID);

        if (pendingOrder != null) {
            logger.info("You have a pending Order!!!Do you want to continue checkout Y/N?? \n");

            checkoutPendingOrder = scanner.next();

            while (!checkoutPendingOrder.toUpperCase(Locale.ROOT).startsWith("N") && !checkoutPendingOrder.toUpperCase(Locale.ROOT).startsWith("Y")) {
                logger.info("Invalid Choice...You have a pending Order!!!Do you want to continue checkout Y/N?? \n");
                checkoutPendingOrder = scanner.next();
            }
        }


        if (checkoutPendingOrder.toUpperCase(Locale.ROOT).startsWith("Y") && (pendingOrder != null)) {
            int pendingOrderId = pendingOrder.getOrderId();
            orders = pendingOrder;
            productList = GlobalController.getPendingOrderItemsByOrderId(orders.getOrderId());

            insertOrderAndOrderItems(orders,productList);
            if (productList.size() > 0){
                pendingOrderId = productList.get(0).getOrderId();
            }
            GlobalController.deletePendingOrderByOrder(pendingOrder);
            GlobalController.deletePendingOrderItemsByOrderItemList(pendingOrderId);
        } else {

            if (pendingOrder != null) {
                int pendingOrderId = pendingOrder.getOrderId();
                GlobalController.deletePendingOrderByOrder(pendingOrder);
                GlobalController.deletePendingOrderItemsByOrderItemList(pendingOrderId);
            }

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

                productList.add(ordersItem);

                logger.info("Do you want to add another product? Y/N: ");
                productSelection = scanner.next();

                while (!productSelection.toUpperCase(Locale.ROOT).startsWith("N") && !productSelection.toUpperCase(Locale.ROOT).startsWith("Y")) {
                    logger.info("Invalid choice...Do you want to add another product? Y/N: ");
                    productSelection = scanner.next();
                }
            } while (!productSelection.toUpperCase(Locale.ROOT).startsWith("N"));

            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for (OrderItems i : productList) {
                totalPrice = totalPrice.add(productDAO.getProductPriceByID(i.getProductId()).multiply(BigDecimal.valueOf(i.getQuantity())));
            }

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


            logger.info("You want to continue checkout Y/N?? ");
            checkoutPendingOrder = scanner.next();

            while (!checkoutPendingOrder.toUpperCase(Locale.ROOT).startsWith("N") && !checkoutPendingOrder.toUpperCase(Locale.ROOT).startsWith("Y")) {
                logger.info("Invalid Choice...You want to continue checkout Y/N??  ");
                checkoutPendingOrder = scanner.next();
            }


            if(checkoutPendingOrder.toUpperCase(Locale.ROOT).startsWith("Y")){
                insertOrderAndOrderItems(orders,productList);
            }else{
                int orderId = GlobalController.addPendingOrder(orders);
                GlobalController.addPendingOrderItems(productList,orderId);
            }

        }

    }

    private void insertOrderAndOrderItems(Orders orders,ArrayList<OrderItems> productList){

        int orderID = orderDAO.insert(orders);

        if (orderID != 0) {
            for (OrderItems i : productList) {
                i.setOrderId(orderID);
            }

            for (OrderItems i : productList) {
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
                exitSelection = scanner.next();

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

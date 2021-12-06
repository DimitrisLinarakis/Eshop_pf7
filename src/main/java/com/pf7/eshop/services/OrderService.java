package com.pf7.eshop.services;

import com.pf7.eshop.database.OrderDAO;
import com.pf7.eshop.models.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private OrderDAO orderDAO;
    private final Scanner scanner = new Scanner(System.in);

    public OrderService(){
        try {
            orderDAO = new OrderDAO();
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
                case 3 -> OrderDAO.showProductTable();
                case 4 -> {return;}
                default -> logger.info("Please give a valid category!");
            }
        }while(true);
    }

    private void insertOrder() {
        logger.info("Please give the email for the order:");
        scanner.next();


    }

    private void deleteOrder() {
    }

}

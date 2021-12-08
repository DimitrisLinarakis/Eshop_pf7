package com.pf7.eshop.view;

import com.pf7.eshop.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class OrderView {
    private static final Logger logger = LoggerFactory.getLogger(OrderView.class);
    private final Scanner scanner = new Scanner(System.in);
    private final OrderController orderController;

    public OrderView(){
            orderController = new OrderController();
    }

    public void createOrderMenu() {
        do{
            logger.info("Please, select category: ");
            logger.info("1. Insert order.");
            logger.info("2. Delete order.");
            logger.info("3. Show order list.");
            logger.info("4. Return to menu.");

            switch (scanner.nextInt()){
                case 1 -> orderController.insertOrder();
                case 2 -> orderController.deleteOrder();
                case 3 -> orderController.showOrderTable();
                case 4 -> {return;}
                default -> logger.info("Please give a valid category!");
            }
        }while(true);
    }


}

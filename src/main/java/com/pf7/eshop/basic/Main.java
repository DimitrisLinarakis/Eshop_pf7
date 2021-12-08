package com.pf7.eshop.basic;

import com.pf7.eshop.controller.DatabaseController;
import com.pf7.eshop.dao.StarterDAO;
import com.pf7.eshop.view.*;
import com.pf7.eshop.view.OrderView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try{
            DatabaseController.createConnection();
            StarterDAO starterDAO = new StarterDAO();
            starterDAO.createTables();
        } catch(Exception e) {
            logger.error("Error {}", e.toString());
            System.exit(0);
        }

        logger.info("=========================");
        logger.info("Welcome to Eshop");
        logger.info("=========================\n");

        do{
            logger.info("Please, select category: ");
            logger.info("1. Customers list");
            logger.info("2. Products list");
            logger.info("3. Orders list");
            logger.info("4. Reports");
            logger.info("5. Exit");

            switch (scanner.nextInt()) {
                case 1 -> {
                    CustomerView customerService = new CustomerView();
                    customerService.createCustomerMenu();
                }
                case 2 -> {
                    ProductView productService = new ProductView();
                    productService.createProductMenu();
                }
                case 3 -> {
                    OrderView orderService = new OrderView();
                    orderService.createOrderMenu();
                }
                case 4 -> {
                    ReportView reportServices = new ReportView();
                    reportServices.createReportMenu();
                }
                case 5 -> {
                    logger.info("We are sorry that you have to leave...Goodbye!\n");
                    DatabaseController.stopServer();
                    System.exit(0);
                    //stop server, shutdown
                }
                default -> logger.info("Please, give a valid category!\n");
            }

        }while(true);

    }
}

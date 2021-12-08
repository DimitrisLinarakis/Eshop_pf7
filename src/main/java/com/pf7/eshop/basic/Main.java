package com.pf7.eshop.basic;

import com.pf7.eshop.database.StarterDAO;
import com.pf7.eshop.service.DatabaseService;
import com.pf7.eshop.service.CustomerService;
import com.pf7.eshop.service.OrderService;
import com.pf7.eshop.service.ProductService;
import com.pf7.eshop.service.ReportServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try{
            DatabaseService.createConnection();
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
                    CustomerService customerService = new CustomerService();
                    customerService.createCustomerMenu();
                }
                case 2 -> {
                    ProductService productService = new ProductService();
                    productService.createProductMenu();
                }
                case 3 -> {
                    OrderService orderService = new OrderService();
                    orderService.createOrderMenu();
                }
                case 4 -> {
                    ReportServices reportServices = new ReportServices();
                }
                case 5 -> {
                    logger.info("We are sorry that you have to leave...Goodbye!\n");
                    DatabaseService.stopServer();
                    System.exit(0);
                    //stop server, shutdown
                }
                default -> logger.info("Please, give a valid category!\n");
            }

        }while(true);

    }
}

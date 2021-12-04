package com.pf7.eshop.basic;

import com.pf7.eshop.services.DatabaseService;
import com.pf7.eshop.services.CustomerService;
import com.pf7.eshop.services.OrderService;
import com.pf7.eshop.services.ProductService;
import com.pf7.eshop.services.ReportServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // hello world
        try{
            DatabaseService.createConnection();
        } catch(Exception e) {
            logger.error("Error {}", e);
            System.exit(0);
        }

        logger.info("=========================");
        logger.info("Welcome to Eshop");
        logger.info("=========================");

        do{
            logger.info("Please, select category: ");
            logger.info("1. Customers list");
            logger.info("2. Products list");
            logger.info("3. Orders list");
            logger.info("4. Reports");
            logger.info("5. Exit\n");

            switch (scanner.nextInt()){
                case 1:{
                    CustomerService customerService = new CustomerService();
                    break;}
                case 2:{
                    ProductService productService = new ProductService();
                    break;
                }
                case 3:{
                   OrderService orderService = new OrderService();
                   break;
                }
                case 4:{
                    ReportServices reportServices = new ReportServices();
                    break;
                }
                case 5:{
                    DatabaseService.stopServer();
                    System.exit(0);
                    //stop server, shutdown
                    break;
                }
                default:
                    logger.info("Please, give a valid category!");
                    continue;
            }

        }while(1==1);

    }
}

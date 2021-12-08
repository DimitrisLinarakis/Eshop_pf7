package com.pf7.eshop.view;

import com.pf7.eshop.controller.CustomerController;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import org.slf4j.Logger;

public class CustomerView {

    private static final Logger logger = LoggerFactory.getLogger(CustomerView.class);
    private final Scanner scanner = new Scanner(System.in);
    private final CustomerController customerController;

    public CustomerView() {
        customerController = new CustomerController();
    }

    public void createCustomerMenu() {

        logger.info("Please, select category: ");

        do {
            logger.info("1. Insert Customer");
            logger.info("2. Update Customer");
            logger.info("3. Delete Customer");
            logger.info("4. Show Customers list");
            logger.info("5. Return To Menu");

            switch (scanner.nextInt()) {
                case 1 -> customerController.insertCustomer();
//                case 2 -> updateCustomer();
                case 3 -> customerController.deleteCustomer();
                case 4 -> customerController.showCustomersTable();
                case 5 -> {
                    return;
                }
                default -> logger.info("Please, give a valid category!");
            }
        } while (true);
    }

}

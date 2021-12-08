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

        do {
            logger.info("1. Insert Customer");
//            logger.info("2. Update Customer");
            logger.info("2. Delete Customer");
            logger.info("3. Show Customers list");
            logger.info("4. Return To Menu\n");

            logger.info("Please, select category: ");

            switch (scanner.nextInt()) {
                case 1 -> customerController.insertCustomer();
//                case 2 -> updateCustomer();
                case 2 -> customerController.deleteCustomer();
                case 3 -> customerController.showCustomersTable();
                case 4 -> {
                    return;
                }
                default -> logger.info("Please, select a valid category!\n");
            }
        } while (true);
    }

}

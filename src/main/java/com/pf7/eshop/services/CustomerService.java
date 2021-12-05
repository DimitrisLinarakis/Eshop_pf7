package com.pf7.eshop.services;

import com.pf7.eshop.database.CustomerDAO;
import com.pf7.eshop.models.Customer;
import com.pf7.eshop.models.CustomerCategory;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import org.slf4j.Logger;

public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private CustomerDAO customerDAO;
    Scanner scanner = new Scanner(System.in);

    public CustomerService() {
        try {
            customerDAO = new CustomerDAO();
        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
        createCustomerMenu();
    }

    private void createCustomerMenu() {

        logger.info("Please, select category: ");

        do {
            logger.info("1. Insert Customer");
            logger.info("2. Update Customer");
            logger.info("3. Delete Customer");
            logger.info("4. Return To Menu");

            switch (scanner.nextInt()) {
                case 1 -> insertCustomer();
                case 2 -> updateCustomer();
                case 3 -> deleteCustomer();
                case 4 -> {
                    return;
                }
                default -> logger.info("Please, give a valid category!");
            }
        } while (1 == 1);
    }

    private void insertCustomer() {
        Customer customer = new Customer();

        logger.info("Please give customer's name:");
        customer.setName(scanner.next());

        logger.info("Please give customer's surname:");
        customer.setSurname(scanner.next());

        boolean correctChoice = false;
        do {
            logger.info("Please select one of the following customer categories: \n 1.{} \n 2.{} \n 3.{}", CustomerCategory.B2B, CustomerCategory.B2C, CustomerCategory.B2G);
            String choice;
            choice = scanner.next();

            switch (choice) {
                case "1", "B2B" -> {
                    customer.setCustomerCategory(CustomerCategory.B2B);
                    correctChoice = true;
                }
                case "2", "B2C" -> {
                    customer.setCustomerCategory(CustomerCategory.B2C);
                    correctChoice = true;
                }
                case "3", "B2G" -> {
                    customer.setCustomerCategory(CustomerCategory.B2G);
                    correctChoice = true;
                }
            }
        } while (!correctChoice);

        logger.info("Please give customer's email:");
        customer.setEmail(scanner.next());

        customerDAO.insert(customer);
    }

    private void updateCustomer() {
        Customer customer = new Customer();

        customerDAO.update(customer);
    }

    private void deleteCustomer() {
        Customer customer = new Customer();

        customerDAO.delete(customer);
    }
}

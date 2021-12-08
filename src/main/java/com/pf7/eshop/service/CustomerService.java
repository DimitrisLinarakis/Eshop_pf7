package com.pf7.eshop.service;

import com.pf7.eshop.database.CustomerDAO;
import com.pf7.eshop.model.Customer;
import com.pf7.eshop.model.CustomerCategory;
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
                case 1 -> insertCustomer();
//                case 2 -> updateCustomer();
                case 3 -> deleteCustomer();
                case 4 -> customerDAO.showCostumersTable();
                case 5 -> {
                    return;
                }
                default -> logger.info("Please, give a valid category!");
            }
        } while (true);
    }

    private void insertCustomer() {
        Customer customer = new Customer();

        String choice;
        boolean correctChoice = false;
        do {
            logger.info("Please select one of the following customer categories: \n 1.{} \n 2.{} \n 3.{}", CustomerCategory.B2B, CustomerCategory.B2C, CustomerCategory.B2G);

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

        if (choice.equals("2") || choice.equals("B2C")) {
            logger.info("Please give customer's name:");
            customer.setName(scanner.next());

            logger.info("Please give customer's surname:");
            customer.setSurname(scanner.next());
        }else{
            logger.info("Please give business name:");
            customer.setName(scanner.next());
        }

        logger.info("Please give customer's email:");
        customer.setEmail(scanner.next());

        while (customerDAO.customerExists(customer.getEmail())){
            logger.info("The customer that you want to insert already exists. Give another customer email: ");
            customer.setEmail(scanner.next());
        }

        customerDAO.insert(customer);
    }

//    private void updateCustomer() {
//        Customer customer = new Customer();
//
//        customerDAO.update(customer);
//    }

    private void deleteCustomer() {

        customerDAO.showCostumersTable();

        logger.info("Please give customer's ID that you want to delete: ");
        int deletedID = scanner.nextInt();


        customerDAO.delete(deletedID);
    }
}

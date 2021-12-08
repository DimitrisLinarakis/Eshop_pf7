package com.pf7.eshop.controller;

import com.pf7.eshop.dao.CustomerDAO;
import com.pf7.eshop.model.Customer;
import com.pf7.eshop.model.CustomerCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private CustomerDAO customerDAO;
    private final Scanner scanner = new Scanner(System.in);

    public CustomerController() {
            customerDAO = new CustomerDAO();
    }

    public void insertCustomer() {
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
            customer.setSurname(" ");
        }

        scanner.nextLine();
        logger.info("Please give email:");
        customer.setEmail(scanner.next());

        while (customerDAO.customerExists(customer.getEmail())){
            logger.info("The customer that you want to insert already exists. Give another customer email: ");
            customer.setEmail(scanner.next());
        }

        customerDAO.insert(customer);
    }

    public void showCustomersTable(){
        customerDAO.showCustomersTable();
    }

//    private void updateCustomer() {
//        Customer customer = new Customer();
//
//        customerDAO.update(customer);
//    }

    public void deleteCustomer() {

        customerDAO.showCustomersTable();

        logger.info("Please give customer's ID that you want to delete: ");
        int deletedID = scanner.nextInt();


        customerDAO.delete(deletedID);
    }
}

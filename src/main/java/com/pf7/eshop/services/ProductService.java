package com.pf7.eshop.services;

import com.pf7.eshop.database.CustomerDAO;
import com.pf7.eshop.database.ProductDAO;
import com.pf7.eshop.models.Customer;
import com.pf7.eshop.models.CustomerCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private ProductDAO productDAO;
    Scanner scanner = new Scanner(System.in);

    public ProductService() {
        try {
            productDAO = new ProductDAO();
        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
        createProductMenu();
    }

    private void createProductMenu() {
        do{
            logger.info("Please, select category: ");
            logger.info("1. Insert product.");
            logger.info("2. Delete product.");
            logger.info("3. Return to menu.");

            switch (scanner.nextInt()){
                case 1 -> insertProduct();
                case 2 -> deleteProduct();
                case 3 -> {return;}
                default -> logger.info("Please give a valid category!");
            }
        }while(1==1);



    }

    private void insertProduct() {

    }

//    private void updateCustomer() {
//        Customer customer = new Customer();
//
//        customerDAO.update(customer);
//    }

    private void deleteProduct() {


    }
}

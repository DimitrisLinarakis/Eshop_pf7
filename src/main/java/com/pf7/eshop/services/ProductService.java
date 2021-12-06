package com.pf7.eshop.services;

import com.pf7.eshop.database.ProductDAO;
import com.pf7.eshop.models.Products;
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
    }

    public void createProductMenu() {
        do{
            logger.info("Please, select category: ");
            logger.info("1. Insert product.");
            logger.info("2. Delete product.");
            logger.info("3. Show product list.");
            logger.info("4. Return to menu.");

            switch (scanner.nextInt()){
                case 1 -> insertProduct();
                case 2 -> deleteProduct();
                case 3 -> productDAO.showProductTable();
                case 4 -> {return;}
                default -> logger.info("Please give a valid category!");
            }
        }while(true);

    }

    private void insertProduct() {
        Products product = new Products();
        scanner.nextLine();
        logger.info("Insert the name of the product: ");
        product.setName(scanner.nextLine());

        while (productDAO.exists(product.getName())){
            logger.info("The product that you want to insert already exists. Give another product name: ");
            product.setName(scanner.nextLine());
        }

        logger.info("Insert the price of the product: ");
        product.setPrice(scanner.nextBigDecimal());

        productDAO.insert(product);
    }

//    private void updateCustomer() {
//        Customer customer = new Customer();
//
//        customerDAO.update(customer);
//    }

    private void deleteProduct() {
        productDAO.showProductTable();

        logger.info("Please give product's ID that you want to delete: ");
        int deletedID = scanner.nextInt();
        productDAO.delete(deletedID);
    }
}

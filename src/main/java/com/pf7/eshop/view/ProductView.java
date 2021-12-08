package com.pf7.eshop.view;

import com.pf7.eshop.controller.ProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ProductView {
    private static final Logger logger = LoggerFactory.getLogger(ProductView.class);
    private final Scanner scanner = new Scanner(System.in);
    private final ProductController productController;

    public ProductView() {
            productController = new ProductController();
    }

    public void createProductMenu() {
        do{
            logger.info("1. Insert product.");
            logger.info("2. Delete product.");
            logger.info("3. Show product list.");
            logger.info("4. Return to menu.\n");

            logger.info("Please, select category: ");

            switch (scanner.nextInt()){
                case 1 -> productController.insertProduct();
                case 2 -> productController.deleteProduct();
                case 3 -> productController.showProductsTable();
                case 4 -> {return;}
                default -> logger.info("Please select a valid category!");
            }
        }while(true);

    }
}

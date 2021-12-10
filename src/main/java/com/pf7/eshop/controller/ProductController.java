package com.pf7.eshop.controller;

import com.pf7.eshop.dao.OrderDAO;
import com.pf7.eshop.dao.ProductDAO;
import com.pf7.eshop.model.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    public ProductController() {
        try {
            productDAO = new ProductDAO();
            orderDAO = new OrderDAO();
        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
    }


    public void insertProduct() {
        Products product = new Products();
        logger.info("Insert the name of the product: ");
        product.setName(scanner.next());


        while (productDAO.exists(product.getName())) {
            logger.info("The product that you want to insert already exists. Give another product name: ");
            product.setName(scanner.next());
        }

        logger.info("Insert the price of the product: ");
        product.setPrice(scanner.nextBigDecimal());

        productDAO.insert(product);
    }

//    public void updateCustomer() {
//        Customer customer = new Customer();
//
//        customerDAO.update(customer);
//    }

    public void deleteProduct() {
        productDAO.showProductTable();

        logger.info("Please give product's ID that you want to delete: ");
        int deletedID = scanner.nextInt();

        boolean result = orderDAO.deleteOrderItemsByProductId(deletedID);
        if (result) {
            productDAO.delete(deletedID);
        } else {
            logger.info("Cannot Delete Product Cause Of Order Items.");
        }
    }

    public void showProductsTable() {
        productDAO.showProductTable();
    }
}

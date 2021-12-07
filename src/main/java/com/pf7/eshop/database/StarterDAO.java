package com.pf7.eshop.database;

import com.pf7.eshop.services.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class StarterDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(StarterDAO.class);

    public StarterDAO() {
        this.statement = DatabaseService.getStatement();
    }

    public void createTables() {
        createCustomersTable();
        createProductTable();
        createOrdersTable();
        createOrderItemsTable();
    }

    private void createCustomersTable() {
        try {
            int result = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Customers(" +
                    " CustomerId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " CustomerCategory VARCHAR(5) NOT NULL default ''," +
                    " Name VARCHAR(20) NOT NULL default ''," +
                    " Surname VARCHAR(20) NOT NULL default ''," +
                    " Email VARCHAR(50) NOT NULL default ''" +
                    ");"
            );

            logger.error("Customers Table Created : {}", result);
        } catch (SQLException ex) {
            logger.error("Create Customers Table Error : {}", ex.toString());
        }
    }

    private void createProductTable() {
        try {
            int result = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Products(" +
                    " productId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(40) NOT NULL default ''," +
                    " price DECIMAL(7, 2) NOT NULL" +
                    ");"
            );

            logger.info("Product Table Created : {}", result);
        } catch (SQLException ex) {
            logger.error("Create Product Table Error : {}", ex.toString());
        }
    }

    private void createOrdersTable() {
        try {
            int result = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Orders(" +
                    " orderId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " customerId  INTEGER, FOREIGN KEY (customerId) REFERENCES CUSTOMERS(CUSTOMERID)," +
                    " paymentMethod VARCHAR(30) NOT NULL default ''," +
                    " totalPrice  DECIMAL(7, 2) NOT NULL" +
                    ");"
            );

            logger.error("Orders Table Created : {}", result);
        } catch (SQLException ex) {
            logger.error("Create Orders Table Error : {}", ex.toString());
        }
    }

    private void createOrderItemsTable() {
        try {
            int result = statement.executeUpdate("CREATE TABLE IF NOT EXISTS OrderItems(" +
                    " orderItemsId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " productsId INTEGER, FOREIGN KEY (productsId) REFERENCES PRODUCTS(PRODUCTID)," +
                    " orderId INTEGER, FOREIGN KEY (orderId) REFERENCES ORDERS(ORDERID)," +
                    " quantity INTEGER NOT NULL default 0" +
                    ");"
            );

            logger.error("Order Items Table Created : {}", result);
        } catch (SQLException ex) {
            logger.error("Create Order Items Table Error : {}", ex.toString());
        }
    }

}

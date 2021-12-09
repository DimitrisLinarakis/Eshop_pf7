package com.pf7.eshop.dao;

import com.pf7.eshop.controller.DatabaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class StarterDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(StarterDAO.class);

    public StarterDAO() {
        this.statement = DatabaseController.getStatement();
    }

    public void createTables() {
        createCustomersTable();
        createProductTable();
        createOrdersTable();
        createOrderItemsTable();
        createTempTableProducts();

        //----------------------Check this later-----------------------//
        InsertIntoTempTable();
        InsertProductsFromTemp();
        DropTempTable();
    }

    private void createTempTableProducts() {
        try {
            int result = statement.executeUpdate("CREATE TABLE IF NOT EXISTS Temp (" +
                    " ProductId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " Name VARCHAR(40) NOT NULL default ''," +
                    " price DECIMAL(7, 2) NOT NULL" +
                    ");"
            );

            logger.info("Temporary Table for Products Created : {}", result);
        } catch (SQLException ex) {
            logger.error("Create Temporary Table for Products Error : {}", ex.toString());
        }
    }

    private void InsertIntoTempTable() {
        try {
            statement.executeUpdate("INSERT INTO Temp" +
                    "(Name, Price) " +
                    "VALUES ('Smartphone Xiaomi 11 Lite',350.00)," +
                    "('Smartphone Realme 8',250.00)," +
                    "('Smartphone Samsung Galaxy A32',278.00)," +
                    "('Smartphone Cubot J8',150.00)," +
                    "('Smartphone Apple iPhone 13',190.00)," +
                    "('Smartphone Xiaomi Redmi 10',150.00)," +
                    "('Smartwatch Huawei Watch GT',150.00)," +
                    "('Smartwatch Apple Watch S7',350.00)," +
                    "('Laptop Toshiba Dynabook',560.00)," +
                    "('Laptop Dell 3K Inspiron',800.00)," +
                    "('Playstation 5 Sony',400.00)," +
                    "('Playstation 4 Sony',350.00)," +
                    "('Nintendo Switch Joy-Con',350.00)," +
                    "('Action Camera Gopro HERO 9',450.00)," +
                    "('Action Camera Gopro Hero 8',400.00)," +
                    "('Action Camera Gopro HERO 7',350.00)," +
                    "('Printer HP Laser Color',200.00)," +
                    "('Air Condition Midea 9000 btu',500.00)," +
                    "('Air Condition Toyotomi 12000 btu',800.00)," +
                    "('Air Condition Inventor 24000 btu',1200.00);"
            );
        } catch (SQLException e) {
            logger.error("Failed to insert products into temporary table: {}", e.toString());
        }
    }

    private void InsertProductsFromTemp() {
        try{
            statement.executeUpdate("INSERT INTO PRODUCTS (Name, Price) " +
                    "SELECT Name, Price FROM Temp "+
                    "WHERE NOT EXISTS (SELECT Name, Price FROM Products WHERE Products.productId = Temp.ProductId)"
            );
        } catch (SQLException e) {
            logger.error("Failed to insert products from temporary table into database: {}", e.toString());
        }
    }

    private void DropTempTable() {
        try{
            int result = statement.executeUpdate("DROP TABLE Temp");
            logger.info("Temporary table removed : {}", result);
        } catch (SQLException e) {
            logger.error("Failed to drop temporary table from database: {}", e.toString());
        }
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

            logger.info("Customers Table Created : {}", result);
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

            logger.info("Orders Table Created : {}", result);
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

            logger.info("Order Items Table Created : {}", result);
        } catch (SQLException ex) {
            logger.error("Create Order Items Table Error : {}", ex.toString());
        }
    }

}

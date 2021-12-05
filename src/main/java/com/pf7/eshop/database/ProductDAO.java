package com.pf7.eshop.database;

import com.pf7.eshop.services.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ProductDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO() {
        this.statement = DatabaseService.getStatement();

        try{
            int result =  statement.executeUpdate("CREATE TABLE IF NOT EXISTS Product(" +
                    " productId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(20) NOT NULL default ''," +
                    " price DECIMAL(7, 2) NOT NULL" +
                    ");"
            );

            logger.error("Product Table Created : {}",result);
        }catch (Exception ex){
            logger.error("Create Product Table Error : {}",ex.toString());
        }

    }

    public void insert() {

    }

    public void delete() {

    }

    public void showCostumersTable() {


    }
}

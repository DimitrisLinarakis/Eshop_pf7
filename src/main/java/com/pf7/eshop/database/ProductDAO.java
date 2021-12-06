package com.pf7.eshop.database;

import com.pf7.eshop.models.Products;
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

            logger.info("Product Table Created : {}",result);
        }catch (Exception ex){
            logger.error("Create Product Table Error : {}",ex.toString());
        }

    }

    public void insert(Products product) {
        try{
            statement.executeUpdate("INSERT INTO Product " +
                    "(Name, Price) " +
                    "VALUES ('"+product.getName()+"', " +
                    "'"+product.getPrice()+"')"
            );
        } catch (Exception e) {
            logger.error("Failed to insert product's into database: {}", e.toString());
        }
    }

    public void delete(int deletedID) {

        try{
            int result = statement.executeUpdate("DELETE FROM Product WHERE ProductID = '"+deletedID+"' ");
            if(result == 1)
                logger.info("Product successfully deleted from database");
            else
                logger.info("Product not found");
        }catch(Exception e){
            logger.error("Failed to delete product from database {}", e.toString());
        }

    }

    public void showProductsTable() {


    }

    public boolean exists(String productName) {
        boolean result = false;

        try{
            ResultSet resultSet = statement.executeQuery("SELECT Name FROM Product WHERE EXISTS (SELECT Name FROM Product WHERE Name = '"+productName+"' )");
            if(resultSet.next()){
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            logger.error("Unable to search this product in table products: {}", e.toString());

        }

        return result;
    }

    public void showProductTable(){

        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Product");

            while(resultSet.next()){
                logger.info("ProductID: {}, ProductName: {}, Price: {}",
                        resultSet.getInt("ProductId"), resultSet.getString("Name"),
                        resultSet.getBigDecimal("Price"));
            }
        }catch(Exception e){
            logger.error("Failed to print product's list {}", e.toString());
        }
    }
}

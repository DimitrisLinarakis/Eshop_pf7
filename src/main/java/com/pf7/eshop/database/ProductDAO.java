package com.pf7.eshop.database;

import com.pf7.eshop.models.Products;
import com.pf7.eshop.services.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;

public class ProductDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO() {
        this.statement = DatabaseService.getStatement();
    }

    public void insert(Products product) {
        try{
            statement.executeUpdate("INSERT INTO PRODUCTS " +
                    "(Name, Price) " +
                    "VALUES ('" + product.getName() + "', " +
                    "'" + product.getPrice() + "')"
            );
        } catch (Exception e) {
            logger.error("Failed to insert product's into database: {}", e.toString());
        }
    }

    public void delete(int deletedID) {

        try{
            int result = statement.executeUpdate("DELETE FROM Products WHERE ProductID = '"+deletedID+"' ");
            if(result == 1)
                logger.info("Product successfully deleted from database");
            else
                logger.info("Product not found");
        }catch(Exception e){
            logger.error("Failed to delete product from database {}", e.toString());
        }

    }

    //public void showProductsTable() {


   // }

    public boolean exists(String productName) {
        boolean result = false;

        try{
            ResultSet resultSet = statement.executeQuery("SELECT Name FROM Products WHERE EXISTS (SELECT Name FROM Products WHERE Name = '"+productName+"' )");
            result = resultSet.next();
        } catch (Exception e) {
            logger.error("Unable to search this product in table products: {}", e.toString());

        }

        return result;
    }

    public void showProductTable(){

        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Products");

            while(resultSet.next()){
                logger.info("ProductID: {}, ProductName: {}, Price: {}",
                        resultSet.getInt("ProductId"), resultSet.getString("Name"),
                        resultSet.getBigDecimal("Price"));
            }
        }catch(Exception e){
            logger.error("Failed to print product's list {}", e.toString());
        }
    }

    public BigDecimal getProductPriceByID(int productid) {

        try{
            ResultSet resultSet = statement.executeQuery("SELECT Price FROM Products WHERE ProductID = '"+productid+"'");

            while(resultSet.next()){
                return resultSet.getBigDecimal("Price");
            }
        }catch(Exception e){
            logger.error("Failed to get product price {}", e.toString());
        }
        return BigDecimal.valueOf(0);
    }
}

package com.pf7.eshop.dao;

import com.pf7.eshop.model.Products;
import com.pf7.eshop.controller.DatabaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.*;

public class ProductDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    public ProductDAO() {
        this.statement = DatabaseController.getStatement();
    }

    public void insert(Products product) {
        try{
            statement.executeUpdate("INSERT INTO PRODUCTS " +
                    "(Name, Price) " +
                    "VALUES ('" + product.getName() + "', " +
                    "'" + product.getPrice() + "')"
            );
        } catch (SQLException e) {
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
        }catch(SQLException e){
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
        } catch (SQLException e) {
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
        }catch(SQLException e){
            logger.error("Failed to print product's list {}", e.toString());
        }
    }

    public BigDecimal getProductPriceByID(int productid) {

        BigDecimal price = new BigDecimal(0);
        try{
            ResultSet resultSet = statement.executeQuery("SELECT Price FROM Products WHERE ProductID = '"+productid+"'");


            while(resultSet.next()){
                price = resultSet.getBigDecimal("Price");
            }
        }catch(SQLException e){
            logger.error("Failed to get product price {}", e.toString());
        }
        return price;
    }

    public boolean productExists(int id) {
        boolean result = false;
        try{
            ResultSet resultSet = statement.executeQuery("SELECT ProductId FROM Products WHERE " +
                    "EXISTS (SELECT ProductId FROM Products " +
                    "WHERE ProductId = '"+id+"')"
            );
            result = resultSet.next();

        }catch(SQLException e){
            logger.error("Unable to search this product in table Products: {}", e.toString());
        }
        return result;
    }
}

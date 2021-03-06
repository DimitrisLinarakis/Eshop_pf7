package com.pf7.eshop.dao;

import com.pf7.eshop.model.Customer;
import com.pf7.eshop.model.CustomerCategory;
import com.pf7.eshop.controller.DatabaseController;
import org.slf4j.LoggerFactory;

import java.sql.*;

import org.slf4j.Logger;

public class CustomerDAO  {

    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    public CustomerDAO() {
        this.statement = DatabaseController.getStatement();
    }

    public void insert(Customer customer) {
        try {
            statement.executeUpdate("INSERT INTO Customers " +
                    "(CustomerCategory, Name, Surname, Email) " +
                    "VALUES ('"+customer.getCustomerCategory()+"', " +
                    "'"+customer.getName()+"', '"+customer.getSurname()+"', " +
                    "'"+customer.getEmail()+"')"
            );
            logger.info("Customer successfully added to database");
        } catch (SQLException ex) {
            logger.error("Failed to insert customer's into database: {}", ex.toString());
        }
    }

//    public void update(Customer customer) {
//
//    }

    public void delete(int deleteID) {
        try {
            int result = statement.executeUpdate("DELETE FROM Customers WHERE CUSTOMERID = '"+deleteID+"'");
            if (result == 1)
                logger.info("Customer successfully deleted from database");
            else
                logger.info("Customer not found");
        } catch (SQLException ex) {
            logger.error("Failed to delete customer from database: {}", ex.toString());
        }
    }

    public void showCustomersTable() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CUSTOMERS");

            while (resultSet.next()) {
                logger.info("CustomerID: {}, CustomerCategory:{}, Name:{}, Surname: {}, Email: {}",
                        resultSet.getInt("CustomerID"),
                        resultSet.getString("CustomerCategory"),
                        resultSet.getString("Name"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Email"));

            }
            logger.info("\n");
        } catch (SQLException ex) {
            logger.error("Failed to print customer's list: {}", ex.toString());
        }

    }

    public Customer getCustomersByID(int customerID) {

        Customer customer = new Customer();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT CustomerID, CustomerCategory " +
                    "FROM CUSTOMERS WHERE CustomerId = '"+customerID+"'");
            while (resultSet.next()) {
                CustomerCategory cat = null;
                if (resultSet.getString("CustomerCategory").equals("B2B")){
                    cat = CustomerCategory.B2B;
                }else if (resultSet.getString("CustomerCategory").equals("B2C")){
                    cat = CustomerCategory.B2C;
                }else if (resultSet.getString("CustomerCategory").equals("B2G")){
                    cat = CustomerCategory.B2G;
                }
                
                customer.setCustomerID(resultSet.getInt("CustomerID"));
                customer.setCustomerCategory(cat);
            }
            
        } catch (SQLException ex) {
            logger.error("Failed to get customer by ID: {}", ex.toString());
        }
        return customer;
    }

    public boolean customerExists(String customerEmail){

        boolean result = false;
        try{
            ResultSet resultSet = statement.executeQuery("SELECT Email FROM Customers WHERE " +
                    "EXISTS (SELECT Email FROM Customers " +
                    "WHERE Email = '"+customerEmail+"')"
            );
            result = resultSet.next();

        }catch(SQLException e){
            logger.error("Unable to search this customer in table Customers: {}", e.toString());
        }
        return result;
    }

    public boolean customerExists(int id){

        boolean result = false;
        try{
            ResultSet resultSet = statement.executeQuery("SELECT CustomerId FROM Customers WHERE " +
                    "EXISTS (SELECT CustomerId FROM Customers " +
                    "WHERE CustomerId = '"+id+"')"
            );
            result = resultSet.next();

        }catch(SQLException e){
            logger.error("Unable to search this customer in table Customers: {}", e.toString());
        }
        return result;
    }

    public void showCustomerCategories(){

        CustomerCategory customerCategory;
        int counter = 1;

        for (CustomerCategory category : CustomerCategory.values()){
            logger.info(counter + category.toString());
        }

    }
}

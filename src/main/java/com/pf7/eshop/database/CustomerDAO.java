package com.pf7.eshop.database;

import com.pf7.eshop.models.Customer;
import com.pf7.eshop.services.DatabaseService;
import org.slf4j.LoggerFactory;

import java.sql.*;
import org.slf4j.Logger;

public class CustomerDAO  {

    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);

    public CustomerDAO() {
        this.statement = DatabaseService.getStatement();

        try{
            int result =  statement.executeUpdate("CREATE TABLE IF NOT EXISTS Customers(" +
                    " CustomerId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    " CustomerCategory VARCHAR(5) NOT NULL default 'B2C'," +
                    " Name VARCHAR(20) NOT NULL default ''," +
                    " Surname VARCHAR(20) NOT NULL default ''," +
                    " Email VARCHAR(50) NOT NULL default '@gmail.com'" +
                    ");"
            );

            logger.error("Customers Table Created : {}",result);
        }catch (Exception ex){
            logger.error("Create Customers Table Error : {}",ex.toString());
        }

    }

    public void insert(Customer customer) {
        try {
            statement.executeUpdate("INSERT INTO Customers " +
                    "(CustomerCategory, Name, Surname, Email) " +
                    "VALUES ('"+customer.getCustomerCategory()+"', " +
                    "'"+customer.getName()+"', '"+customer.getSurname()+"', " +
                    "'"+customer.getEmail()+"')"
            );
        } catch (Exception ex) {
            logger.error("Create Customers Table Error : {}", ex.toString());
        }
    }

    public void update(Customer customer) {

    }

    public void delete(Customer customer) {

    }
}

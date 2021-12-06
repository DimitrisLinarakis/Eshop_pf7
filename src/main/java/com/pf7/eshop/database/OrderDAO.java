package com.pf7.eshop.database;

import com.pf7.eshop.models.Orders;
import com.pf7.eshop.services.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class OrderDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    public OrderDAO(){
        this.statement = DatabaseService.getStatement();
    }

    public void insert(Orders order){
        try {
            statement.executeUpdate("");
        }catch (Exception e){
            logger.error("Failed to insert order to database: {}", e.toString());
        }
    }

    public static void showProductTable() {
    }
}

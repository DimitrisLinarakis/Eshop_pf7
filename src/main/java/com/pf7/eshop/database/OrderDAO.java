package com.pf7.eshop.database;

import com.pf7.eshop.model.OrderItems;
import com.pf7.eshop.model.Orders;
import com.pf7.eshop.service.DatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class OrderDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    public OrderDAO(){
        this.statement = DatabaseService.getStatement();
    }

    public Integer insert(Orders order){
        try {
            statement.executeUpdate("INSERT INTO Orders (CUSTOMERID, PAYMENTMETHOD, TOTALPRICE) " +
                    "VALUES ('"+order.getCustomerId()+"', '"+order.getPaymentMethod().toString()+"', '"+order.getTotalPrice()+"')", Statement.RETURN_GENERATED_KEYS);

            ResultSet rs;
            rs = statement.getGeneratedKeys();

            if (rs.next())
                return rs.getInt(1);
        }catch (SQLException e){
            logger.error("Failed to insert order to database: {}", e.toString());
        }

        return 0;
    }

    public void insertOrderItems(OrderItems orderItem){
        try {
            statement.executeUpdate("INSERT INTO OrderItems (productsId, orderId, quantity) " +
                    "VALUES ('"+orderItem.getProductId()+"', '"+orderItem.getOrderId()+"', '"+orderItem.getQuantity()+"')");
        }catch (SQLException e){
            logger.error("Failed to insert order to database: {}", e.toString());
        }
    }

    public static void showOrdersTable() {
    }
}

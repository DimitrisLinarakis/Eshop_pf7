package com.pf7.eshop.dao;

import com.pf7.eshop.controller.DatabaseController;
import com.pf7.eshop.model.OrderItems;
import com.pf7.eshop.model.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class OrderDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    public OrderDAO(){
        this.statement = DatabaseController.getStatement();
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

    public void showOrdersTable() {

        try{
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Orders");

            while(resultSet.next()){
                logger.info("Order Id: {}, Customer Id: {}, PaymentMethod: {} , Total Price : {}",
                        resultSet.getInt("OrderId"),
                        resultSet.getInt("CustomerId"),
                        resultSet.getString("PaymentMethod"),
                        resultSet.getBigDecimal("TotalPrice"));
            }
        }catch(SQLException e){
            logger.error("Failed to print order's list {}", e.toString());
        }
    }

    public boolean deleteOrder(int orderId){

        int resultOrder = 0;
        try {
            int resultOrderItems = statement.executeUpdate("DELETE FROM OrderItems where OrderId ='"+orderId+"'");


            if (resultOrderItems == 1){
                logger.info("Order Items successfully deleted from database");
                resultOrder = statement.executeUpdate("DELETE FROM Orders WHERE OrderId = '"+orderId+"'");

                if (resultOrder == 1){
                    logger.info("Order successfully deleted from database");
                }else{
                    logger.info("Error delete Order from database");
                }
            }
            else {
                int resultOrderState = statement.executeUpdate("DELETE FROM Orders where OrderId ='" + orderId + "'");
                if (resultOrderState == 1){
                    logger.info("Order successfully deleted from database");
                }else{
                    logger.info("Error delete Order from database");
                }
            }
        } catch (SQLException ex) {
            logger.error("Failed to delete Order from database: {}", ex.toString());
        }

        if (resultOrder == 1){
            return true;
        }else{
            return false;
        }
    }

    public boolean orderExists(int orderId){

        boolean result = false;
        try{
            ResultSet resultSet = statement.executeQuery("SELECT OrderId FROM Orders WHERE " +
                    "EXISTS (SELECT OrderId FROM Orders " +
                    "WHERE OrderId = '"+orderId+"')"
            );
            result = resultSet.next();

        }catch(SQLException e){
            logger.error("Unable to search this order in table orders: {}", e.toString());
        }
        return result;
    }


}

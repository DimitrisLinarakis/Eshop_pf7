package com.pf7.eshop.controller;

import com.pf7.eshop.model.OrderItems;
import com.pf7.eshop.model.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class GlobalController {
    private static ArrayList<Orders> pendingOrdersList;
    private static ArrayList<OrderItems> pendingOrderItemsList;
    private static final Logger logger = LoggerFactory.getLogger(GlobalController.class);

    public GlobalController() {
        pendingOrdersList = new ArrayList<>();
        pendingOrderItemsList = new ArrayList<>();
    }


    //========================================================================//
    //                      Order Getter - Setter                             //

    public static int addPendingOrder(Orders order) {
        order.setOrderId(pendingOrderItemsList.size() + 1);
        pendingOrdersList.add(order);

        logger.info("Order Added To Cart!!! \n");
        pendingOrdersList.forEach(orders -> logger.info("Order Id : {}, Total Price : {}, Customer Id : {}, Payment Method : {}",
                orders.getOrderId(),orders.getTotalPrice(),orders.getCustomerId(),orders.getPaymentMethod()));

        return pendingOrdersList.get((pendingOrdersList.size() - 1)).getOrderId();
    }

    public static Orders getPendingOrderByCustomerId(int customerId) {
        for (Orders i : pendingOrdersList) {
            if (i.getCustomerId() == customerId)
                return i;
        }
        return null;
    }

    public static void deletePendingOrderByOrder(Orders order) {
        try {
            pendingOrdersList.removeIf(orders -> orders.getOrderId() == order.getOrderId());
            pendingOrdersList.forEach(orders -> logger.info("Order Id : {} , Customer Id : {} ",orders.getOrderId(),orders.getCustomerId()));
        } catch (Exception ex) {
            logger.error("Error Remove Pending Order {}", ex.toString());
        }
    }


    //========================================================================//
    //                      Order Items Getter - Setter                       //

    public static void addPendingOrderItems(ArrayList<OrderItems> orderItems,int orderId) {

        for(OrderItems item :orderItems){
            item.setOrderId(orderId);
            pendingOrderItemsList.add(item);
        }

        logger.info("OrderItems Added To Cart!!! \n");
        pendingOrderItemsList.forEach(orderItems1 -> logger.info("Order Id : {}, Product Id : {}, Quantity : {}",
                orderItems1.getOrderId(),orderItems1.getProductId(),orderItems1.getQuantity()));

    }

    public static ArrayList<OrderItems> getPendingOrderItemsByOrderId(int orderId) {
        ArrayList<OrderItems> orderItemsList = new ArrayList<>();

        for (OrderItems i : pendingOrderItemsList) {
            if (i.getOrderId() == orderId)
                orderItemsList.add(i);
        }
        return orderItemsList;
    }

    public static void deletePendingOrderItemsByOrderItemList(ArrayList<OrderItems> orderItems) {
        try {
            orderItems.forEach(orderItems1 -> pendingOrderItemsList.removeIf(orderItems2 -> orderItems2.getOrderId() == orderItems1.getOrderId()));
            pendingOrderItemsList.forEach(orderItems1 -> logger.info("Order Id : {} , Order Item Id : {} ",orderItems1.getOrderId(),orderItems1.getOrderItemsId()));
        } catch (Exception ex) {
            logger.error("Error Remove Pending Order {}", ex.toString());
        }
    }
}

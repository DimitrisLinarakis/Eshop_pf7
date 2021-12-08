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


    public static void addPendingOrder(Orders order) {
        pendingOrdersList.add(order);
    }

    public static Orders getPendingOrderByCustomerId(int customerId) {
        for (Orders i : pendingOrdersList) {
            if (i.getCustomerId() == customerId)
                return i;
        }
        return null;
    }


    public static void addPendingOrderItems(OrderItems orderItem) {
        pendingOrderItemsList.add(orderItem);
    }

    public static ArrayList<OrderItems> getPendingOrderItemsByOrderId(int orderId) {
        ArrayList<OrderItems> orderItemsList = new ArrayList<>();

        for (OrderItems i : pendingOrderItemsList) {
            if (i.getOrderId() == orderId)
                orderItemsList.add(i);
        }
        return orderItemsList;
    }

    public static void deletePendingOrderByOrderItem(Orders order) {
        try {
            pendingOrdersList.remove(order);
            pendingOrdersList.forEach(orders -> logger.info("Order Id : {} , Customer Id : {} ",orders.getOrderId(),orders.getCustomerId()));
        } catch (Exception ex) {
            logger.error("Error Remove Pending Order {}", ex.toString());
        }
    }

    public static void deletePendingOrderItemsByOrderItemList(ArrayList<OrderItems> orderItems) {
        try {
            pendingOrderItemsList.removeAll(orderItems);
            pendingOrderItemsList.forEach(orderItems1 -> logger.info("Order Id : {} , Order Item Id : {} ",orderItems1.getOrderId(),orderItems1.getOrderItemsId()));
        } catch (Exception ex) {
            logger.error("Error Remove Pending Order {}", ex.toString());
        }
    }
}

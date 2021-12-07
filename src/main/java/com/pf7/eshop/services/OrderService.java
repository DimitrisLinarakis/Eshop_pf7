package com.pf7.eshop.services;

import com.pf7.eshop.database.CustomerDAO;
import com.pf7.eshop.database.OrderDAO;
import com.pf7.eshop.database.ProductDAO;
import com.pf7.eshop.models.Customer;
import com.pf7.eshop.models.OrderItems;
import com.pf7.eshop.models.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private ArrayList<OrderItems> productlist;
    private final Scanner scanner = new Scanner(System.in);

    public OrderService(){
        try {
            orderDAO = new OrderDAO();
            customerDAO = new CustomerDAO();
            productDAO = new ProductDAO();
            productlist = new ArrayList<OrderItems>();
        } catch (Exception e) {
            logger.error("Error : {}", e.toString());
        }
    }

    public void createOrderMenu() {
        do{
            logger.info("Please, select category: ");
            logger.info("1. Insert order.");
            logger.info("2. Delete order.");
            logger.info("3. Show order list.");
            logger.info("4. Return to menu.");

            switch (scanner.nextInt()){
                case 1 -> insertOrder();
                case 2 -> deleteOrder();
                case 3 -> OrderDAO.showProductTable();
                case 4 -> {return;}
                default -> logger.info("Please give a valid category!");
            }
        }while(true);
    }

    private void insertOrder() {

        logger.info("Please select customer:\n");
        customerDAO.showCostumersTable();
        int customerID = scanner.nextInt();

        logger.info("Please select product:\n");
        productDAO.showProductTable();
        String productSelection = "N";
        do{
            logger.info("Give product id: \n");
            int id = scanner.nextInt();

            logger.info("Give product quantity: \n");
            int quantity = scanner.nextInt();

            OrderItems ordersItem = new OrderItems();
            ordersItem.setProductId(id);
            ordersItem.setQuantity(quantity);

            productlist.add(ordersItem);


            logger.info("Do you want to continue Y/N:");
            productSelection = scanner.next();

        }while(!productSelection.equals("Y"));

        BigDecimal price = productDAO.getProductPriceByID(id);
        for(OrderItems i:  productlist){

        }
        Orders orders = new Orders();
        orders.setCustomerId(customerID);

        Customer customer = new Customer();
        customer = customerDAO.getCustomersByID(customerID);

        orders.setC
        orderDAO.insert(orders);

    }

    private void deleteOrder() {
    }

}

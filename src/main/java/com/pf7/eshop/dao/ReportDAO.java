package com.pf7.eshop.dao;

import com.pf7.eshop.controller.DatabaseController;
import com.pf7.eshop.model.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    public ReportDAO(){
        this.statement = DatabaseController.getStatement();
    }



    public void getPreviewReportPerParticularCustomer(int CustomerId) {
        try {
            ResultSet resultSet = statement.executeQuery("select C.NAME,C.SURNAME,count(*) as TotalCount,Round(sum(TOTALPRICE),2) as Total from ORDERS ord " +
                    "inner join CUSTOMERS C on ord.CUSTOMERID = C.CUSTOMERID " +
                    "where ord.CUSTOMERID = '"+CustomerId+"'" +
                    "group by C.NAME, C.SURNAME " +
                    "order BY Total DESC;");

            while (resultSet.next()) {
                logger.info("Total And Cost Per Particular Customer -> Name : {}, Surname:{}, Total Number :{}, Cost Of Purchases : {}",
                        resultSet.getString("NAME"),
                        resultSet.getString("SURNAME"),
                        resultSet.getInt("TotalCount"),
                        resultSet.getBigDecimal("Total"));

            }
        } catch (SQLException ex) {
            logger.error("Failed to print Report Per Particular Customer : {}", ex.toString());
        }

    }

    public void getPreviewReportPerCustomerCategory() {
        try {
            ResultSet resultSet = statement.executeQuery("select C.CUSTOMERCATEGORY,count(*) as TotalCount,Round(sum(TOTALPRICE),2) as Total from ORDERS " +
                    "inner join CUSTOMERS C on ORDERS.CUSTOMERID = C.CUSTOMERID " +
                    "group BY C.CUSTOMERCATEGORY " +
                    "order by Total DESC;");

            while (resultSet.next()) {
                logger.info("Category {} -> Total Number :{}, Cost Of Purchases : {} ",
                        resultSet.getString("CustomerCategory"),
                        resultSet.getInt("TotalCount"),
                        resultSet.getBigDecimal("Total"));

            }
        } catch (SQLException ex) {
            logger.error("Failed to print Report Per Customer Category: {} ", ex.toString());
        }

    }

    public void getPreviewReportPerPaymentMethod() {
        try {
            ResultSet resultSet = statement.executeQuery("select ord.PAYMENTMETHOD,count(*) as TotalCount,Round(sum(TOTALPRICE),2) as Total from ORDERS ord " +
                    "GROUP BY ord.PAYMENTMETHOD " +
                    "order by Total DESC;");

            while (resultSet.next()) {
                logger.info("Payment Method {} -> Total Number :{}, Cost Of Purchases : {} ",
                        resultSet.getString("PaymentMethod"),
                        resultSet.getInt("TotalCount"),
                        resultSet.getBigDecimal("Total"));

            }
        } catch (SQLException ex) {
            logger.error("Failed to print Report Per Payment Method: {} ", ex.toString());
        }

    }

    public void getPreviewReportCustomerByMostExpensiveProduct() {
        try {
            ResultSet resultSet = statement.executeQuery(" " +
                    "select C.NAME,C.SURNAME,C.CUSTOMERID,pr.NAME,MAX(pr.PRICE) as MostExpensiveProductPrice,count(pr.PRODUCTID) as ProductPurchases,pr.NAME as PrName from ORDERITEMS ordI " +
                    "inner join PRODUCTS pr on pr.PRODUCTID = ordI.PRODUCTSID " +
                    "inner join ORDERS O on ordI.ORDERID = O.ORDERID " +
                    "inner join CUSTOMERS C on C.CUSTOMERID = O.CUSTOMERID " +
                    "group by pr.PRICE,pr.PRODUCTID,C.CUSTOMERID,pr.NAME " +
                    "order by PRICE DESC;");

            while (resultSet.next()) {
                logger.info("Most Expensive Products ->Customer Id : {} , Name : {} , Surname : {} , Product Name : {} , Price : {}, Products Purchases : {} ",
                        resultSet.getInt("CustomerId"),
                        resultSet.getString("Name"),
                        resultSet.getString("Surname"),
                        resultSet.getString("PrName"),
                        resultSet.getBigDecimal("MostExpensiveProductPrice"),
                        resultSet.getInt("ProductPurchases"));

            }
        } catch (SQLException ex) {
            logger.error("Failed to print Customer Report By Most Expensive Product: {} ", ex.toString());
        }

    }
}

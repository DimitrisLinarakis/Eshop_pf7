package com.pf7.eshop.dao;

import com.pf7.eshop.controller.DatabaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDAO {
    private final Statement statement;
    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    public ReportDAO() {
        this.statement = DatabaseController.getStatement();
    }


    public void getPreviewReportPerParticularCustomer(int CustomerId) {
        try {
            ResultSet resultSet = statement.executeQuery("select C.NAME as Name,C.SURNAME as Surname,count(*) as TotalCount,Round(sum(TOTALPRICE),2) as Total from ORDERS ord " +
                    "inner join CUSTOMERS C on ord.CUSTOMERID = C.CUSTOMERID " +
                    "where ord.CUSTOMERID = '" + CustomerId + "'" +
                    "group by C.NAME, C.SURNAME " +
                    "order BY Total DESC;");

            if (resultSet.next()) {
                do {
                    logger.info("Total And Cost Per Particular Customer -> Name : {}, Surname:{}, Total Number :{}, Cost Of Purchases : {}",
                            resultSet.getString("Name"),
                            resultSet.getString("Surname"),
                            resultSet.getInt("TotalCount"),
                            resultSet.getBigDecimal("Total"));

                }while (resultSet.next());
            } else {
                logger.error("No Data \n");
            }
        } catch (SQLException ex) {
            logger.error("Failed to print Report Per Particular Customer : {}", ex.toString());
        }

    }

    public void getPreviewReportPerCustomerCategory() {
        try {
            ResultSet resultSet = statement.executeQuery("select C.CUSTOMERCATEGORY as Category,count(*) as TotalCount,Round(sum(TOTALPRICE),2) as Total from ORDERS " +
                    "inner join CUSTOMERS C on ORDERS.CUSTOMERID = C.CUSTOMERID " +
                    "group BY C.CUSTOMERCATEGORY " +
                    "order by Total DESC;");

            if (resultSet.next()) {
                do {
                    logger.info("Category {} -> Total Number :{}, Cost Of Purchases : {} ",
                            resultSet.getString("Category"),
                            resultSet.getInt("TotalCount"),
                            resultSet.getBigDecimal("Total"));

                }while (resultSet.next());
            } else {
                logger.error("No Data \n");
            }
        } catch (SQLException ex) {
            logger.error("Failed to print Report Per Customer Category: {} ", ex.toString());
        }
    }

    public void getPreviewReportPerPaymentMethod() {
        try {
            ResultSet resultSet = statement.executeQuery("select ord.PAYMENTMETHOD as Method,count(*) as TotalCount,Round(sum(TOTALPRICE),2) as Total from ORDERS ord " +
                    "GROUP BY ord.PAYMENTMETHOD " +
                    "order by Total DESC;");

            if (resultSet.next()) {
                do {
                    logger.info("Payment Method {} -> Total Number :{}, Cost Of Purchases : {} ",
                            resultSet.getString("Method"),
                            resultSet.getInt("TotalCount"),
                            resultSet.getBigDecimal("Total"));

                }while (resultSet.next());
            } else {
                logger.error("No Data \n");
            }
        } catch (SQLException ex) {
            logger.error("Failed to print Report Per Payment Method: {} ", ex.toString());
        }

    }

    public void getPreviewReportCustomerByMostExpensiveProduct() {
        try {
            ResultSet resultSet = statement.executeQuery(" " +
                    "SELECT P.NAME,P.PRICE,C.NAME as CustomerName,C.SURNAME,C.CUSTOMERID,SUM(ORDI.QUANTITY) as QUANTITY,P.PRODUCTID FROM ORDERITEMS ORDI " +
                    "INNER JOIN PRODUCTS P on ORDI.PRODUCTSID = P.PRODUCTID " +
                    "INNER JOIN ORDERS O on O.ORDERID = ORDI.ORDERID " +
                    "INNER JOIN CUSTOMERS C on C.CUSTOMERID = O.CUSTOMERID " +
                    "WHERE P.PRICE = (SELECT MAX(pr.PRICE) FROM PRODUCTS pr inner join ORDERITEMS ordI on pr.PRODUCTID = ordI.PRODUCTSID) " +
                    "GROUP BY P.NAME, P.PRICE,C.CUSTOMERID,C.NAME,P.PRODUCTID " +
                    "ORDER BY SUM(ORDI.QUANTITY) DESC;");

            if (resultSet.next()) {
                do {
                    logger.info("Most Expensive Products ->Customer Id : {} , Name : {} , Surname : {} , Product Name : {} , Price : {}, Products Purchases : {} ",
                            resultSet.getInt("CustomerId"),
                            resultSet.getString("CustomerName"),
                            resultSet.getString("Surname"),
                            resultSet.getString("Name"),
                            resultSet.getBigDecimal("Price"),
                            resultSet.getInt("Quantity"));

                }while (resultSet.next());
            } else {
                logger.error("No Data \n");
            }
        } catch (SQLException ex) {
            logger.error("Failed to print Customer Report By Most Expensive Product: {} ", ex.toString());
        }

    }
}

package com.pf7.eshop.controller;

import com.pf7.eshop.dao.CustomerDAO;
import com.pf7.eshop.dao.ReportDAO;
import com.pf7.eshop.model.PaymentMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final ReportDAO reportDAO;
    private final CustomerDAO customerDAO;

    public ReportController() {
        reportDAO = new ReportDAO();
        customerDAO = new CustomerDAO();
    }

    public void previewReportPerParticularCustomer() {

        customerDAO.showCustomersTable();
        logger.info("Please give Customer Id: ");
        int customerId = scanner.nextInt();

        while (!customerDAO.customerExists(customerId)) {
            logger.info("Please give a valid Customer Id: ");
            customerId = scanner.nextInt();
        }

        reportDAO.getPreviewReportPerParticularCustomer(customerId);
    }

    public void previewReportPerCustomerCategory() {

        logger.info("Show Results Per Customer Category : \n");
        reportDAO.getPreviewReportPerCustomerCategory();
        logger.info("\n");
    }

    public void previewReportPerPaymentMethod() {

        logger.info("Show Results Per Payment Method : \n");
        reportDAO.getPreviewReportPerPaymentMethod();
        logger.info("\n");
    }

    public void previewReportCustomerByMostExpensiveProduct() {

        logger.info("Show Results For Most Expensive Purchased Product And How Many Times : \n");
        reportDAO.getPreviewReportCustomerByMostExpensiveProduct();
        logger.info("\n");
    }
}

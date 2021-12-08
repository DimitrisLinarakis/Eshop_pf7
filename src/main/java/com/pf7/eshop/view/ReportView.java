package com.pf7.eshop.view;

import com.pf7.eshop.controller.ReportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class ReportView {

    private static final Logger logger = LoggerFactory.getLogger(ProductView.class);
    private final Scanner scanner = new Scanner(System.in);
    private final ReportController reportController;

    public ReportView() {
        reportController = new ReportController();
    }

    public void createReportMenu() {
        do {
            logger.info("1. Select Total number and cost of purchases for a particular customer.");
            logger.info("2. Select Total number and cost of purchases per customer category.");
            logger.info("3. Select Total number and cost of purchases per payment method.");
            logger.info("4. Select The customer(s) who purchased the most expensive product and how many times.");
            logger.info("5. Return to menu.\n");

            logger.info("Please, select category: ");

            switch (scanner.nextInt()) {
                case 1 -> reportController.previewReportPerParticularCustomer();
                case 2 -> reportController.previewReportPerCustomerCategory();
                case 3 -> reportController.previewReportPerPaymentMethod();
                case 4 -> reportController.previewReportCustomerByMostExpensiveProduct();
                case 5-> {
                    return;
                }
                default -> logger.info("Please select a valid category!");
            }
        } while (true);
    }
}

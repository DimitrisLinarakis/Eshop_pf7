package com.pf7.eshop.basic;

import com.pf7.eshop.models.DatabaseConnection;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // hello world
        try{
            DatabaseConnection.Connect();
        } catch(Exception e) {
            logger.error("Error {}", e);
        }
    }
}

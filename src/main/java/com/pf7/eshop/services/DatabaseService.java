package com.pf7.eshop.services;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    private static final String DB_CONNECTION_URL_MEMORY_MODE = "jdbc:h2:./eshopDB";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    private static Server server;
    private static Statement statement;

    public static void createConnection() throws SQLException {
        server = Server.createTcpServer("-tcpAllowOthers", "-tcpDaemon");
        server.start();
        logger.info("H2 server has started with status '{}'.", server.getStatus());

        org.h2.Driver.load();
        logger.info("H2 JDBC driver server has been successfully loaded.");

        Connection connection = DriverManager.getConnection(DB_CONNECTION_URL_MEMORY_MODE, DB_USERNAME, DB_PASSWORD);
        statement = connection.createStatement();
    }

    public static void stopServer(){
        server.stop();
        server.shutdown();
        logger.info("H2 server has been shutdown.");
    }

    public static Statement getStatement(){
        return statement;
    }

}

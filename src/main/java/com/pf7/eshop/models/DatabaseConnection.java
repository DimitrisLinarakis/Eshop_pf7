package com.pf7.eshop.models;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String DB_CONNECTION_URL_MEMORY_MODE = "jdbc:h2:./Eshop";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    private static Server server;

    public static Statement Connect() throws SQLException {
        server = Server.createTcpServer("-tcpAllowOthers", "-tcpDaemon");
        server.start();
        logger.info("H2 server has started with status '{}'.", server.getStatus());

        org.h2.Driver.load();
        logger.info("H2 JDBC driver server has been successfully loaded.");

        Connection connection = DriverManager.getConnection(DB_CONNECTION_URL_MEMORY_MODE, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();

        return statement;
    }
}

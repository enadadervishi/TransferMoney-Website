package com.lucy1098.assignments.website.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.UnavailableException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private DbConnection() {}

    private static DataSource dataSource = null;

    public static Connection getConnection() throws UnavailableException {
        try {
            if (dataSource == null) {
                synchronized (DbConnection.class) {
                    if (dataSource == null) {
                        initializeDataSource();
                    }
                }
            }

            return dataSource.getConnection();
        } catch (IOException e) {
            throw new UnavailableException("Error accessing db properties: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new UnavailableException("Can't load database driver: " + e.getMessage());
        } catch (SQLException e) {
            throw new UnavailableException("Couldn't get db connection: " + e.getMessage());
        }
    }

    private static void initializeDataSource() throws IOException, ClassNotFoundException {
        Properties properties = new Properties();
        try (InputStream inputStream = DbConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(inputStream);
        }

        Class.forName(properties.getProperty("DB_DRIVER_CLASS"));

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty("DB_DRIVER_CLASS"));
        dataSource.setUrl(properties.getProperty("DB_URL"));
        dataSource.setUsername(properties.getProperty("DB_USERNAME"));
        dataSource.setPassword(properties.getProperty("DB_PASSWORD"));

        DbConnection.dataSource = dataSource;
    }
}

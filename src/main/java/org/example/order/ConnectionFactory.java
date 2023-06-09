package org.example.order;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
    private static boolean initiated;

    public static Connection getConnection() throws SQLException {
        initialize();
        return dataSource.getConnection();
    }

    private static void initialize() {
        if (initiated) return;

        Properties props = new Properties();
        try {
            props.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("databaseorder.properties"));

            dataSource.setDriverClass(props.getProperty("db.driver"));
            dataSource.setJdbcUrl(props.getProperty("db.url"));
            dataSource.setUser(props.getProperty("db.user"));
            dataSource.setPassword(props.getProperty("db.password"));

            initiated = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

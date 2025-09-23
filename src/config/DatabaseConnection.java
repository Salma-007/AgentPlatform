package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3307/agentplatform_db";
    private static final String user = "root";
    private static final String password = "";

    private static Connection connection;
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            synchronized (DatabaseConnection.class) {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("connected");
                }
            }
        }
        return connection;
    }
}

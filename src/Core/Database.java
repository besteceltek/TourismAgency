package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Database instance = null;
    private Connection connection = null;

    // Defined PostgreSQL variables
    private final String DB_URL = "jdbc:postgresql://localhost:5432/tourismagency";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "1234";

    private Database() { // Database constructor for connection
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Database();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instance.getConnection();
    }
}

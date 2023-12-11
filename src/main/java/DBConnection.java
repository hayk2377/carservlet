

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            // Check if the connection is null before creating a new one
            if (conn == null || conn.isClosed()) {
                // Assuming "jdbc:sqlite:../cardb/sample.db" is your JDBC URL
                String url = "jdbc:sqlite:src/main/cardb/cardb.db"
;
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        } finally {
            conn = null; // Set the variable to null regardless of whether the close operation succeeds
        }
    }
}

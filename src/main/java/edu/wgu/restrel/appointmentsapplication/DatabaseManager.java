package edu.wgu.restrel.appointmentsapplication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class DatabaseManager {
    Connection conn = null; // connection to database

    private static String URL = "jdbc:mysql://localhost:3306/client_schedule";
    private static String USERNAME = "restrella";
    private static String PASSWORD = "@369_rE!";

    // constuctor
    public DatabaseManager() {
        connect();
    }

    public static Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    public void connect() {
        try {
            conn = getConnection();
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from database.");
        } catch (Exception e) {
            System.out.println("Error disconnecting from database.");
            e.printStackTrace();
        }
    }

    public void runQuery(String sql, QueryExecutor executor, Object... args) {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {

            // create a PreparedStatement with the given SQL and parameters
            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }

            // execute the query and pass the result set to the executor
            try (ResultSet rs = pstmt.executeQuery()) {
                executor.execute(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface QueryExecutor {
        void execute(ResultSet rs) throws SQLException;
    }

}

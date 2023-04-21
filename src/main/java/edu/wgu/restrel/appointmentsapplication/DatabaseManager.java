package edu.wgu.restrel.appointmentsapplication;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * DatabaseManager singleton class for connecting to the database and running
 * queries.
 */
public class DatabaseManager {
    Connection conn = null; // connection to database

    private static String URL = "jdbc:mysql://localhost:3306/client_schedule";
    private static String USERNAME = "restrella";
    private static String PASSWORD = "@369_rE!";

    /**
     * Connect to the database upon instantiation.
     */
    public DatabaseManager() {
        connect();
    }

    /**
     * Gets a connection to the database.
     *
     * @return the connection
     * @throws SQLException if there is an error connecting to the database
     */
    public static Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    /**
     * Connects to the database and sets the connection field. catches SQLException
     */
    public void connect() {
        try {
            conn = getConnection();
            System.out.println("Connected to database.");
        } catch (SQLException e) {
            System.out.println("Error connecting to database.");
            e.printStackTrace();
        }
    }

    /**
     * Disconnects from the database and sets the connection field to null.
     */
    public void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from database.");
        } catch (Exception e) {
            System.out.println("Error disconnecting from database.");
            e.printStackTrace();
        }
    }

    /**
     * Runs a query on the database and passes the result set to the given executor.
     * LAMDA EXPRESSION USED
     * accepts a string of the query and a QueryExecutor object as well as any
     * number of parameters that the query needs
     * 
     * @param sql
     * @param executor
     * @param args
     */
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

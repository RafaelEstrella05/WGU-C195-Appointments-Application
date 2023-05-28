package edu.wgu.restrel.appointmentsapplication.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * DatabaseManager singleton class for connecting to the database and running
 * queries.
 */
public class DatabaseManager {
    Connection conn = null; // connection to database

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    /**
     * Connect to the database upon instantiation.
     */
    public DatabaseManager() {

        URL = "jdbc:mysql://localhost:3306/client_schedule";
        USERNAME = "restrella";
        PASSWORD = "@369_rE!";

        connect();

    }

    public void setUrl(String url) {
        URL = url;
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
     * accepts a string of the query and a QueryExecutor object as well as any
     * number of parameters that the query needs
     * 
     * @param sql
     * @param executor
     * @param args     1,2,3,...,n
     */
    public void executeQuery(String sql, QueryExecutor executor, Object... args) {
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

    /**
     * this method is used to execute an insert statement on the database, accepts a
     * string of the query, an executor and any number of parameters that the query
     * needs
     * 
     * @param sql
     * @param executor
     * @param args     1,2,3,...,n
     */
    public void executeInsert(String sql, QueryExecutor executor, Object... args) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Insert statement executed successfully.");

            // Pass the ResultSet to the QueryExecutor
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                executor.execute(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is used to execute an update statement on the database, accepts
     * multiple parameters
     * 
     * @param sql
     * @param executor
     * @param args
     */
    public void executeUpdate(String sql, QueryExecutor executor, Object... args) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Update statement executed successfully.");

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                executor.execute(rs);
            } catch (SQLException e) {
                e.printStackTrace();
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

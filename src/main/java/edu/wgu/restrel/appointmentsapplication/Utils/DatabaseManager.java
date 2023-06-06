package edu.wgu.restrel.appointmentsapplication.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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

        loadDatabaseProperties();
        connect();

    }

    private void loadDatabaseProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.properties");

            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();

                URL = properties.getProperty("url");
                USERNAME = properties.getProperty("username");
                PASSWORD = properties.getProperty("password");

                connect();
            } else {
                throw new FileNotFoundException("database.properties file not found.");
            }
        } catch (IOException e) {
            System.out.println("Error loading database properties: " + e.getMessage());
        }
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

            // set the parameters using a List
            List<Object> argList = Arrays.asList(args);
            for (int i = 0; i < argList.size(); i++) {
                pstmt.setObject(i + 1, argList.get(i));
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
     * this method is used to execute an update, delete or insert statement
     * accepts multiple parameters
     * 
     * @param sql
     * @param executor
     * @param args
     */
    public void executeUpdate(String sql, QueryExecutor executor, Object... args) {
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the parameters using a List
            List<Object> argList = Arrays.asList(args);
            for (int i = 0; i < argList.size(); i++) {
                pstmt.setObject(i + 1, argList.get(i));
            }

            // execute the query and pass the result set to the executor
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

package edu.wgu.restrel.appointmentsapplication.Models;

/**
 * User class for storing user information
 * 
 * @author Rafael Estrella Paz
 * @version 1.0
 */
public class User {
    private int id;
    private String userName;
    private String password;

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns the ID of the user that is logged in
     * 
     * @return id Unique identifier for user
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user name
     * 
     * @return userName login Name of user
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password
     * 
     * @return password Password of user
     */
    public String getPassword() {
        return password;
    }
}

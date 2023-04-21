package edu.wgu.restrel.appointmentsapplication;

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
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the user name
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password
     * @return
     */
    public String getPassword() {
        return password;
    }
}

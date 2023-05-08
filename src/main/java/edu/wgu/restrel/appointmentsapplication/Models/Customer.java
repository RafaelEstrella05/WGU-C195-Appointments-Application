package edu.wgu.restrel.appointmentsapplication.Models;

/*
 * 
 * CUSTOMERS
 * Customer_ID INT(10) (PK)
 * Customer_Name VARCHAR(50)
 * Address VARCHAR(100)
 * Postal_Code VARCHAR(50)
 * Phone VARCHAR(50)
 * Create_Date DATETIME
 * Created_By VARCHAR(50)
 * Last_Update TIMESTAMP
 * Last_Updated_By VARCHAR(50)
 * Division_ID INT(10) (FK)
 * FIRST-LEVEL DIVISIONS
 * Division_ID INT(10) (PK)
 * Division VARCHAR(50)
 * Create_Date DATETIME
 * Created_By VARCHAR(50)
 * Last_Update TIMESTAMP
 * Last_Updated_By VARCHAR(50)
 * Country_ID INT(10) (FK)
 * COUNTRIES
 * Country_ID INT(10) (PK)
 * Country VARCHAR(50)
 * Create_Date DATETIME
 * Created_By VARCHAR(50)
 * Last_Update TIMESTAMP
 * Last_Updated_By VARCHAR(50)
 * APPOINTMENTS
 * Appointment_ID INT(10) (PK)
 * Title VARCHAR(50)
 * Description VARCHAR(50)
 * Location VARCHAR(50)
 * Type VARCHAR(50)
 * Start DATETIME
 * End DATETIME
 * Create_Date DATETIME
 * Created_By VARCHAR(50)
 * Last_Update TIMESTAMP
 * Last_Updated_By VARCHAR(50)
 * Customer_ID INT(10) (FK)
 * User_ID INT(10) (FK)
 * Contact_ID INT(10) (FK)
 * USERS
 * User_ID INT(10) (PK)
 * User_Name VARCHAR(50) (UNIQUE)
 * Password TEXT
 * Create_Date DATETIME
 * Created_By VARCHAR(50)
 * Last_Update TIMESTAMP
 * Last_Updated_By VARCHAR(50)
 * CONTACTS
 * Contact_ID INT(10) (PK)
 * Contact_Name VARCHAR(50)
 * Email VARCHAR(50)
 * 
 */

public class Customer {

    int Customer_ID;
    String Customer_Name;
    String Address;
    String Postal_Code;
    String Phone;
    String Create_Date;
    String Created_By;
    String Last_Update;
    String Last_Updated_By;
    int Division_ID;

}

# Appointments Application – WGU C195

**Performance Assessment for Software II – Advanced Java Concepts (C195)**  
**Author:** Rafael Estrella Paz  
**Version:** 1.0  
**Date:** June 20, 2023  

---

## 📌 Purpose

The Appointments Application is designed to help businesses with multiple locations schedule and manage appointments across different regions. It enables employees to:
- Schedule appointments with customers.
- View, modify, and track customer information.
- Access appointment data for various time zones and divisions.

---

## 👤 Author Info

- **Name:** Rafael Estrella Paz  
- **Email:** restrel@wgu.edu  
- **Phone:** +1 (512) 351-0458

---

## 🛠️ Development Tools & Resources

- **IDE:** IntelliJ IDEA 2022.3.1 (Community Edition)  
- **Java SDK:** Java SE Development Kit 17.0.6  
- **Database:** MySQL Server 8.0.25  
- **JDBC Driver:** mysql-connector-j-8.0.33.jar  
- **JavaFX Version:** org.openjfx:javafx-base:17.0.2 (via Maven)

---

## 🚀 How to Run the Application in IntelliJ

### 1. Dependency Setup
1. Open the project in IntelliJ IDEA.
2. Go to `File` → `Project Structure` → `Modules` → `Dependencies`.
3. Ensure `mysql-connector-java-8.0.33.jar` is listed as a dependency.

### 2. Database Setup
1. Navigate to `src/main/resources/database.properties`.
2. Update the following fields:
   - `server`: Set to your MySQL server IP or `localhost`.
   - `port`: Match your MySQL port (default is 3306).
   - `username` and `password`: Enter credentials with access to the `client_schedule` database.

### 3. Start the Application
1. Run `src/main/java/restrel/appointmentsapplication/AppointmentsApplication`.
2. The login screen should appear.
3. Enter the user credentials and click **Submit** to log in.

---

## 📊 Additional Report – Customer Count by Division and Country

This report provides a summary of the number of customers grouped by division and country. It helps identify:
- Areas with the highest customer concentration.
- Opportunities for marketing and staffing.

### 💡 Use Case
Use this data to compare customer count against appointment frequency. If divisions with high customer counts have low appointments, consider boosting marketing or support in those regions.

### 📄 SQL Query
```sql
SELECT d.Division, co.Country, COUNT(c.Customer_ID) AS `Total Customers`
FROM customers c
JOIN first_level_divisions d ON d.Division_ID = c.Division_ID
JOIN countries co ON co.Country_ID = d.Country_ID
GROUP BY d.Division, co.Country
ORDER BY d.Division, co.Country;

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

## 🗃️ Database Setup

To populate the required database schema and data:

1. Ensure MySQL Server is running.
2. Use the provided `Dump20230414.sql` file to set up the `client_schedule` database.
   - You can run this file using MySQL Workbench or the MySQL command line:

   **Using MySQL Workbench:**
   - Open MySQL Workbench.
   - Connect to your server.
   - Open the `Dump20230414.sql` file.
   - Execute the script to create and populate the database.

   **Using MySQL CLI:**
   ```bash
   mysql -u your_username -p < Dump20230414.sql

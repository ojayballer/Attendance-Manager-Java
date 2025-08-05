# 🎓 Student Attendance Manager (Java + Swing + MySQL)

This is a **Java-based Student Attendance Management System** built with **Swing** for the GUI and **MySQL** as the database backend. It allows you to **add, update, delete, and view student attendance records** for multiple subjects.

---

## 📋 Features

- ✅ Add student attendance data (Name, Matric Number, Subject, Total Classes, Classes Attended)
- ✏️ Edit existing records
- ❌ Delete students by Matric Number
- 📊 View records in a scrollable JTable
- 📈 Calculates total attendance percentage automatically
- 🔗 Connected to a MySQL database using JDBC
- 💻 User-friendly interface using Java Swing

---

## 🧱 Project Structure

```
Attendance-Manager-Java/
├── src/
│   ├── Attendance.java      # Main GUI and logic
│   ├── attest.java          # Launches the GUI
│   └── Database (MySQL DB)  # Contains attendance table
```

---

## 🗃️ MySQL Database Schema

Database: `intern`

Table: `attendance`

```sql
CREATE TABLE attendance (
    matric_number VARCHAR(20) PRIMARY KEY,
    NAME VARCHAR(100),
    SUBJECT VARCHAR(20),
    TOTAL_CLASSES INT,
    CLASSES_ATTENDED INT,
    TOTAL_ATTENDANCE VARCHAR(10)
);
```

---

## 🚀 How to Run

### 1. Set up the Database

1. Open MySQL
2. Create database: `intern`
3. Run the SQL script above to create the `attendance` table.

### 2. Update JDBC connection in `Attendance.java`

```java
// Replace with your actual MySQL credentials
Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/intern", "root", "password");
```

### 3. Compile and Run

You can compile and run using IntelliJ, Eclipse, or the terminal:

```bash
javac Attendance.java attest.java
java attest
```

---

## 📸 GUI Preview

> The application uses Java Swing and displays input fields on the left and the student table on the right. Clicking a student row populates the fields for editing.

---

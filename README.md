# 📊 Employee Collaboration Analyzer

This application processes employee project data from CSV files to determine **pairs of employees who have worked together on the same projects**, along with the **total number of days they've collaborated per project**.

---

## 🚀 Features

- Upload CSV files via a modern, responsive UI.
- Supports drag & drop or file picker for uploads.
- Parses a wide variety of date formats (including timezones).
- Computes employee pairs who worked together, showing:
    - Employee ID #1
    - Employee ID #2
    - Project ID
    - Days Worked Together
- Displays results in a clean table.
- Frontend styled with a user-friendly modal upload form.
- Backend service to process CSV files and return results via REST API.

---

## 🗂️ CSV Format

Your input CSV should have the following columns:

- `EmpID`: Employee ID
- `ProjectID`: Project identifier
- `DateFrom`: Start date of the employee on the project
- `DateTo`: End date on the project (`NULL` means current date)

### Example
EmpID,ProjectID,DateFrom,DateTo
143,12,2013-11-01,2014-01-05
218,10,05/16/2012,NULL

---

## 🛠️ Technologies Used

- **Frontend:** Angular 19, HTML, CSS
- **Backend:** Java Spring Boot
- **Testing:** JUnit
- **Build Tools:** Maven / Gradle

---

## 🧩 Setup Instructions

### Backend
1. Navigate to the backend directory:
    ```bash
    cd employee-service
    ```
2. Build and run the application:
    ```bash
    ./mvnw spring-boot:run
    ```
3. Backend API endpoint:
    ```
    POST http://localhost:8080/api/employee/upload
    ```

### Frontend
1. Navigate to the frontend directory:
    ```bash
    cd employee-client
    ```
2. Install dependencies:
    ```bash
    npm install
    ```
3. Run the development server:
    ```bash
    ng serve
    ```
4. Access the app:
    ```
    http://localhost:4200
    ```

---

## ✅ Tests

Backend unit tests are located at: 

src/test/java/com/sub/employeeservice

To run tests:
```bash
./mvnw test


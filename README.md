# AwesomeGIC Bank - Application Setup and Run Guide

This README will guide you through the process of setting up and running both the backend and frontend of the AwesomeGIC Bank application.

## Backend Setup

The backend of the application is built with **Spring Boot**.

### Backend Prerequisites
- **Java 17** (or later) installed.
- **Maven** for building and running the Spring Boot application.
- **Postman** or any API testing tool for testing the backend API.

### Backend Setup and Running

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd bankAccountInterestBackend
   ```

2. **Build the application**:
   Use Maven to build the backend project:
   ```bash
   mvn clean install
   ```

3. **Run the Spring Boot application**:
   After the build is complete, you can run the backend using the following command:
   ```bash
   mvn spring-boot:run
   ```

   By default, the backend will run on:
    - **Localhost:** `http://localhost:8080`

4. **Verify Backend Running**:
   Once the backend is up and running, you can open Postman or any similar API testing tool and test the following APIs:

    - **POST /api/accounts/transaction**: To add a transaction (Deposit or Withdrawal).
    - **GET /api/accounts/{accountId}**: To fetch account details.
    - **GET /api/accounts/{accountId}/statement**: To fetch the account statement for a specific month.

---


Swagger UI
Swagger UI is enabled for testing and exploring the backend APIs. You can access the Swagger UI at the following URL:

Swagger UI URL: http://localhost:8080/swagger-ui/index.html


## Frontend Setup

The frontend of the application is built using **Angular**.

### Frontend Prerequisites
- **Node.js 16** (or later) installed.
- **Angular CLI** installed globally:
   ```bash
   npm install -g @angular/cli
   ```

### Frontend Setup and Running

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd bankAccountInterestFrontend
   ```

2. **Install frontend dependencies**:
   Run the following command to install all necessary packages:
   ```bash
   npm install
   ```

3. **Run the Angular application**:
   Start the frontend application using Angular CLI:
   ```bash
    npm start or ng serve
   ```

   By default, the frontend will be available at:
   - **Localhost:** `http://localhost:4200`

4. **Verify Frontend Running**:
   Open your browser and navigate to `http://localhost:4200`. You should see the **AwesomeGIC Bank** interface.

---

## Testing

### Backend Testing

1. **Unit Tests**:
   You can run unit tests for the backend using Maven:
   ```bash
   mvn test
   ```

   This will execute all the tests in the backend code and display results in the terminal.

2. **API Testing**:
   - You can use **Postman** to test all the API endpoints exposed by the backend.
   - Refer to the `AccountController` and `InterestRuleController` for the available endpoints.

3. **Swaffer**:
  -Swagger UI URL: http://localhost:8080/swagger-ui/index.html

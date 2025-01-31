Here's a README document for both the frontend and backend of your project:

---

# AwesomeGIC Bank - Application Setup and Run Guide

This README will guide you through the process of setting up and running both the backend and frontend of the AwesomeGIC Bank application.

## Table of Contents
- [Backend Setup](#backend-setup)
    - [Prerequisites](#backend-prerequisites)
    - [Setup and Running](#backend-setup-and-running)
- [Frontend Setup](#frontend-setup)
    - [Prerequisites](#frontend-prerequisites)
    - [Setup and Running](#frontend-setup-and-running)
- [Testing](#testing)

---

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
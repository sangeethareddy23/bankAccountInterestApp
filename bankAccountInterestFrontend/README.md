
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

### Frontend Testing

1. **Unit Tests**:
   Run the unit tests in Angular using the following command:
   ```bash
   ng test
   ```

2. **E2E Testing**:
   Run end-to-end tests using:
   ```bash
   ng e2e
   ```

---

## Notes
- Ensure that the **backend** is running before starting the **frontend**, as the frontend makes requests to the backend API.
- The **backend** and **frontend** should be connected by the default API URL: `http://localhost:8080/api/` for the backend and `http://localhost:4200/` for the frontend.
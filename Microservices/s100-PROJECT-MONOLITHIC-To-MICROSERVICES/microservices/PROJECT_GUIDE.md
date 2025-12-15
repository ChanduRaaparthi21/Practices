# S-100 Exchange Set Builder - Project Guide

This document provides a comprehensive guide on the architecture, internal workings, and usage of the S-100 Exchange Set Builder microservices project.

## 1. Architecture Overview

The project is built using a **Microservices Architecture** with Spring Boot.

### Core Components:

1.  **Service Registry (Eureka Server)**:
    *   **Role**: The "Phonebook". All services register here so they can find each other.
    *   **Port**: 8761

2.  **API Gateway**:
    *   **Role**: The "Front Door". All external requests go through here. It routes requests to the appropriate service.
    *   **Port**: 8080

3.  **Auth Service**:
    *   **Role**: Identity Provider. Handles User Registration and Login. Issues JWT (JSON Web Tokens).
    *   **Port**: 8081

4.  **Exchange Service**:
    *   **Role**: Domain Logic. Manages Exchange Sets, creates S-100 XML catalogs, signs files, and packages them.
    *   **Port**: 8082

5.  **Resource Service**:
    *   **Role**: File Management. Handles uploading and storage of certificates, private keys, and other resources.
    *   **Port**: 8083

6.  **Audit Service**:
    *   **Role**: Logging. Records important actions (who did what and when) from other services.
    *   **Port**: 8084

---

## 2. Internal Communication (How Services Talk)

Services communicate with each other using **Spring Cloud OpenFeign**. This allows them to call each other like simple Java method calls, while handling the HTTP requests internally.

### Example Flow: Creating an Exchange Set

1.  **User Request**: You send a POST request to `http://localhost:8080/api/exchange-sets` (Gateway).
2.  **Gateway Routing**: The Gateway sees `/api/exchange-sets` and forwards the request to the **Exchange Service**.
3.  **Exchange Service Logic**:
    *   The service creates the Exchange Set folder structure.
    *   It generates the `CATALOG.XML` file.
4.  **Inter-Service Call (Audit)**:
    *   The `ExchangeSetService` needs to log this action.
    *   It uses `AuditClient` (a Feign Client) to call the **Audit Service**.
    *   `auditClient.logAction(...)` -> sends HTTP POST to `http://audit-service/api/audit`.
5.  **Audit Service**: Receives the log and saves it to the database.
6.  **Response**: Exchange Service returns the created object to the user.

### Diagram
```
User -> API Gateway -> Exchange Service
                            |
                            +---> (Feign Client) ---> Audit Service
```

---

## 3. Authentication Flow (Current State)

Currently, the authentication flow is designed as follows:

1.  **Login**: User sends credentials to `http://localhost:8080/api/auth/login`.
2.  **Token**: Auth Service validates credentials and returns a **JWT Token**.
3.  **Access**: For subsequent requests (e.g., creating an exchange set), the client *should* include this Token in the `Authorization` header (`Bearer <token>`).
    *   *Note: Currently, the services are configured to trust requests. Implementing strict Token Validation at the Gateway or Service level is a recommended next step.*

---

## 4. How to Use (Step-by-Step)

### Prerequisites
Ensure all services are running (via Docker Compose or Maven).

### Step 1: Register a User
*   **URL**: `POST http://localhost:8080/api/auth/register`
*   **Body**:
    ```json
    {
        "username": "admin",
        "password": "password123",
        "email": "admin@example.com",
        "role": "ADMIN"
    }
    ```

### Step 2: Login
*   **URL**: `POST http://localhost:8080/api/auth/login`
*   **Body**:
    ```json
    {
        "username": "admin",
        "password": "password123"
    }
    ```
*   **Response**: You will receive a `token`. Copy this token.

### Step 3: Upload a Private Key (Resource Service)
*   **URL**: `POST http://localhost:8080/api/resources?type=PRIVATE_KEY`
*   **Headers**: `Authorization: Bearer <your_token>`
*   **Body**: Form-Data with key `file` containing your `.pem` or `.key` file.

### Step 4: Create an Exchange Set (Exchange Service)
*   **URL**: `POST http://localhost:8080/api/exchange-sets?name=MyExchangeSet`
*   **Headers**: `Authorization: Bearer <your_token>`

### Step 5: Sign the Exchange Set
*   **URL**: `POST http://localhost:8080/api/exchange-sets/{id}/sign?privateKeyId={resourceId}`
*   **Headers**: `Authorization: Bearer <your_token>`
*   *Note: This triggers an internal call to Audit Service.*

### Step 6: View Audit Logs
*   **URL**: `GET http://localhost:8080/api/audit`
*   **Response**: You should see logs for "EXCHANGE_SET_CREATED", "RESOURCE_ADDED", etc.

---

## 5. Directory Structure & Key Files

*   **`microservices/`**: Root directory.
    *   **`docker-compose.yml`**: Orchestrates all services.
    *   **`api-gateway/`**:
        *   `application.yml`: Defines routes (e.g., `/api/auth/**` -> `auth-service`).
    *   **`exchange-service/`**:
        *   `src/main/java/.../client/AuditClient.java`: The Feign Client interface for calling Audit Service.
        *   `src/main/java/.../service/ExchangeSetService.java`: Contains the business logic and calls `auditClient`.
    *   **`resource-service/`**:
        *   `src/main/java/.../client/AuditClient.java`: Feign Client for Audit Service.

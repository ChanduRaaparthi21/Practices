# TFConnect - Digital Trade Finance Platform 🏦

TFConnect is a robust, secure, and scalable Trade Finance Platform inspired by banking systems like Axis Bank's TFConnect. It facilitates corporate trade transactions like Letters of Credit (LC), Bank Guarantees, and Remittances through a secure Maker-Checker-Authorizer workflow.

## 🎯 Features
- **JWT-Based Authentication**: Secure login with Role-Based Access Control (RBAC).
- **Maker-Checker-Authorizer Workflow**: Multi-level authorization for sensitive banking transactions.
- **Audit Trails**: Complete history of who performed what action and when.
- **Credit Limit Management**: Real-time tracking of corporate limits.
- **Trade Modules**: Support for LC, Import/Export Bills, and Guarantees.
- **Swagger Documentation**: Interactive API testing via OpenAPI 3.0.

## 🛠 Tech Stack
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security (JWT)**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**
- **Swagger/Springdoc**

## 🏗 Project Architecture (Clean Layered)
- `entity`: JPA Models
- `repository`: Data Access Layer
- `service`: Business Logic & Workflow implementation
- `controller`: REST API endpoints
- `security`: JWT & Spring Security configuration
- `dto`: Data Transfer Objects for requests/responses

## 📊 Database Design (ER Diagram Summary)
- **Organizations**: Stores corporate client details.
- **Users**: Bank/Corporate users with assigned Roles.
- **TradeTransactions**: Core transaction data (Amount, Status, Reference).
- **TransactionAudits**: History of status changes (SUBMITTED -> CHECKED -> APPROVED).
- **CreditLimits**: Tracking utilization vs total limit.

## 🚀 How to Run
1. Update `application-dev.yml` with your MySQL credentials.
2. Run `mvn install`.
3. Start the application: `mvn spring-boot:run`.
4. Access Swagger UI: `http://localhost:8080/swagger-ui.html`.

## 🔑 Test Users (Seeded automatically)
- **Maker**: `maker` / `password`
- **Checker**: `checker` / `password`
- **Authorizer**: `authorizer` / `password`
- **Admin**: `admin` / `password`

## 📝 Sample API Request (Login)
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "maker",
  "password": "password"
}
```

## 📝 Sample API Request (Initiate LC)
```http
POST /api/v1/trade/initiate
Authorization: Bearer <TOKEN>
Content-Type: application/json

{
  "type": "LETTER_OF_CREDIT",
  "amount": 50000.00,
  "currency": "USD",
  "remarks": "Urgent LC for raw material import"
}
```

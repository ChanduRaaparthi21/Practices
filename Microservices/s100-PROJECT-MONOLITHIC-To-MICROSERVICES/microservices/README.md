# S-100 Exchange Set Builder - Microservices Architecture

This project has been migrated from a monolithic architecture to a microservices architecture.

## Services

1.  **Service Registry (Eureka Server)**
    *   Port: 8761
    *   Path: `service-registry`
    *   Description: Centralized service discovery.

2.  **API Gateway**
    *   Port: 8080
    *   Path: `api-gateway`
    *   Description: Entry point for all API requests. Routes requests to appropriate services.

3.  **Auth Service**
    *   Port: 8081
    *   Path: `auth-service`
    *   Description: Handles user registration, login, and JWT token generation.

4.  **Exchange Service**
    *   Port: 8082
    *   Path: `exchange-service`
    *   Description: Manages Exchange Sets, Content Items, and S-100 XML generation.

5.  **Resource Service**
    *   Port: 8083
    *   Path: `resource-service`
    *   Description: Manages file uploads (certificates, keys, etc.).

6.  **Audit Service**
    *   Port: 8084
    *   Path: `audit-service`
    *   Description: Centralized logging of user actions.

## Prerequisites

*   Java 17
*   Maven
*   MySQL (Running on localhost:3306)

## Database Setup

Ensure you have a MySQL database named `s100db` created.
The services are configured to use `root` / `chandu221299` as credentials. Update `application.yml` in each service if different.

## How to Run

### Option 1: Using Docker Compose (Recommended)

1.  Build all services:
    ```bash
    mvn clean package -DskipTests
    ```
    (Run this from the root `microservices` directory)

2.  Start services:
    ```bash
    docker-compose up --build
    ```

### Option 2: Running Locally with Maven

1.  **Start Service Registry**:
    ```bash
    cd service-registry
    mvn spring-boot:run
    ```

2.  **Start API Gateway**:
    ```bash
    cd api-gateway
    mvn spring-boot:run
    ```

3.  **Start Auth Service**:
    ```bash
    cd auth-service
    mvn spring-boot:run
    ```

4.  **Start Exchange Service**:
    ```bash
    cd exchange-service
    mvn spring-boot:run
    ```

5.  **Start Resource Service**:
    ```bash
    cd resource-service
    mvn spring-boot:run
    ```

6.  **Start Audit Service**:
    ```bash
    cd audit-service
    mvn spring-boot:run
    ```

## API Documentation

Once all services are running, you can access the API Gateway.
Swagger UI for individual services can be accessed at:
*   Auth Service: `http://localhost:8081/swagger-ui.html`
*   Exchange Service: `http://localhost:8082/swagger-ui.html`
*   Resource Service: `http://localhost:8083/swagger-ui.html`
*   Audit Service: `http://localhost:8084/swagger-ui.html`

## Architecture Diagram

Client -> API Gateway (8080) -> Service Registry (8761)
                        |
                        +-> Auth Service (8081)
                        +-> Exchange Service (8082)
                        +-> Resource Service (8083)
                        +-> Audit Service (8084)

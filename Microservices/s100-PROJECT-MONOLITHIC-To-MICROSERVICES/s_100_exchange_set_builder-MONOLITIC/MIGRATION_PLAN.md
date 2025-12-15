# Microservices Migration Plan

This document outlines the plan to migrate the monolithic `s_100_exchange_set_builder` application to a microservices architecture.

## Proposed Architecture

We will split the application into the following microservices:

1.  **Service Registry (Eureka Server)**
    *   **Port**: 8761
    *   **Responsibility**: Service discovery and registration.

2.  **API Gateway (Spring Cloud Gateway)**
    *   **Port**: 8080
    *   **Responsibility**: Entry point for all client requests, routing, and cross-cutting concerns (auth validation).

3.  **Auth Service**
    *   **Port**: 8081
    *   **Responsibility**: User registration, login, JWT generation, and validation.
    *   **Entities**: `User`, `AuthRequest`.

4.  **Exchange Service**
    *   **Port**: 8082
    *   **Responsibility**: Managing Exchange Sets and Content Items.
    *   **Entities**: `ExchangeSet`, `ContentItem`, `ContentType`.

5.  **Resource Service**
    *   **Port**: 8083
    *   **Responsibility**: Managing uploaded resources/files.
    *   **Entities**: `Resource`, `ResourceType`.

6.  **Audit Service**
    *   **Port**: 8084
    *   **Responsibility**: Centralized logging of user actions.
    *   **Entities**: `AuditLog`.

## Migration Steps

1.  **Setup Parent Project**: Create a new Maven parent project to manage dependencies (Spring Boot, Spring Cloud).
2.  **Create Service Registry**: Initialize and configure Eureka Server.
3.  **Create API Gateway**: Initialize and configure Spring Cloud Gateway.
4.  **Extract Auth Service**: Move User and Auth logic to a new module.
5.  **Extract Exchange Service**: Move ExchangeSet and Content logic to a new module.
6.  **Extract Resource Service**: Move Resource logic to a new module.
7.  **Extract Audit Service**: Move AuditLog logic to a new module.
8.  **Configure Communication**: Use OpenFeign for inter-service communication (e.g., Exchange Service calling Audit Service).
9.  **Database Separation**: Each service should ideally have its own database (or schema). For simplicity, we might start with separate schemas or a shared DB but logically separated.

## Next Steps

I will begin by creating the `microservices` directory and the parent `pom.xml`.

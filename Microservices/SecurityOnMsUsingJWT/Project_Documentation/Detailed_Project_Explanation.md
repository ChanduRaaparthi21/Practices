# Security on Microservices Using JWT - Project Documentation

## 1. Project Overview
This project demonstrates a secure microservices architecture using **Spring Boot**, **Spring Cloud Gateway**, and **JSON Web Tokens (JWT)**. The core objective is to secure communication between microservices and external clients by implementing a centralized authentication mechanism.

### Key Components:
1.  **Eureka Server**: A service registry where all microservices register themselves.
2.  **API Gateway**: The entry point for all client requests. It acts as a gatekeeper, validating JWT tokens before routing requests to downstream services.
3.  **Auth Service**: Responsible for user registration, authentication, and generating JWT tokens.
4.  **Product Service / User Service**: Example functional microservices that are protected and can only be accessed with a valid token.

---

## 2. Architecture & Flow

### High-Level Flow:
1.  **Registration/Login**: The client sends a request to the `Auth Service` (via the Gateway) to register or log in.
2.  **Token Generation**: Upon successful login, the `Auth Service` generates a signed JWT token and returns it to the client.
3.  **Accessing Resources**: The client wants to access a protected resource (e.g., `Product Service`). They send a request to the `API Gateway` with the JWT token in the `Authorization` header.
4.  **Validation**: The `API Gateway` intercepts the request. It extracts the token and validates its signature and expiration using the shared secret key.
5.  **Routing**:
    *   If **Valid**: The Gateway routes the request to the `Product Service`.
    *   If **Invalid**: The Gateway denies the request and returns an error (401/403).

---

## 3. Detailed Component Explanation

### A. Auth Service (`auth-service`)
This service handles the creation and management of user identities and tokens.

#### 1. `AuthConfig.java`
**Purpose**: Configures Spring Security settings.
*   **`securityFilterChain(HttpSecurity http)`**:
    *   Disables CSRF (common for stateless APIs).
    *   `authorizeHttpRequests`: Allows open access to `/auth/**` endpoints (login, register) but requires authentication for any other request.
    *   `sessionManagement`: Sets session policy to `STATELESS` because we are using JWTs, not server-side sessions.
*   **`passwordEncoder()`**: Defines a `BCryptPasswordEncoder` bean to hash passwords before saving them to the database.
*   **`authenticationManager()`**: Exposes the `AuthenticationManager` bean, which is used to programmatically authenticate users in the controller.
*   **`authenticationProvider()`**: Links the `UserDetailsService` and `PasswordEncoder` to the authentication process.

#### 2. `JwtUtilService.java`
**Purpose**: A utility class for handling raw JWT operations.
*   **`SECRET`**: A Base64-encoded secret key used to sign and verify tokens. **Crucial**: This key must be kept secret and consistent across services that need to validate the token.
*   **`generateToken(String userName)`**: Creates a new JWT.
    *   Sets claims (payload data).
    *   Sets subject (username).
    *   Sets issued date and expiration date (30 minutes).
    *   Signs the token using the `SECRET` and `HS256` algorithm.
*   **`validateToken(final String token)`**: Parses the token to verify its signature. If the signature is invalid or the token is expired, this method will throw an exception.
*   **`getSignKey()`**: Decodes the Base64 secret string into a cryptographic `Key` object.

#### 3. `AuthService.java`
**Purpose**: The business logic layer.
*   **`save(UserCredential userCredential)`**:
    *   Takes raw user data.
    *   Encodes the password using `passwordEncoder`.
    *   Saves the user to the database via `UserCredentialRepo`.
*   **`generateToken(String username)`**: Delegates to `JwtUtilService` to create a token.
*   **`validateToken(String token)`**: Delegates to `JwtUtilService` to check validity.

#### 4. `AuthController.java`
**Purpose**: REST Controller exposing endpoints.
*   **`POST /auth/register`**: Accepts user details and saves them.
*   **`POST /auth/token`**:
    *   Accepts `AuthRequest` (username/password).
    *   Uses `authenticationManager.authenticate(...)` to verify credentials against the database.
    *   If successful, returns a generated JWT token.
    *   If failed, throws an exception.
*   **`GET /auth/validate`**: A simple endpoint to check if a token is valid.

---

### B. API Gateway (`api-gateway`)
This is the most critical component for security enforcement.

#### 1. `AuthenticationFilter.java`
**Purpose**: A custom Gateway Filter that runs for every request.
*   **`apply(Config config)`**: The main logic method.
    *   **Check Secured**: Uses `routeValidate.isSecured` to check if the requested path needs protection.
    *   **Header Check**: Verifies if the `Authorization` header is present.
    *   **Extraction**: Removes the "Bearer " prefix to get the raw token.
    *   **Validation**: Calls `jwtUtilService.validateToken(authHeader)`.
        *   If valid, the request chain continues (`chain.filter(exchange)`).
        *   If invalid (exception thrown), it blocks the request with a RuntimeException (resulting in 401/403).

#### 2. `RouteValidate.java`
**Purpose**: Defines which endpoints are open (public) and do not need token validation.
*   **`openApiEndpoints`**: A list of paths like `/auth/register`, `/auth/token`, `/eureka`.
*   **`isSecured`**: A predicate (boolean function) that returns `true` if the request path is **NOT** in the open list.

#### 3. `JwtUtilService.java` (Gateway Version)
**Purpose**: Validates tokens.
*   **Note**: This class is almost identical to the one in `Auth Service`. It **MUST** use the exact same `SECRET` key.
*   **`validateToken(String token)`**: Parses the token. If it parses successfully without error, the token is trusted.

---

## 4. How to Build and Run

### Prerequisites
*   Java 17 or higher
*   Maven

### Steps
1.  **Start Eureka Server**:
    *   Navigate to `eureka-server`.
    *   Run `mvn spring-boot:run`.
    *   Verify it's running at `http://localhost:8761`.

2.  **Start Auth Service**:
    *   Navigate to `auth-service`.
    *   Run `mvn spring-boot:run`.
    *   It will register itself with Eureka.

3.  **Start API Gateway**:
    *   Navigate to `api-gateway`.
    *   Run `mvn spring-boot:run`.
    *   It will register with Eureka and start routing traffic.

4.  **Start Other Services** (e.g., `product-service`):
    *   Run them similarly.

---

## 5. How It Works (User Journey)

### Scenario: A user wants to see products.

1.  **Registration**:
    *   User sends `POST http://localhost:8080/auth/register` (Gateway URL) with JSON body `{"name": "john", "password": "123"}`.
    *   Gateway sees `/auth/register` is in `RouteValidate` open list -> Allows request.
    *   `Auth Service` saves user.

2.  **Login**:
    *   User sends `POST http://localhost:8080/auth/token` with credentials.
    *   Gateway allows request.
    *   `Auth Service` verifies password, generates a JWT (e.g., `eyJhbG...`), and sends it back.

3.  **Access Products**:
    *   User sends `GET http://localhost:8080/products`.
    *   **Without Token**:
        *   Gateway sees `/products` is NOT in open list.
        *   Checks header -> Missing.
        *   **Result**: Error "Missing Authorization Header".
    *   **With Token**:
        *   User sends `GET` with header `Authorization: Bearer eyJhbG...`.
        *   Gateway extracts token.
        *   Gateway calls `jwtUtilService.validateToken()`.
        *   Token signature matches `SECRET`.
        *   Gateway forwards request to `Product Service`.
        *   `Product Service` returns list of products.
        *   User sees products.

---

## 6. Key Takeaways for Building This
1.  **Shared Secret**: Ensure the secret key is strong and identical in both the Auth Service (Generator) and Gateway (Validator).
2.  **Statelessness**: The Gateway does not store session data. It relies entirely on the validity of the token signature.
3.  **Centralized Security**: The individual microservices (like Product Service) don't need to implement complex security logic. They trust that if a request reaches them, the Gateway has already validated it.

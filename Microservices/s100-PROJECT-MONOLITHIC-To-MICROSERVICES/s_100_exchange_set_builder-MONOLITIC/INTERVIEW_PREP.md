# S-100 Exchange Set Builder - Interview Preparation Guide

## 1. Project Summary (The "Elevator Pitch")

**"I developed the S-100 Exchange Set Builder, a full-stack web application designed to automate the creation, management, and digital signing of maritime data packages compliant with the International Hydrographic Organization (IHO) S-100 standards."**

**Key Problem Solved:**
Traditionally, creating these exchange sets is a manual, error-prone process involving complex XML editing and command-line cryptographic tools. My application streamlines this into a user-friendly workflow, reducing creation time from hours to minutes while ensuring strict compliance and security.

**Core Functionality:**
- **Workspace Management:** distinct environments for different data products.
- **Automated XML Generation:** dynamically creates S-100 catalog files.
- **Digital Signing:** implements SHA256withRSA signatures for data integrity.
- **Security:** Role-based access control using JWT.

---

## 2. Architecture & Tech Stack

| Component | Technology | Reasoning |
|-----------|------------|-----------|
| **Backend** | Java 17, Spring Boot 3.x | Robust ecosystem, strong security features, excellent for enterprise-grade applications. |
| **Frontend** | React (or Vanilla JS/HTML) | Responsive UI, component-based architecture for managing complex forms. |
| **Database** | H2 (Dev) / PostgreSQL (Prod) | H2 for easy setup/testing; JPA/Hibernate for ORM abstraction. |
| **Security** | Spring Security, JWT | Stateless authentication suitable for modern REST APIs; scalable. |
| **Cryptography** | Bouncy Castle | Industry-standard library for handling PEM keys and advanced crypto operations. |
| **XML Processing** | Jackson XML | Efficient serialization of Java objects to XML structures. |
| **Deployment** | Docker | Containerization for consistent deployment across environments. |

---

## 3. Key Features & Benefits

### 🌟 Benefits
1.  **Efficiency**: Automates folder structure creation and metadata generation.
2.  **Compliance**: Built-in validation ensures output meets IHO standards.
3.  **Security**: Digital signatures guarantee data hasn't been tampered with.
4.  **Traceability**: Comprehensive audit logs track who created/modified what.

### 🛠️ Use Cases
*   **National Hydrographic Offices**: Distributing weekly updates to nautical charts.
*   **Marine Survey Companies**: Delivering bathymetric survey data to clients.
*   **ECDIS Vendors**: Creating test datasets for navigation system validation.
*   **Port Authorities**: Sharing local depth information with vessels.

---

## 4. Technical Deep Dive (For Technical Rounds)

### 🔐 Digital Signatures (`SignatureUtils.java`)
*   **Algorithm**: We use **SHA256withRSA**.
*   **Implementation**:
    *   I used the **Bouncy Castle** library (`PEMParser`, `JcaPEMKeyConverter`) to read private keys from PEM files. Standard Java `KeyStore` can be tricky with raw PEMs, so Bouncy Castle simplifies this.
    *   The `java.security.Signature` class handles the actual signing process.
    *   **Flow**: Read Private Key -> Init Signature -> Update with File Bytes -> Sign -> Write Signature File.

### 📄 XML Generation (`S100XmlGenerator.java`)
*   **Approach**: Instead of string concatenation (error-prone), I used **Jackson XML**.
*   **Why**: It allows mapping Java POJOs (Plain Old Java Objects) directly to XML elements.
*   **Structure**: I created a `Map<String, Object>` representing the catalog structure and serialized it. This makes the code cleaner and easier to maintain if the schema changes.

### 🛡️ Security Architecture
*   **Authentication**: Implemented a `JwtAuthenticationFilter` that intercepts requests.
*   **Statelessness**: The server doesn't hold session state; the JWT token contains the user's identity and roles.
*   **Password Storage**: BCrypt hashing is used for storing passwords in the database.

---

## 5. Top Interview Questions & Answers

### 🟢 General / Behavioral

**Q: What was the most challenging aspect of this project?**
> **A:** "The biggest challenge was correctly implementing the **digital signature workflow** according to the S-100 specification. Handling different private key formats (PEM) and ensuring the generated signature matched the exact byte content of the files was tricky. I overcame this by integrating the **Bouncy Castle** library to robustly parse keys and writing unit tests to verify signature validity before full integration."

**Q: How does this application handle large files?**
> **A:** "Since hydrographic datasets can be large, we use **streaming** where possible. For file uploads, Spring's `MultipartFile` handles the stream. When signing, we read file bytes. For extremely large files (GBs), I would optimize further by using `BufferedInputStream` to feed the `Signature` object in chunks rather than loading the whole file into memory."

### 🟡 Technical / Java & Spring

**Q: Explain how the JWT authentication works in your app.**
> **A:** "When a user logs in, the server validates credentials and generates a signed JWT containing their username and roles. This token is sent to the client. For subsequent requests, the client sends the token in the `Authorization` header. My `JwtAuthenticationFilter` intercepts the request, validates the token signature, extracts the user details, and sets the `Authentication` object in the `SecurityContextHolder`, allowing Spring Security to enforce authorization checks."

**Q: Why did you use `Component` annotation for your Utils classes?**
> **A:** "I annotated `SignatureUtils` and `S100XmlGenerator` with `@Component` to let Spring manage them as beans. This allows me to **inject** them into services (`ExchangeSetService`) and makes unit testing easier because I can easily **mock** these components to test the service logic without performing actual file I/O or crypto operations."

**Q: How do you handle concurrent users creating exchange sets?**
> **A:** "Each exchange set creation is isolated by a unique ID and workspace path. The database handles transaction isolation. If we needed to scale for high concurrency, I would implement a **job queue** (like RabbitMQ) to process the heavy signing/packaging tasks asynchronously, so the HTTP request doesn't time out."

### 🔴 Advanced / System Design

**Q: If you had to scale this to support 10,000 users, what would you change?**
> **A:**
> 1.  **Database**: Migrate from H2 to a clustered **PostgreSQL**.
> 2.  **Storage**: Move from local file system to **AWS S3** or MinIO for shared storage access.
> 3.  **Async Processing**: Offload the signing and zipping process to background workers (Spring Batch or separate microservices).
> 4.  **Caching**: Use Redis to cache frequent metadata lookups.

---

## 6. Sample "Star" Method Answer (Situation, Task, Action, Result)

**Scenario: "Tell me about a time you had to learn a new domain quickly."**

*   **Situation**: "I started building the S-100 Exchange Set Builder but had zero knowledge of maritime hydrographic standards."
*   **Task**: "I needed to build a compliant validator and XML generator that adhered to the strict IHO S-100 specification."
*   **Action**: "I broke down the 100+ page specification into core requirements. I created small prototypes for just the XML part and just the signing part. I used the 'Fail Fast' approach—building a validator first to test my own outputs."
*   **Result**: "I successfully delivered a working MVP that not only generated valid sets but was used to identify errors in manually created sets from other sources, proving its reliability."

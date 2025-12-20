# S-100 Exchange Set Builder

## Comprehensive Master Documentation

---

## 1. Executive Summary

The **S-100 Exchange Set Builder** is a production-ready web application designed to automate the creation, validation, digital signing, and packaging of **IHO S-100 compliant Exchange Sets**. It replaces a highly manual, error‑prone process with a secure, auditable, and standardized workflow used by hydrographic offices, port authorities, survey companies, and maritime software vendors.

In simple terms, this system works like a **high‑security packaging factory for maritime digital maps**.

---

## 2. Business Problem & Real‑World Context

### The Real‑World Problem

Maritime navigation systems (ECDIS) rely on **trusted digital charts**. These charts must:

* Follow strict **IHO S‑100 XML schemas**
* Be distributed as **Exchange Sets**
* Be **digitally signed** to guarantee authenticity
* Provide a full **audit trail** for legal and safety compliance

Manually building these packages involves:

* Writing and maintaining complex XML
* Handling cryptographic keys
* Managing folder structures
* Risking human error

A single mistake can cause:

* Charts to be rejected by ship systems
* Navigation hazards
* Compliance violations

### The Solution

The S‑100 Exchange Set Builder **automates everything**:

* Folder creation
* XML catalog generation
* Digital signing
* ZIP packaging
* Audit logging

---

## 3. Key Concepts (Plain English Dictionary)

| Concept           | Meaning                                  | Real‑World Analogy     |
| ----------------- | ---------------------------------------- | ---------------------- |
| S‑100             | International hydrographic data standard | Shipping rules         |
| Exchange Set      | A package containing all map data        | Shipping box           |
| Dataset           | Actual map/chart file                    | Product                |
| Support File      | Manuals, metadata                        | Instruction leaflet    |
| CATALOG.XML       | Index of everything inside               | Packing slip           |
| Digital Signature | Proof data wasn’t altered                | Tamper‑proof seal      |
| Private Key       | Used to sign data                        | Official stamp         |
| Certificate       | Used to verify signature                 | ID card                |
| Workspace         | Local storage area                       | Warehouse              |
| Locking           | Freeze final package                     | Handing box to courier |

---

## 4. Target Users & Roles

### 4.1 User Roles

| Role                   | Responsibilities                        |
| ---------------------- | --------------------------------------- |
| **Admin**              | User management, system configuration   |
| **Data Producer**      | Create exchange sets, upload datasets   |
| **Security Officer**   | Manage keys and certificates            |
| **Compliance Auditor** | Review audit logs and verify signatures |

Role‑based access is enforced using **JWT + Spring Security**.

---

## 5. High‑Level Architecture (C4 Model)

### Context

User → Web Application → Spring Boot API → File System + Database

### Containers

* **Frontend**: React 18 + Bootstrap
* **Backend**: Spring Boot 3 (Java 17)
* **Database**: H2 (JPA‑based, replaceable)
* **File System**: Local Workspace directory

---

## 6. Backend Architecture

### Package Structure

```
com.chandu.s_100_exchange_set_builder
├── config        # Security, Swagger, CORS
├── controller    # REST APIs
├── service       # Business logic
├── repository    # JPA repositories
├── model         # Entities & DTOs
├── util          # XML, Crypto, ZIP utilities
└── exception     # Global error handling
```

### Core Services

* WorkspaceService
* ResourceService
* ExchangeSetService
* ContentService
* AuditLogService
* AuthService
* ExportImportService

---

## 7. Frontend Architecture

### React Structure

```
frontend/
├── components   # Reusable UI components
├── pages        # Screens
├── services     # Axios API layer
├── App.js       # Routing & auth
└── index.css    # Theme
```

### Pages

* Login
* Settings (Workspace)
* Resources
* Exchange Sets
* Exchange Set Detail
* Audit Logs

---

## 8. End‑to‑End User Workflow (Real‑World Scenario)

### Scenario: Emergency Navigation Alert

A sunken container is discovered in a shipping lane.

1. Operator logs in (JWT issued)
2. Workspace is initialized
3. Official private key uploaded
4. Exchange set created: `SUNKEN_CONTAINER_ALERT_2025`
5. Dataset uploaded (S‑101 XML)
6. Dataset digitally signed
7. Exchange set signed
8. ZIP package generated
9. Ships download and verify package

**Result:** Ships receive authenticated warning data within minutes.

---

## 9. Digital Signing & Security Model

* SHA‑256 with RSA
* X.509 certificates
* Detached `.sign` files
* CATALOG.SIGN for entire set
* BCrypt password hashing
* JWT token‑based authentication

---

## 10. Audit & Compliance

Every action is logged:

* Who performed it
* When it happened
* What was affected

Audit logs provide **chain‑of‑custody**, critical for:

* Legal investigations
* Accident analysis
* Regulatory audits

---

## 11. API Overview

### Key API Groups

* Authentication: `/api/auth/*`
* Workspace: `/api/workspace`
* Resources: `/api/resources`
* Exchange Sets: `/api/exchange-sets`
* Content: `/api/content`
* Audit Logs: `/api/audit-logs`
* Export/Import: `/api/export-import`

Swagger UI provides interactive testing.

---

## 12. Installation & Deployment

### Local

* Java 17
* Maven
* Node.js

### Docker

* Dockerfile (Backend)
* Dockerfile (Frontend)
* Docker Compose

Supports dev, test, and production environments.

---

## 13. Risks & Mitigation

| Risk               | Mitigation            |
| ------------------ | --------------------- |
| File system access | Permission validation |
| Key exposure       | Secure workspace      |
| Large datasets     | Disk monitoring       |
| Invalid XML        | Schema validation     |

---

## 14. Testing Strategy

* JUnit 5 unit tests
* Mockito service tests
* Integration test structure
* Manual Postman workflows

---

## 15. Interview‑Ready Summary

> “The S‑100 Exchange Set Builder is a secure packaging system for maritime data. It automates IHO‑compliant exchange set creation, digital signing, and distribution using Spring Boot, React, and cryptographic standards. The system replaces manual, error‑prone workflows with a fully auditable, secure, and production‑ready solution that directly improves maritime safety.”

---

## 16. Project Status

* ✅ All Epics Completed
* ✅ Optional Enhancements Implemented
* ✅ Production Ready
* ✅ Fully Documented

**Version:** 1.0.0
**Status:** Complete & Interview‑Ready


----------------------------------------



# 🌍 Real-World Story: How Ships Get Safe, Trusted Digital Maps

## 🧠 The Real Problem (Before This Application)

Imagine this situation:

- A cargo ship is approaching a busy port at night.
- The port was dredged last week, and water depth has changed.
- If the ship uses old or tampered map data, it can run aground — causing loss of life, cargo damage, and serious legal issues.

---

## ❌ Old Manual Process (Painful Reality)

Before this application existed, engineers had to manually:

- Edit complex XML files
- Create folder structures by hand
- Generate digital signatures using command-line tools

Even **one small mistake** could break everything:

- Wrong XML tag ❌
- Missed file ❌
- Expired certificate ❌

### Result:
- Ships reject the data  
- Or worse → accept **incorrect** data  

This process was **dangerous, slow, and error-prone**.

---

## ✅ What the Application Does (In One Line)

**The S-100 Exchange Set Builder** is a secure *“packing and sealing machine”* that prepares trusted digital map packages for ships — **automatically and correctly**.

It follows international rules defined by the **International Hydrographic Organization (IHO)**.

---

## 📦 Simple Real-World Analogy (Amazon + Security)

| Real World        | Your Project                     |
|------------------|----------------------------------|
| Amazon warehouse | S-100 Exchange Set Builder        |
| Products         | Nautical map datasets             |
| Packing slip     | `CATALOG.XML`                     |
| Tamper seal      | Digital Signature                 |
| Sealed box       | Exchange Set ZIP                  |
| Courier trust    | Certificate verification          |

**Your app ensures nothing inside the box is changed after sealing.**

---

## 🚢 Real-World Scenario #1  
### Emergency Sunken Container Alert

### Situation
- A container falls off a ship in a busy sea route
- It becomes an underwater hazard
- Authorities must warn **thousands of ships immediately**

### How the App Is Used
1. Hydrographic officer logs in
2. Uploads obstacle coordinates (map dataset)
3. Clicks **Create Exchange Set**
4. The app:
   - Builds folder structure
   - Updates `CATALOG.XML`
   - Digitally signs data
   - Locks and packages everything
5. Ships download the ZIP
6. Navigation screen shows 🚨 **RED WARNING ICON**

### 🎯 Impact
- Ships trust the data
- No manual XML editing
- No fake or altered maps
- **Lives and cargo are saved**

---

## 🚢 Real-World Scenario #2  
### Port Dredging Update

### Situation
- A port deepens its channel from **13m → 15m**
- Large oil tankers need verified depth data
- Wrong depth = grounded ship = **millions in losses**

### The App’s Role
**Port Authority:**
- Uploads bathymetry data (S-102)
- Uploads survey report (PDF)
- Signs using official port certificate

**System Ensures:**
- Data and reports match
- No editing after signing
- Legal audit trail is created

### 🎯 Impact
- Tankers enter safely
- Port handles bigger ships
- Legal and safety compliance guaranteed

---

## 🕵️ Real-World Scenario #3  
### Accident Investigation (Audit Case)

### Situation
- A ship runs aground
- Lawyers ask:  
  *“Was the chart data correct and untampered?”*

### How the App Helps
- Auditor opens **Audit Logs**
- Finds:
  - Who created the package
  - Which certificate signed it
  - Exact timestamp

### Result
- Proven chain of custody
- Court-ready evidence

### 🎯 Impact
- Protects organizations legally
- Proves compliance with IHO standards

---

## 🔐 Why Digital Signing Matters (Simple Explanation)

- Digital signature = **Wax seal on a royal letter**
- If the seal is broken → data is rejected
- Ships trust **signatures**, not people

**Your app makes this automatic and safe.**

---

## 🧠 How to Explain This to a Non-Technical Person

> “This app safely packs digital sea maps, seals them with a tamper-proof digital lock, and delivers them to ships so they can navigate without accidents.”

---

## 🎤 Interview-Ready One-Minute Answer

> “I worked on the S-100 Exchange Set Builder, a Spring Boot and React application used in the maritime industry to securely package nautical chart data according to international IHO S-100 standards.  
>  
> The problem we solved was that creating these map packages manually is complex, error-prone, and risky.  
>  
> Our system automates folder creation, XML catalog generation, cryptographic signing using certificates, and produces a locked, audit-ready ZIP file.  
>  
> This ensures ships always receive trusted, untampered navigation data, directly improving maritime safety.”

---

## 💡 Why This Project Is Strong (Interview Perspective)

- ✔ Real-world safety impact  
- ✔ International standards (IHO S-100)  
- ✔ Security and cryptography  
- ✔ Audit and compliance  
- ✔ Enterprise-grade backend design  

---

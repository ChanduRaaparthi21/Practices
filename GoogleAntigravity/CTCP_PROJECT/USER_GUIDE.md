# CTCP / TFConnect – Digital Trade Finance Workflow System 🏦

## 🌟 1. Project Overview & Purpose
**The purpose of this project is to help banks and businesses safely handle international trade payments and problems digitally, instead of using paperwork, emails, and branch visits.**

In short: It helps companies import and export goods smoothly and helps the bank manage and resolve issues faster.

### 💼 One-Line Interview Answer
> "The purpose of this project is to digitally manage trade finance transactions and complaints so that import-export businesses and bank teams can track, authorize, and resolve issues quickly, securely, and transparently."

---

## � 2. Main Agenda (Problem Solving)
The project is built to solve specific challenges in the trade finance ecosystem:

### 1️⃣ Digitization of Operations
*   **Replace Manual Processes:** Move away from paper forms, scattered emails, and physical branch visits.
*   **24/7 Accessibility:** Allow businesses to manage trade finance issues anytime, anywhere.

### 2️⃣ Efficient Complaint & Exception Management
*   **Multi-Module Support:** Handle issues across Letters of Credit (LC), Import/Export bills, Remittances (A1/A2), and Bank Guarantees.
*   **Lifecycle Tracking:** Automated flow from *Raised* → *Reviewed* → *Resolved* → *Closed*.

### 3️⃣ Role-Based Workflow Control
*   **Secure Access:** Strict segregation of duties for Corporate Users, Trade Ops, and Authorizers.
*   **Fraud Prevention:** Use of RBAC to prevent unauthorized actions.

### 4️⃣ Faster Turnaround (TAT) & Efficiency
*   **Automated Routing:** Tickets are automatically sent to the right department.
*   **Operational Speed:** Reduce bottlenecks in approvals and escalations.

### 5️⃣ Real-Time Visibility & Transparency
*   **Live Dashboards:** See the health of all trade transactions at a glance.
*   **Audit Trails:** Every single action is logged for regulatory compliance.

### 6️⃣ Compliance & Audit Readiness
*   **RegTech Ready:** Built-in compliance with banking and international trade regulations.
*   **Forensic History:** Maintain deep transaction history for internal and external auditors.

---

## �🌍 3. Understanding Trade Finance
### Simple Definition
Trade Finance represents the financial instruments used by companies to facilitate international trade. It acts as a **bridge of trust** between an importer (buyer) and an exporter (seller) by using banks to mitigate risks.

### The "Trust" Example
Imagine an Indian company buys goods from a foreign supplier:
1. The supplier wants a guarantee of payment.
2. The buyer wants a guarantee of goods.
3. **The bank acts as a trusted middleman.**
This support provided by the bank is called **Trade Finance**.

### Why is it needed?
*   **Trust Gap:** Buyers and sellers don’t trust each other fully.
*   **Complexity:** Different countries, currencies, laws, and timelines.
*   **Risk Mitigation:** Reduces payment risk, fraud, delays, and legal disputes.

---

## 🛠 3. Common Trade Finance Instruments
These are the core modules supported in the TFConnect system:

1.  **Letter of Credit (LC)**: The bank promises the seller: *"If you ship goods correctly, I will pay you."* Protects both parties.
2.  **Remittances (A1 / A2)**: Standard cross-border payments for imports or services.
3.  **Bill Discounting**: The seller gets money early by selling their invoices to the bank at a discount.
4.  **Bank Guarantees**: Bank guarantees payment if a customer fails to fulfill a contract.

---

## 🚀 4. Problem vs. Solution
| Before (Traditional) ❌ | After (TFConnect) ✅ |
| :--- | :--- |
| Manual forms & emails | Digital requests |
| Physical branch visits | Online access 24×7 |
| Slow, manual approvals | Automated role-based workflows |
| No real-time tracking | Real-time status dashboards |
| High manual error rate | Standardized templates & validation |
| Delayed resolution | Faster turnaround time (TAT) |

---

## 👥 5. User Roles & Workflow
The system follows a strict **Maker-Checker-Authorizer (MCA)** model to ensure zero-fraud and high compliance.

| Role | Responsibility |
| :--- | :--- |
| **Corporate Maker** | Initiates/Drafts the Trade Request or raises a complaint. |
| **Corporate Checker** | Verifies the request for accuracy and compliance. |
| **Corporate Authorizer** | Final approval to transmit the request to the bank. |
| **Bank Admin** | Manages organization onboarding and credit limits. |

---

## 🔧 6. How to Use (Technical Guide)

### Step 1: Authentication
- **Endpoint**: `POST /api/v1/auth/login`
- **Maker**: `maker` / `password`
- **Checker**: `checker` / `password`
- **Authorizer**: `authorizer` / `password`

### Step 2: The Workflow
1. **Initiate**: Maker calls `/api/v1/trade/initiate` (Status: `SUBMITTED`).
2. **Verify**: Checker calls `/api/v1/trade/{id}/check` (Status: `PENDING_APPROVAL`).
3. **Authorize**: Authorizer calls `/api/v1/trade/{id}/authorize` (Status: `APPROVED`).

### Step 3: API Documentation
Access the interactive **Swagger UI** for testing:
- **URL**: `http://localhost:8080/swagger-ui.html`

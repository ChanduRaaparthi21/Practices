# 📘 TFConnect: End-to-End User & Operation Guide

Welcome to the **Digital Trade Finance (TFConnect)** portal. This guide provides a step-by-step walkthrough of how to use the application, from initial setup to final transaction authorization.

---

## 🏗️ 1. Setup & Environment
Before using the application, ensure the following are configured:
*   **Database**: MySQL should have a database named `ctcp`.
*   **Configuration**: Check `src/main/resources/application-dev.yml` for your database credentials.
*   **Run**: Start the application using your IDE or via command line: `mvn spring-boot:run`.
*   **Web Portal**: Access the frontend at [http://localhost:8080/portal.html](http://localhost:8080/portal.html).

---

## 🆕 1.5. User Registration (Self-Onboarding)
Instead of relying only on default users, you can now register new users via the API or the Portal.

### How to Register via API:
*   **Endpoint**: `POST /api/v1/auth/register`
*   **Payload**:
    ```json
    {
      "username": "new_maker",
      "password": "secure_password",
      "email": "new_maker@corporate.com",
      "organizationId": 1,
      "roles": ["ROLE_CORPORATE_MAKER"]
    }
    ```
*   **Roles Available**: `ROLE_CORPORATE_MAKER`, `ROLE_CORPORATE_CHECKER`, `ROLE_CORPORATE_AUTHORIZER`, `ROLE_BANK_ADMIN`.

---

## 🌐 2. The Premium Web Portal
We have built a premium, glassmorphism-style web portal to manage your trades without using Postman.

### Features of the Portal:
*   **Seamless Login/Register**: Switch between entering credentials and creating new accounts within the same window.
*   **Role-Based Dashboards**: 
    *   **Makers** see an "Initiate New Trade" button and a list of their submissions.
    *   **Checkers** see a "Verify" button for all `SUBMITTED` trades.
    *   **Authorizers** see an "Approve" button for all `PENDING_APPROVAL` trades.
*   **Live Status Tracking**: Watch your transaction move from `SUBMITTED` → `PENDING_APPROVAL` → `APPROVED` in real-time.
*   **Remarks Capture**: Beautiful pop-up modals to enter your decision remarks during verification or approval.

### Accessing the Portal:
1.  Open your browser to `http://localhost:8080`.
2.  Click **"Go to Portal"** in the top navigation bar.
3.  Login or Register to start your trade journey!

## 🔑 2. Authentication (Getting Access)
The application uses **JWT (JSON Web Tokens)** for security. You must login to get a "Bearer Token" before you can call any Trade Finance APIs.

### Default Test Users:
| Role | Username | Password | Purpose |
| :--- | :--- | :--- | :--- |
| **Maker** | `maker` | `password` | Initiates new trade requests |
| **Checker** | `checker` | `password` | Verifies and validates requests |
| **Authorizer** | `authorizer` | `password` | Final approval for bank transmission |
| **Admin** | `admin` | `password` | Manages limits and onboarding |

### How to Login:
1. Open **Postman** or **Swagger UI**.
2. Call `POST /api/v1/auth/login`.
3. Provide JSON body:
   ```json
   {
     "username": "maker",
     "password": "password"
   }
   ```
4. **Copy the `token`** from the response. You will need this for the next steps.

---

## 🌊 3. The Digital Workflow (How it Flows)

The system operates on an **Asynchronous Digital Pipeline**. Here is the architectural flow:

1.  **Request Layer**: Corporate user submits data.
2.  **Validation Layer**: System checks if the organization has enough credit limits and valid roles.
3.  **Persistence Layer**: Transaction is saved with a state (`SUBMITTED`).
4.  **Notification Layer**: Checkers and Authorizers are alerted of pending actions.
5.  **Audit Layer**: Every state change is timestamped with the User ID and IP address.

---

## 🌎 4. Real-World Example: "The Smart Electronics Import"

**Scenario:** *Reliance Digital (Buyer in India)* wants to buy $100,000 worth of iPhones from *Apple Inc (Seller in USA)*.

### 1. Initiation (Maker - Finance Executive)
*   **Action**: Rahul (The Maker at Reliance) logs into **TFConnect** using his multi-factor authenticated account.
*   **Preparation**: He gathers the digital copies of the **Purchase Order (PO)** and **Proforma Invoice** provided by Apple Inc.
*   **Execution (Data Entry)**:
    *   **Select Instrument**: Rahul selects **Import Letter of Credit (LC)** from the "New Transaction" menu.
    *   **Beneficiary Details**: He enters Apple Inc's legal address and their receiving bank's **SWIFT Code** (e.g., CHASUS33 for JPMorgan Chase).
    *   **Financial Terms**: He sets the amount to **$100,000 USD** and specifies the **Expiry Date** (e.g., 90 days from now).
    *   **Logistics Details**: He specifies the **Port of Loading** (San Francisco) and **Port of Discharge** (JNPT Mumbai).
    *   **Document Upload**: He scans and uploads the digital PDF of the Proforma Invoice.
*   **Final Action**: He clicks **"Submit to Workflow"**.
*   **System State**: Transaction `TF-8821` is created in the database with status `SUBMITTED`. 
*   **Audit Log**: *"Rahul initiated LC request TF-8821 for $100,000 favoring Apple Inc"*.

### 2. Verification (Checker - Finance Manager)
*   **Action**: Priya (The Checker) receives an alert. She performs a **Triple-Check** of the digital documents uploaded by the Maker:
    *   📄 **Commercial Invoice**: Comparing the $100,000 value with the purchase order.
    *   🚢 **Bill of Lading (BoL)**: Verifying the shipping route from USA to Mumbai Port.
    *   🏥 **Insurance Certificate**: Ensuring the iPhones are protected against damage during sea transit.
    *   🏦 **Bank Account Details**: Cross-checking Apple Inc's swift code and beneficiary account.
*   **Execution**: She finds all documents are authentic and compliant with bank rules, then clicks **Verify**.
*   **System State**: Transaction `TF-8821` moves to `PENDING_APPROVAL`. Audit log: *"Priya verified Invoice, BoL, and Insurance terms"*.

### 3. Final Authorization (Authorizer - CFO)
*   **Action**: Mr. Khanna (The CFO at Reliance) receives a mobile/email notification for a high-value ($100,000) trade transaction pending his final sign-off.
*   **Final Decision Checks**:
    *   📊 **Budget Review**: He checks the quarterly "Electronics Import" budget to ensure this $100k doesn't exceed company limits.
    *   💸 **Cash Flow Analysis**: He verifies that the company will have the liquid cash required to pay the bank when the LC matures (e.g., in 90 days).
    *   🛡️ **Risk Confirmation**: He confirms that Apple Inc is a trusted partner and there are no active trade sanctions.
*   **Execution**: Mr. Khanna logs into his **Secure Authorizer Dashboard**, reviews the "Verification Report" submitted by Priya (the Checker), and clicks **"Authorize & Transmit"**.
*   **System State**: 
    *   Transaction `TF-8821` moves to status `APPROVED`.
    *   Reliance's **Utilized Credit Limit** is automatically updated in the database.
*   **Final Output**: The **TFConnect Gateway** generates a **SWIFT MT700** message (the digital format for LCs) and triggers a notification to Axis Bank's systems.
*   **Audit Log**: *"Mr. Khanna (CFO) provided final authorization for TF-8821. Corporate guarantee active."*

---

## 🌍 5. Real-World Example 2: "The Spice & Tea Import (UK to India)"

**Scenario:** *Royal Teas Ltd (Buyer in UK)* wants to import £50,000 worth of organic Assam tea and spices from *Organic India Exports (Seller in India)*.

### The Role of TFConnect:
Since the seller is in India, **Axis Bank** provides the trade platform (**TFConnect**) to the Indian exporter and the UK buyer's bank to manage the trust and payment bridge.

### Step-by-Step Trade Journey:

#### 1. The Contract (Outside System)
*   The UK buyer and Indian seller agree on price and quality. The UK buyer asks for a **Guarantee** that the food will meet British Food Standard Agency (FSA) rules.

#### 2. Initiation (Maker - Indian Exporter)
*   **Action**: The Maker at *Organic India* logs into **TFConnect**.
*   **TFConnect Role**: Rahul (Maker) creates an **Export Bill Collection** request.
*   **Documents**: He uploads the **Phytosanitary Certificate** (proving the food is pest-free) and the **Health Certificate** required by UK customs.
*   **Audit**: System logs: *"Maker initiated Export Bill for Royal Teas Ltd UK"*.

#### 3. Verification (Checker - Export Manager)
*   **Action**: Priya (Checker) reviews the "Food Grade" certifications.
*   **TFConnect Role**: She ensures the **Certificate of Origin** is attached so the UK buyer can get lower import duties under the Trade Agreement.
*   **Execution**: She verifies the bank details of the UK bank (e.g., HSBC London).
*   **Audit**: *"Checker verified Food Safety & Origin certificates."*

#### 4. Approval & Shipment (Authorizer - Director)
*   **Action**: The Director authorizes the trade on **TFConnect**.
*   **TFConnect Role**: Once authorized, the system generates the digital **Shipping Documents**.
*   **The Payment Bridge**: Axis Bank (via TFConnect) sends a digital message to the UK bank: *"We have the safety docs and bill. Once your buyer pays £50,000, we will release the goods."*

#### 5. Reaching the UK (Final Output)
*   The ship arrives in London. 
*   The UK buyer pays their bank. 
*   **TFConnect** automatically updates the status to **COMPLETED** once the funds hit the Indian exporter's account.

---

## 🚀 6. Does it only work for Ships? (Modes of Transport)

**No!** While "Bill of Lading" is famous for sea trade, **TFConnect** is designed to handle trade via **any mode of transport**. The system automatically adapts based on the "Document of Title" uploaded:

| Transport Mode | Key Document Used in TFConnect | Real-World Use Case |
| :--- | :--- | :--- |
| 🚢 **Sea (Ocean)** | **Bill of Lading (BoL)** | Bulk goods, cars, heavy machinery. |
| ✈️ **Air (Freight)** | **Air Waybill (AWB)** | Electronics, medicines, urgent medical gear. |
| 🚛 **Road (Truck)** | **Lorry Receipt (LR) / CMR** | Cross-border trade (e.g., India to Nepal/Bhutan). |
| 🚂 **Rail (Train)** | **Railway Receipt (RR)** | Coal, minerals, fertilizers across large continents. |

---

## 🌍 7. Real-World Example 3: "Emergency Medical Gear (Germany to India)"

**Scenario:** *Apollo Hospitals (Buyer in India)* needs urgent MRI scanners from *Siemens Healthineers (Seller in Germany)* for a new ICU wing.

### The Problem:
Medical equipment is life-saving and extremely expensive. The seller (Siemens) won't put it on a plane without a payment guarantee, and the hospital (Apollo) won't pay $500,000 until they see the **Air Waybill**.

### Step-by-Step Trade Journey:

#### 1. The High-Speed Initiation (Maker)
*   **Action**: The Finance Team at Apollo Hospital uses **TFConnect** to initiate an **Urgent Letter of Credit**.
*   **Transport Mode**: They select **"AIR FREIGHT"**. 
*   **TFConnect Role**: The system highlights that an **Air Waybill (AWB)** is required as the primary proof of shipment instead of a sea bill.

#### 2. The Expert Verification (Checker)
*   **Action**: The Checker verifies the **Technical Specification Sheet**.
*   **TFConnect Role**: She checks that the equipment has a "Fragile" handling instruction and that the insurance covers "Air Transit Risks."
*   **Audit**: *"Checker verified Air Transit Insurance & AWB requirements."*

#### 3. The Executive Authorization (Authorizer)
*   **Action**: The Hospital Director approves the $500,000 transaction.
*   **The Result**: Within minutes, a digital guarantee is sent to Siemens in Germany.
*   **The Shipment**: Siemens hands over the MRI scanner to **Lufthansa Cargo**. They receive an **Air Waybill (AWB)** and upload it to the portal.

#### 4. Instant Delivery
*   Because air travel is fast (12 hours), **TFConnect** allows for **Express Document Processing**. As soon as the AWB is uploaded, the bank processes it so the hospital can clear the scanners from the airport immediately upon arrival.

---

## � 8. Benefits of Using TFConnect

Why should a corporate chooses **TFConnect** over traditional methods?

1.  **High-Speed Turnaround (TAT)**: Transactions that used to take 5-7 days of courier time are now processed in **minutes**.
2.  **Zero-Paperwork Environment**: All documents are digitally uploaded, verified, and transmitted, saving costs and the environment.
3.  **Real-Time Transparency**: Both the buyer and the bank see exactly where the transaction is (e.g., *"Pending with CFO"*). No more follow-up emails!
4.  **Forensic Audit Trail**: Every action is stamped with a User ID and Timestamp, making the system **100% compliant** for legal and tax audits.
5.  **Role-Based Security**: The **Maker-Checker-Authorizer** flow ensures that no single person can accidentally or intentionally mismanage funds.

---

## 🤝 9. The "Middleman" Advantage: TFConnect in the Center

In international trade, the biggest problem is **Lack of Trust**. TFConnect sits in the middle of the **Buyer (Importer)** and **Seller (Exporter)** to solve this:

### 🛡️ Benefit for the Seller (Exporter):
*   **Payment Assurance**: They get a "Bank-Backed Guarantee" before they even pack the goods.
*   **Faster Cash Flow**: Through modules like "Bill Discounting," they can get their money early from the bank.

### 🛡️ Benefit for the Buyer (Importer):
*   **Control over Funds**: Their money is only released when **Proof of Shipment** (like a Bill of Lading or Air Waybill) is uploaded and verified.
*   **Credit Utilization**: They can use their **Bank Credit Limit** to trade instead of using their own cash immediately.

### 🛡️ Benefit for BOTH (The Bridge):
*   **Standardization**: TFConnect translates complex trade rules into simple digital steps, ensuring everyone is on the same page.
*   **Dispute Prevention**: If there is a disagreement about what was shipped, the digital audit trail acts as the **"Single Source of Truth."**

---

## 💰 10. The Revenue Model: How does the Bank Profit?

If **TFConnect** is so beneficial for users, how does Axis Bank (or the provider) make money? There are 5 main "Profit Silos":

1.  **Commission & Service Fees**: 
    *   The bank charges a percentage (e.g., 0.5% to 2%) for every **Letter of Credit (LC)** or **Bank Guarantee (BG)** issued.
    *   Digital processing via TFConnect reduces the bank's cost of labor, making these fees high-margin profit.

2.  **Interest Spread (Bill Discounting)**: 
    *   If a seller wants their money early, the bank "discounts" the bill. For example, the bank pays the seller $98,000 today and collects $100,000 from the buyer later. 
    *   The **$2,000 difference** is direct interest profit for the bank.

3.  **Foreign Exchange (FX) Margins**:
    *   Most trade finance is international (e.g., USD to INR). The bank provides the currency at a slightly different rate than the market rate.
    *   With high-volume trades ($1M+), even a few paise of difference per dollar generates massive "FX Spread" profit.

4.  **Operational Cost Savings**:
    *   By removing physical branches, paper couriers, and manual data-entry staff from the workflow, the bank saves millions in **Operational Expenditure (OPEX)**. A digital transaction costs the bank 90% less to process than a manual one.

5.  **Float Income**:
    *   The bank holds the buyer's money while the documents are being verified. During this period, the bank can invest that "float" money in short-term markets to earn interest.

---

## 🌍 11. End-to-End Trade Journey: "The English Willow Trade (Australia to India)"

**Scenario:** You want to import 1,000 Professional Grade Cricket Bats from a manufacturer in Sydney, Australia.

### PHASE 1: Finding & Fixing the Deal (Outside the System)
1.  **Search**: You find the seller, *Sydney Willow Co.*, through a B2B platform (like Alibaba, Indiamart, or Global Sources).
2.  **Communication**: You email/WhatsApp the seller to negotiate price. You agree on **$50,000 USD** for 1,000 bats.
3.  **The Proforma Invoice**: The seller sends you a document (Proforma Invoice) stating: *"I will ship 1,000 bats once I receive a Bank Guarantee (Letter of Credit)."*

### PHASE 2: The TFConnect Journey (The Application)

#### 1. Initiation (Your Office - Maker Role)
*   **Conversation**: You tell your Finance Executive (Rahul): *"Initiate an LC for Sydney Willow Co."*
*   **Action**: Rahul logs into **TFConnect**.
*   **Technical Call**: `POST /api/v1/trade/initiate`
    ```json
    {
      "type": "LETTER_OF_CREDIT",
      "amount": 50000.00,
      "currency": "USD",
      "remarks": "Import 1000 Cricket Bats - Sydney Willow"
    }
    ```
*   **System Response**: Status = `SUBMITTED`, ID = `101`.

#### 2. Verification (Internal Audit - Checker Role)
*   **Conversation**: Your Manager (Priya) reviews the deal. She asks: *"Is the Australian bank SWIFT code correct?"*
*   **Action**: Priya verifies the uploaded Proforma Invoice against the data entry.
*   **Technical Call**: `POST /api/v1/trade/101/check?remarks=Supplier bank details verified`
*   **System Response**: Status = `PENDING_APPROVAL`.

#### 3. Authorization (CFO Sign-off - Authorizer Role)
*   **Conversation**: Your CFO reviews the $50,000 cash flow.
*   **Action**: He provides the digital "Green Light."
*   **Technical Call**: `POST /api/v1/trade/101/authorize?remarks=Monthly quota available`
*   **System Response**: Status = `APPROVED`.

### PHASE 3: Shipping & Payment
1.  **Notification**: The bank in Sydney receives the digital guarantee from Axis Bank.
2.  **Shipment**: The seller loads the bats on a **Ship** (Sea Freight).
3.  **The Proof**: The seller uploads the **Bill of Lading** to his bank. 
4.  **Final Step**: Your bank verifies the shipping bill, and you get your bats at the Mumbai port!

---

## 🔄 12. Technical Execution: Full API Sequence
Here is the exact sequence of technical steps for the entire process:

### 🛠️ Step 1: Authentication (Who are you?)
Call `/api/v1/auth/login` to get your JWT Token.
*   **Maker**: `token_maker`
*   **Checker**: `token_checker`
*   **Authorizer**: `token_authorizer`

### 🛠️ Step 2: Create the Trade Record
`POST /api/v1/trade/initiate` (Header: `Authorization: Bearer <token_maker>`)
*   Creates the database entry in `trade_transactions`.
*   Assigns a unique `reference_number` (e.g., `TF-A87B2`).
*   Logs audit entry in `transaction_audit`.

### 🛠️ Step 3: Lifecycle Management
1.  **Checker Approval**: `POST /api/v1/trade/{id}/check` (Header: `Authorization: Bearer <token_checker>`)
    *   Updates `status` to `PENDING_APPROVAL`.
2.  **Authorizer Final Sign**: `POST /api/v1/trade/{id}/authorize` (Header: `Authorization: Bearer <token_authorizer>`)
    *   Updates `status` to `APPROVED`.

### 🛠️ Step 4: Audit & Tracking
`GET /api/v1/trade/{id}`
*   Returns JSON including the full audit history: who initiated, who checked, and who authorized with precise timestamps.

---

## 📊 4. Monitoring & Tracking
You can track the progress and audit history at any time.

1. **Dashboard View**: Visit `http://localhost:8080` to see the visualized project agenda and status.
2. **Transaction Details**: Call `GET /api/v1/trade/{id}` to see the current status and which user (Maker/Checker/Authorizer) handled it.
3. **Audit Trails**: Check the database `transaction_audit` table to see every timestamped action.

---

## 🛠️ 5. Where to use what? (Developer Reference)

| Feature | Where is it? | Use Case |
| :--- | :--- | :--- |
| **Interactive Testing** | `http://localhost:8080/swagger-ui/index.html` | To test APIs without writing code. |
| **Business Logic** | `TradeServiceImpl.java` | To modify how transactions are processed. |
| **Security Rules** | `SecurityConfig.java` | To change who can access which API. |
| **Database Tables** | MySQL `ctcp` DB | To view permanent records of trades and audits. |

---

## 💸 13. Where and When do I pay the money?

This is the most common question for new traders. In **Trade Finance**, you don't pay the seller directly like a normal UPI transfer or Credit Card payment. 

### Does the money go to the seller's bank or TFConnect?
1.  **Initial Stage (Earmarking)**: When you "Authorize" a trade in **TFConnect**, the money is not sent yet. Instead, the bank **"Blocks"** or **"Earmarks"** that amount ($50,000) from your Corporate Account or Credit Limit. This ensures you have the money to pay later.
2.  **The Middle Stage (The Documents)**: The seller ships the bats. They take the shipping papers (Bill of Lading) to their bank in Australia.
3.  **The Settlement (The Actual Pay)**: The Australian bank sends those papers to **Axis Bank** via the digital network. 
4.  **The Trigger**: Once Axis Bank confirms the papers match the LC rules you set in TFConnect, they **automatically deduct** the $50,000 from your blocked funds and send it to the Australian bank.

### Summary:
*   **Where do I put the money?** In your own **Axis Bank Corporate Account**.
*   **Wait, so I don't send it myself?** No. The bank handles the transmission. This is the "Safety" part of Trade Finance—you only pay when the bank sees proof that the goods are on their way to you!

---

## �️ 15. API Reference & JSON Payloads

This section provides the exact JSON payloads and endpoints to use in **Postman** or **Swagger**.

### 1. The Maker (Initation)
*   **Role**: `ROLE_CORPORATE_MAKER`
*   **Endpoint**: `POST /api/v1/trade/initiate`
*   **Request JSON**:
    ```json
    {
      "type": "LETTER_OF_CREDIT",
      "amount": 50000.00,
      "currency": "USD",
      "remarks": "Importing 1000 Cricket Bats from Sydney"
    }
    ```
*   **Response JSON (Success)**:
    ```json
    {
      "id": 1,
      "referenceNumber": "TF-B92D1F74",
      "status": "SUBMITTED",
      "amount": 50000.0,
      "currency": "USD",
      "maker": "maker_user",
      "createdAt": "2024-05-19T10:00:00"
    }
    ```

### 2. The Checker (Verification)
*   **Role**: `ROLE_CORPORATE_CHECKER`
*   **Step A: Get Pending Actions**: `GET /api/v1/trade/pending`
*   **Step B: Verify Transaction**: `POST /api/v1/trade/{id}/check?remarks=Docs Verified`
*   **Response JSON (Success)**:
    ```json
    {
      "id": 1,
      "status": "PENDING_APPROVAL",
      "checker": "checker_user",
      "remarks": "Docs Verified"
    }
    ```

### 3. The Authorizer (Final Approval)
*   **Role**: `ROLE_CORPORATE_AUTHORIZER`
*   **Step A: Get Pending Actions**: `GET /api/v1/trade/pending`
*   **Step B: Authorize Transaction**: `POST /api/v1/trade/{id}/authorize?remarks=Final Sign-off`
*   **Response JSON (Success)**:
    ```json
    {
      "id": 1,
      "status": "APPROVED",
      "authorizer": "cfo_user",
      "remarks": "Final Sign-off"
    }
    ```

### 4. Rejection (Checker or Authorizer)
*   **Endpoint**: `POST /api/v1/trade/{id}/reject?remarks=Missing Invoice`
*   **Response JSON (Success)**:
    ```json
    {
      "id": 1,
      "status": "REJECTED",
      "remarks": "Missing Invoice"
    }
    ```

---

## 👤 17. User Journey by Role (Step-by-Step)

Here is exactly what each user does from the moment they open the application.

### 🔵 The Maker Journey (Initiator)
**Goal**: To create a new trade finance request for a supplier.

1.  **Login**: Call `POST /api/v1/auth/login` with `username: maker` and `password: password`.
2.  **Get Token**: Copy the `token` from the response and set it as a **Bearer Token** in your headers.
3.  **Draft Request**: Decide on the trade details (e.g., $50k LETTER_OF_CREDIT for Australian Bats).
4.  **Action**: Call `POST /api/v1/trade/initiate`.
    *   *System saves the record and assigns a Reference Number.*
5.  **Confirmation**: Check the response to ensure status is `SUBMITTED`. You can now tell the Checker to verify it.

### 🟢 The Checker Journey (Verifier)
**Goal**: To ensure the Maker hasn't made mistakes and the documents are valid.

1.  **Login**: Call `POST /api/v1/auth/login` with `username: checker` and `password: password`.
2.  **Get Token**: Use the Checker's token in your headers.
3.  **Find Work**: Call `GET /api/v1/trade/pending`. This shows all items in `SUBMITTED` status.
4.  **Review**: Look at ID `1` (or whichever you created).
5.  **Action**: Call `POST /api/v1/trade/1/check?remarks=Valid Invoice & Bank Details`.
    *   *System moves status to PENDING_APPROVAL.*
6.  **Closure**: The transaction is now in the Authorizer’s queue.

### 🟠 The Authorizer Journey (Final Approver)
**Goal**: To provide the final corporate guarantee and release funds/credit.

1.  **Login**: Call `POST /api/v1/auth/login` with `username: authorizer` and `password: password`.
2.  **Get Token**: Use the Authorizer's token in your headers.
3.  **Find Work**: Call `GET /api/v1/trade/pending`. This shows all items in `PENDING_APPROVAL` status (items already verified by the Checker).
4.  **Strategic Review**: Check the high-value amount against the corporate budget.
5.  **Action**: Call `POST /api/v1/trade/1/authorize?remarks=CFO Approved for Production`.
    *   *System moves status to APPROVED.*
6.  **Final Result**: The system officially notifies the bank, and the trade becomes legally active.

---

## 🧪 19. Dummy Test Data Library (Copy & Paste)

Use these payloads to test different scenarios in **Postman** or **Swagger**.

### Scenario A: Import of Electronics (Letter of Credit)
*   **Purpose**: Testing high-value secure trade.
*   **JSON**:
    ```json
    {
      "type": "LETTER_OF_CREDIT",
      "amount": 250000.00,
      "currency": "USD",
      "remarks": "Import of 5000 high-performance servers from Singapore"
    }
    ```

### Scenario B: Payment for Consultany (Remittance A2)
*   **Purpose**: Testing service-based international payments.
*   **JSON**:
    ```json
    {
      "type": "REMITTANCE_A1",
      "amount": 15000.00,
      "currency": "EUR",
      "remarks": "Monthly IT consultancy fees for German branch"
    }
    ```

### Scenario C: Raw Material Shipment (Import Bill)
*   **Purpose**: Testing bulk raw material procurement.
*   **JSON**:
    ```json
    {
      "type": "IMPORT_BILL",
      "amount": 85000.00,
      "currency": "GBP",
      "remarks": "Procurement of 20 tons of Raw Steel from UK"
    }
    ```

### Scenario D: Construction Project (Bank Guarantee)
*   **Purpose**: Testing project-based guarantees.
*   **JSON**:
    ```json
    {
      "type": "BANK_GUARANTEE",
      "amount": 1200000.00,
      "currency": "INR",
      "remarks": "Performance Guarantee for Highway Construction Project"
    }
    ```

---

### 🟢 Checker Dummy Data (Verification Remarks)
Once the Maker has submitted, use these remarks for your `POST /api/v1/trade/{id}/check?remarks=...` call:

| Scenario | Remark String (to paste in URL) | Effect |
| :--- | :--- | :--- |
| **Standard Approval** | `Documents verified. Swift code CHASUS33 matches supplier.` | Moves to Pending Approval |
| **Minor Correction** | `Verified. Proforma Invoice dated May 19th attached.` | Moves to Pending Approval |
| **High Value Check** | `Foreign Exchange rates checked. Margin applied as per bank rules.` | Moves to Pending Approval |

### 🟠 Authorizer Dummy Data (Approval Remarks)
Once verified by the Checker, use these for your `POST /api/v1/trade/{id}/authorize?remarks=...` call:

| Scenario | Remark String (to paste in URL) | Effect |
| :--- | :--- | :--- |
| **Policy Match** | `Aligned with Corporate Trade Policy 2024. Execute payment.` | Moves to APPROVED |
| **Budget Match** | `Q2 Electronics Import Budget confirmed. Proceed.` | Moves to APPROVED |
| **Urgent Request** | `Emergency health supplies. Priority authorization granted.` | Moves to APPROVED |

---

## 🎮 21. The Master Playbook - Exact API Execution Order

Follow these steps in order to complete a full trade transaction.

### 🔵 PHASE 1: THE MAKER (Initiation)

**Step 1: Login to get Your Token**
*   **API**: `POST http://localhost:8080/api/v1/auth/login`
*   **Body (JSON)**:
    ```json
    {
      "username": "maker",
      "password": "password"
    }
    ```
*   **Action**: Copy the `token` from the response.

**Step 2: Create the Trade**
*   **API**: `POST http://localhost:8080/api/v1/trade/initiate`
*   **Header**: `Authorization: Bearer {{PASTE_MAKER_TOKEN_HERE}}`
*   **Body (JSON)**:
    ```json
    {
      "type": "LETTER_OF_CREDIT",
      "amount": 50000.00,
      "currency": "USD",
      "remarks": "Importing Cricket Bats from Australia"
    }
    ```
*   **Action**: Copy the `id` from the response (e.g., `1`).

---

### 🟢 PHASE 2: THE CHECKER (Verification)

**Step 3: Login to get Checker Token**
*   **API**: `POST http://localhost:8080/api/v1/auth/login`
*   **Body (JSON)**:
    ```json
    {
      "username": "checker",
      "password": "password"
    }
    ```
*   **Action**: Copy the new `token`.

**Step 4: Verify the Trade**
*   **API**: `POST http://localhost:8080/api/v1/trade/{{ID}}/check?remarks=Verified`
*   **Header**: `Authorization: Bearer {{PASTE_CHECKER_TOKEN_HERE}}`
*   **Note**: Replace `{{ID}}` with the ID from Step 2.
*   **Action**: Response should show status `PENDING_APPROVAL`.

---

### 🟠 PHASE 3: THE AUTHORIZER (Final Approval)

**Step 5: Login to get Authorizer Token**
*   **API**: `POST http://localhost:8080/api/v1/auth/login`
*   **Body (JSON)**:
    ```json
    {
      "username": "authorizer",
      "password": "password"
    }
    ```
*   **Action**: Copy the new `token`.

**Step 6: Final Authorization**
*   **API**: `POST http://localhost:8080/api/v1/trade/{{ID}}/authorize?remarks=Approved`
*   **Header**: `Authorization: Bearer {{PASTE_AUTHORIZER_TOKEN_HERE}}`
*   **Action**: Response should show status `APPROVED`. Trade is now complete.

---

## 🔄 22. Troubleshooting
*   **403 Forbidden**: Your token has expired or you are using the wrong role for the action.
*   **401 Unauthorized**: You forgot to include the Bearer token in the header.
*   **Empty Pending List**: Ensure you are logged in with the correct `Checker` or `Authorizer` role.

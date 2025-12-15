# S-100 Exchange Set Builder - User Guide

## 📖 What is this Application?

The **S-100 Exchange Set Builder** is a production-ready web application designed to help maritime organizations create, manage, sign, and package **S-100 Exchange Sets** according to the International Hydrographic Organization (IHO) S-100 standards.

### What are S-100 Exchange Sets?

S-100 is the IHO Universal Hydrographic Data Model - a framework for digital hydrographic data. Exchange Sets are standardized packages that contain:
- **Geographic datasets** (e.g., nautical charts, bathymetry data)
- **Support files** (documentation, metadata)
- **Catalogues** (XML-based discovery metadata)
- **Digital signatures** (for data integrity and authenticity)

### Why Use This Application?

✅ **Simplify S-100 Compliance** - Automates the complex process of creating standards-compliant exchange sets  
✅ **Ensure Data Integrity** - Digital signing capabilities for authentication  
✅ **Streamline Workflows** - Manage multiple exchange sets from a single interface  
✅ **Track Changes** - Built-in audit logging for all operations  
✅ **Secure Access** - JWT-based authentication and authorization  

---

## 🚀 Getting Started

### Step 1: Start the Application

#### Option A: Local Development
```bash
# Start Backend (Terminal 1)
cd s_100_exchange_set_builder
mvn spring-boot:run

# Start Frontend (Terminal 2)
cd frontend
npm start
```

#### Option B: Docker
```bash
docker-compose up --build
```

### Step 2: Access the Application

- **Frontend**: http://localhost:3000 (or http://localhost if using Docker)
- **Backend API**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html

### Step 3: Login

**Default Credentials:**
- Username: `admin`
- Password: `admin123`

> **Note**: In production, you should create your own users via the registration endpoint.

---

## 📋 How to Use the Application

### 1️⃣ Configure Workspace

**Purpose**: Set up the directory where all exchange sets and resources will be stored.

**Steps:**
1. Navigate to **Settings** page
2. Enter the workspace directory path (e.g., `C:\workspace\s100`)
3. Click **Set Workspace**

**What Happens:**
- The application creates the workspace directory if it doesn't exist
- Two subdirectories are created automatically:
  - `exchange_sets/` - for all exchange set data
  - `resources/` - for signing keys and certificates

---

### 2️⃣ Add Resources (Keys & Certificates)

**Purpose**: Upload signing keys and certificates needed for digital signatures.

**Steps:**
1. Navigate to **Resources** page
2. Click **+ Add Resource**
3. Select resource type:
   - **Private Key** - for signing operations
   - **Certificate** - for verification
   - **Public Key** - for encryption
4. Choose the file from your computer
5. Click **Upload**

**Supported Formats:**
- Private Keys: `.key`, `.pem`
- Certificates: `.crt`, `.cer`, `.pem`
- Public Keys: `.pub`, `.pem`

**Features:**
- Automatic certificate validation
- Expiry date checking
- Duplicate prevention

---

### 3️⃣ Create an Exchange Set

**Purpose**: Create a new S-100 Exchange Set container.

**Steps:**
1. Navigate to **Exchange Sets** page
2. Click **+ Create Exchange Set**
3. Enter a unique name (e.g., `MyNauticalChart_2024`)
4. Click **Create**

**What Happens:**
- A new directory is created in `workspace/exchange_sets/[name]/`
- A `CATALOG.XML` file is generated automatically
- The exchange set appears in the list with status "🔓 Unlocked"

---

### 4️⃣ Add Content to Exchange Set

**Purpose**: Add datasets, support files, or catalogues to your exchange set.

**Steps:**
1. Click on an exchange set name to open the detail view
2. Click **+ Add Content**
3. Select content type:
   - **Dataset** - Main geographic data files
   - **Support File** - Documentation, metadata, etc.
   - **Catalogue** - Additional catalogue files
4. Choose the file
5. Click **Add**

**What Happens:**
- File is copied to the exchange set directory
- `CATALOG.XML` is automatically updated with the new content
- Content appears in the table with status "Not Signed"

**Repeat** this step for all files you want to include.

---

### 5️⃣ Sign Content (Optional)

**Purpose**: Digitally sign individual content items for integrity verification.

**Steps:**
1. In the Exchange Set detail view, find the content item
2. Click **Sign** button
3. Select a private key from the dropdown
4. Click **Sign**

**What Happens:**
- A `.sign` file is created for the content
- Content status changes to "✓ Signed"
- Signature is recorded in the audit log

---

### 6️⃣ Sign the Exchange Set

**Purpose**: Sign the entire exchange set (signs the CATALOG.XML).

**Steps:**
1. Go back to **Exchange Sets** list
2. Click **Sign** on the exchange set
3. Select a private key
4. Click **Sign**

**What Happens:**
- `CATALOG.SIGN` file is created
- Exchange set is cryptographically signed
- Ensures the catalogue hasn't been tampered with

---

### 7️⃣ Package the Exchange Set

**Purpose**: Create a distributable ZIP file of the exchange set.

**Steps:**
1. In **Exchange Sets** list, click **Package**
2. The ZIP file downloads automatically

**What's Included:**
- All content files (datasets, support files, catalogues)
- `CATALOG.XML`
- All `.sign` files
- Complete directory structure

**Use Case:** Share this ZIP with other organizations or systems.

---

### 8️⃣ Lock/Unlock Exchange Sets

**Purpose**: Prevent accidental modifications to finalized exchange sets.

**Lock an Exchange Set:**
1. Click **Lock** button
2. Status changes to "🔒 Locked"

**When Locked:**
- ❌ Cannot add/remove content
- ❌ Cannot sign content
- ❌ Cannot package
- ❌ Cannot delete

**Unlock:**
1. Click **Unlock** button
2. All operations become available again

---

### 9️⃣ View Audit Logs

**Purpose**: Track all system activities and changes.

**Steps:**
1. Navigate to **Audit Logs** page
2. View chronological list of all actions

**Information Shown:**
- Timestamp
- Action type (color-coded badges)
- Details of what happened

**Actions Tracked:**
- Workspace creation
- Resource uploads
- Exchange set creation/deletion
- Content additions/removals
- Signing operations
- Package creation

**Features:**
- Pagination for large logs
- Refresh button
- Newest entries first

---

### 🔟 Export/Import (Advanced)

**Export Exchange Set Metadata:**
1. Use API endpoint: `GET /api/export-import/export/{id}`
2. Downloads JSON file with metadata

**Import Exchange Set:**
1. Use API endpoint: `POST /api/export-import/import`
2. Upload JSON file
3. Metadata is restored

**Use Case:** Backup configurations, migrate between systems.

---

## 🔒 Security Features

### Authentication
- All pages require login (except login page)
- JWT tokens expire after 24 hours
- Logout clears all session data

### Authorization
- Role-based access control
- Protected API endpoints
- Secure password storage (BCrypt)

### Data Security
- Private keys stored securely in workspace
- Digital signatures ensure data integrity
- Audit trail for accountability

---

## 💡 Common Workflows

### Workflow 1: Create and Distribute a Nautical Chart

1. **Setup** → Configure workspace
2. **Prepare** → Upload private key and certificate
3. **Create** → New exchange set "NauticalChart_2024"
4. **Add** → Upload chart dataset files
5. **Add** → Upload metadata support files
6. **Sign** → Sign individual datasets
7. **Sign** → Sign the exchange set
8. **Package** → Download ZIP
9. **Distribute** → Share ZIP with partners

### Workflow 2: Update an Existing Exchange Set

1. **Unlock** → Unlock the exchange set
2. **Add** → Upload new content
3. **Sign** → Sign new content
4. **Sign** → Re-sign exchange set
5. **Lock** → Lock to prevent changes
6. **Package** → Create new distribution package

### Workflow 3: Audit and Compliance

1. **Review** → Check audit logs regularly
2. **Verify** → Ensure all content is signed
3. **Export** → Backup metadata periodically
4. **Monitor** → Track who made what changes

---

## 🛠️ Troubleshooting

### Issue: Cannot set workspace
**Solution:** Ensure the path exists and you have write permissions

### Issue: Certificate validation fails
**Solution:** Check certificate is valid and not expired

### Issue: Cannot sign content
**Solution:** Ensure exchange set is unlocked and you've uploaded a private key

### Issue: Login fails
**Solution:** Check credentials or register a new user

### Issue: Package download fails
**Solution:** Ensure exchange set has content and is properly signed

---

## 📚 API Documentation

For developers integrating with the application:

**Swagger UI:** http://localhost:8080/swagger-ui.html

**Key Endpoints:**
- Authentication: `/api/auth/*`
- Workspace: `/api/workspace`
- Resources: `/api/resources`
- Exchange Sets: `/api/exchange-sets`
- Content: `/api/content`
- Audit Logs: `/api/audit-logs`
- Export/Import: `/api/export-import`

---

## 🎯 Best Practices

1. **Always configure workspace first** before any other operations
2. **Upload certificates and keys** before creating exchange sets
3. **Sign content individually** before signing the entire exchange set
4. **Lock exchange sets** when finalized to prevent accidental changes
5. **Review audit logs** regularly for compliance
6. **Backup metadata** using export functionality
7. **Use meaningful names** for exchange sets (include version, date)
8. **Test signatures** before distribution

---

## 📞 Support

For issues or questions:
- Check the **Swagger API documentation**
- Review **audit logs** for error details
- Consult the **README.md** for technical details
- Check **walkthrough.md** for implementation details

---

**Version**: 1.0.0 with Enhancements  
**Last Updated**: 2025-11-26

# Postman Guide - S-100 Exchange Set Builder API

## 🚀 Getting Started with Postman

### Prerequisites
1. **Start the Backend Application**
   ```bash
   cd s_100_exchange_set_builder
   mvn spring-boot:run
   ```
   Backend will run at: `http://localhost:8080`

2. **Install Postman**
   - Download from: https://www.postman.com/downloads/
   - Or use Postman Web

---

## 📋 Base Configuration

### Base URL
```
http://localhost:8080/api
```

### Environment Variables (Optional)
Create a Postman environment with:
- `base_url`: `http://localhost:8080/api`
- `token`: (will be set after login)

---

## 🔐 Step 1: Authentication

### 1.1 Register a New User (Optional)

**Endpoint**: `POST /auth/register`

**URL**: `http://localhost:8080/api/auth/register`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "username": "testuser",
  "password": "password123",
  "email": "test@example.com"
}
```

**Expected Response** (200 OK):
```json
{
  "message": "User registered successfully",
  "username": "testuser"
}
```

---

### 1.2 Login and Get JWT Token

**Endpoint**: `POST /auth/login`

**URL**: `http://localhost:8080/api/auth/login`

**Headers**:
```
Content-Type: application/json
```

**Body** (raw JSON):
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Expected Response** (200 OK):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "admin"
}
```

**IMPORTANT**: Copy the `token` value - you'll need it for all subsequent requests!

---

### 1.3 Configure Authorization for All Requests

For all endpoints below, add this header:

**Headers**:
```
Authorization: Bearer YOUR_TOKEN_HERE
```

Replace `YOUR_TOKEN_HERE` with the token from login response.

**In Postman**:
1. Go to the request
2. Click "Authorization" tab
3. Select "Bearer Token" from Type dropdown
4. Paste your token in the "Token" field

---

## 📁 Step 2: Workspace Management

### 2.1 Set Workspace

**Endpoint**: `POST /workspace`

**URL**: `http://localhost:8080/api/workspace`

**Headers**:
```
Content-Type: application/json
Authorization: Bearer YOUR_TOKEN
```

**Body** (raw JSON):
```json
{
  "path": "C:\\workspace\\s100"
}
```

**Expected Response** (200 OK):
```json
{
  "id": 1,
  "path": "C:\\workspace\\s100",
  "createdAt": "2025-11-26T11:00:00"
}
```

---

### 2.2 Get Current Workspace

**Endpoint**: `GET /workspace`

**URL**: `http://localhost:8080/api/workspace`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
```json
{
  "id": 1,
  "path": "C:\\workspace\\s100",
  "createdAt": "2025-11-26T11:00:00"
}
```

---

## 🔑 Step 3: Resource Management

### 3.1 Upload a Resource (Private Key/Certificate)

**Endpoint**: `POST /resources`

**URL**: `http://localhost:8080/api/resources`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Body** (form-data):
- Key: `file` | Type: File | Value: [Select your .key or .crt file]
- Key: `type` | Type: Text | Value: `PRIVATE_KEY` or `CERTIFICATE` or `PUBLIC_KEY`

**In Postman**:
1. Select "Body" tab
2. Select "form-data"
3. Add key `file`, change type to "File", click "Select Files"
4. Add key `type`, keep as "Text", enter value `PRIVATE_KEY`

**Expected Response** (200 OK):
```json
{
  "id": 1,
  "fileName": "mykey.key",
  "fileType": "PRIVATE_KEY",
  "filePath": "C:\\workspace\\s100\\resources\\mykey.key",
  "uploadedAt": "2025-11-26T11:05:00"
}
```

---

### 3.2 Get All Resources

**Endpoint**: `GET /resources`

**URL**: `http://localhost:8080/api/resources`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
```json
[
  {
    "id": 1,
    "fileName": "mykey.key",
    "fileType": "PRIVATE_KEY",
    "filePath": "C:\\workspace\\s100\\resources\\mykey.key",
    "uploadedAt": "2025-11-26T11:05:00"
  }
]
```

---

### 3.3 Delete a Resource

**Endpoint**: `DELETE /resources/{id}`

**URL**: `http://localhost:8080/api/resources/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
```
Resource deleted successfully
```

---

## 📦 Step 4: Exchange Set Management

### 4.1 Create Exchange Set

**Endpoint**: `POST /exchange-sets`

**URL**: `http://localhost:8080/api/exchange-sets`

**Headers**:
```
Content-Type: application/json
Authorization: Bearer YOUR_TOKEN
```

**Body** (raw JSON):
```json
{
  "name": "MyNauticalChart_2024"
}
```

**Expected Response** (200 OK):
```json
{
  "id": 1,
  "name": "MyNauticalChart_2024",
  "path": "C:\\workspace\\s100\\exchange_sets\\MyNauticalChart_2024",
  "locked": false,
  "createdAt": "2025-11-26T11:10:00",
  "updatedAt": "2025-11-26T11:10:00"
}
```

---

### 4.2 Get All Exchange Sets

**Endpoint**: `GET /exchange-sets`

**URL**: `http://localhost:8080/api/exchange-sets`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
```json
[
  {
    "id": 1,
    "name": "MyNauticalChart_2024",
    "path": "C:\\workspace\\s100\\exchange_sets\\MyNauticalChart_2024",
    "locked": false,
    "createdAt": "2025-11-26T11:10:00",
    "updatedAt": "2025-11-26T11:10:00"
  }
]
```

---

### 4.3 Get Exchange Set by ID

**Endpoint**: `GET /exchange-sets/{id}`

**URL**: `http://localhost:8080/api/exchange-sets/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

---

### 4.4 Lock Exchange Set

**Endpoint**: `POST /exchange-sets/{id}/lock`

**URL**: `http://localhost:8080/api/exchange-sets/1/lock`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
```
Exchange Set locked successfully
```

---

### 4.5 Unlock Exchange Set

**Endpoint**: `POST /exchange-sets/{id}/unlock`

**URL**: `http://localhost:8080/api/exchange-sets/1/unlock`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

---

### 4.6 Sign Exchange Set

**Endpoint**: `POST /exchange-sets/{id}/sign`

**URL**: `http://localhost:8080/api/exchange-sets/1/sign`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters**:
- `privateKeyId`: `1` (ID of the private key resource)

**In Postman**:
1. Click "Params" tab
2. Add Key: `privateKeyId`, Value: `1`

**Expected Response** (200 OK):
```
Exchange Set signed successfully
```

---

### 4.7 Package Exchange Set

**Endpoint**: `POST /exchange-sets/{id}/package`

**URL**: `http://localhost:8080/api/exchange-sets/1/package`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
- File download (ZIP file)

**In Postman**:
1. Click "Send and Download"
2. File will be saved to your downloads folder

---

### 4.8 Delete Exchange Set

**Endpoint**: `DELETE /exchange-sets/{id}`

**URL**: `http://localhost:8080/api/exchange-sets/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

---

## 📄 Step 5: Content Management

### 5.1 Add Content to Exchange Set

**Endpoint**: `POST /content/{exchangeSetId}`

**URL**: `http://localhost:8080/api/content/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Body** (form-data):
- Key: `file` | Type: File | Value: [Select your dataset file]
- Key: `type` | Type: Text | Value: `DATASET` or `SUPPORT_FILE` or `CATALOGUE`

**Expected Response** (200 OK):
```json
{
  "id": 1,
  "fileName": "chart_data.xml",
  "filePath": "C:\\workspace\\s100\\exchange_sets\\MyNauticalChart_2024\\chart_data.xml",
  "contentType": "DATASET",
  "signed": false,
  "addedAt": "2025-11-26T11:15:00"
}
```

---

### 5.2 Get All Content Items

**Endpoint**: `GET /content/{exchangeSetId}`

**URL**: `http://localhost:8080/api/content/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
```json
[
  {
    "id": 1,
    "fileName": "chart_data.xml",
    "contentType": "DATASET",
    "signed": false,
    "addedAt": "2025-11-26T11:15:00"
  }
]
```

---

### 5.3 Sign Content Item

**Endpoint**: `POST /content/{id}/sign`

**URL**: `http://localhost:8080/api/content/1/sign`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters**:
- `privateKeyId`: `1`

**Expected Response** (200 OK):
```
Content signed successfully
```

---

### 5.4 Remove Content Item

**Endpoint**: `DELETE /content/{id}`

**URL**: `http://localhost:8080/api/content/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

---

## 📊 Step 6: Audit Logs

### 6.1 Get All Audit Logs (Paginated)

**Endpoint**: `GET /audit-logs`

**URL**: `http://localhost:8080/api/audit-logs?page=0&size=50`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Query Parameters**:
- `page`: `0` (page number, starts from 0)
- `size`: `50` (items per page)

**Expected Response** (200 OK):
```json
{
  "content": [
    {
      "id": 1,
      "action": "WORKSPACE_CREATED",
      "details": "Workspace created at: C:\\workspace\\s100",
      "timestamp": "2025-11-26T11:00:00"
    }
  ],
  "totalPages": 1,
  "totalElements": 1,
  "size": 50,
  "number": 0
}
```

---

### 6.2 Get Recent Audit Logs

**Endpoint**: `GET /audit-logs/recent`

**URL**: `http://localhost:8080/api/audit-logs/recent?limit=100`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

---

## 📤 Step 7: Export/Import

### 7.1 Export Exchange Set Metadata

**Endpoint**: `GET /export-import/export/{id}`

**URL**: `http://localhost:8080/api/export-import/export/1`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Expected Response** (200 OK):
- JSON file download

---

### 7.2 Export All Exchange Sets

**Endpoint**: `GET /export-import/export/all`

**URL**: `http://localhost:8080/api/export-import/export/all`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

---

### 7.3 Import Exchange Set

**Endpoint**: `POST /export-import/import`

**URL**: `http://localhost:8080/api/export-import/import`

**Headers**:
```
Authorization: Bearer YOUR_TOKEN
```

**Body** (form-data):
- Key: `file` | Type: File | Value: [Select exported JSON file]

---

## 🔄 Complete Workflow Example

### Workflow: Create and Package an Exchange Set

1. **Login** → Get JWT token
2. **Set Workspace** → `POST /workspace`
3. **Upload Private Key** → `POST /resources` (type: PRIVATE_KEY)
4. **Create Exchange Set** → `POST /exchange-sets`
5. **Add Dataset** → `POST /content/1` (type: DATASET)
6. **Add Support File** → `POST /content/1` (type: SUPPORT_FILE)
7. **Sign Content** → `POST /content/1/sign`
8. **Sign Exchange Set** → `POST /exchange-sets/1/sign`
9. **Package** → `POST /exchange-sets/1/package`
10. **Download ZIP** → Save the file

---

## 🛠️ Postman Collection (JSON)

You can import this collection into Postman:

```json
{
  "info": {
    "name": "S-100 Exchange Set Builder",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Authentication",
      "item": [
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [{"key": "Content-Type", "value": "application/json"}],
            "body": {
              "mode": "raw",
              "raw": "{\"username\":\"admin\",\"password\":\"admin123\"}"
            },
            "url": "http://localhost:8080/api/auth/login"
          }
        }
      ]
    }
  ]
}
```

---

## 💡 Tips

1. **Save Token**: After login, copy the token and use it in all requests
2. **Use Environment Variables**: Create a Postman environment to store `base_url` and `token`
3. **Test Order**: Follow the workflow order (workspace → resources → exchange sets → content)
4. **Check Swagger**: Visit http://localhost:8080/swagger-ui.html for interactive API docs
5. **Error Handling**: Check response status codes and error messages

---

## 🐛 Troubleshooting

### 401 Unauthorized
- Token expired (24 hours) - login again
- Token missing - add Authorization header
- Invalid token - check token format

### 400 Bad Request
- Check request body format
- Verify required fields
- Check file upload format

### 500 Internal Server Error
- Check backend logs
- Verify workspace is configured
- Ensure files exist

---

**Last Updated**: 2025-11-26  
**API Version**: 1.0.0

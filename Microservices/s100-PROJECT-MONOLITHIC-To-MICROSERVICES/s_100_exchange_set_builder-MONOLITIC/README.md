# S-100 Exchange Set Builder

A production-ready web application for creating, managing, signing, and packaging S-100 Exchange Sets according to IHO S-100 standards.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Deployment](#deployment)
- [Project Structure](#project-structure)
- [Risks & Dependencies](#risks--dependencies)
- [License](#license)

## 🎯 Overview

The S-100 Exchange Set Builder enables users to create and manage S-100 Exchange Sets with XML-based discovery metadata for geographic datasets. The application provides a complete workflow for:

- Workspace management
- Resource (keys/certificates) management
- Exchange Set creation and lifecycle management
- Content management (datasets, support files, catalogues)
- Digital signing and packaging

## ✨ Features

### Core Features

#### Epic 1: Workspace
- ✅ Configure workspace directory
- ✅ Automatic subdirectory creation
- ✅ Workspace persistence
- ✅ Validation and error handling

#### Epic 2: Resources
- ✅ Upload and manage signing keys and certificates
- ✅ Certificate validation and expiry checks
- ✅ Secure storage
- ✅ Duplicate prevention

#### Epic 3: Exchange Set
- ✅ Create Exchange Sets with standard folder structure
- ✅ Generate CATALOG.XML
- ✅ Sign entire Exchange Set (CATALOG.SIGN)
- ✅ Package Exchange Set as .zip
- ✅ Lock/Unlock functionality
- ✅ Delete Exchange Sets

#### Epic 4: Content
- ✅ Add/Remove datasets, support files, and catalogues
- ✅ Sign individual content items
- ✅ Automatic CATALOG.XML updates
- ✅ Duplicate detection

### Optional Enhancements

#### Authentication & Security
- ✅ JWT-based authentication
- ✅ User registration and login
- ✅ Role-based access control
- ✅ Password encryption (BCrypt)
- ✅ Protected routes and API endpoints

#### Testing
- ✅ Comprehensive unit tests for all services
- ✅ Integration test structure
- ✅ Mockito-based testing

#### Advanced Features
- ✅ S-100 XML schema validation
- ✅ Audit log viewing with pagination
- ✅ Export/Import exchange set metadata
- ✅ Real-time activity tracking

## 🏗️ Architecture

### C4 Context Diagram
```
User → S-100 Exchange Set Builder Web App → Spring Boot API → Workspace/Database
```

### C4 Container Diagram
- **Frontend**: React 18.2 with Bootstrap 5.3
- **Backend**: Spring Boot 3.5.8 (Java 17)
- **Database**: H2 (embedded)
- **File System**: Local workspace directory

### Backend Layers
```
com.chandu.s_100_exchange_set_builder
├── config/          # Configuration classes
├── controller/      # REST endpoints
├── service/         # Business logic
├── repository/      # Data access
├── model/           # Entities & DTOs
├── util/            # Utilities (XML, Signing, Packaging)
└── exception/       # Global exception handling
```

## 🛠️ Technology Stack

### Backend
- Java 17
- Spring Boot 3.5.8
- Spring Data JPA
- Spring Boot Validation
- H2 Database
- Springdoc OpenAPI (Swagger)
- BouncyCastle (Cryptography)
- Jackson XML
- Apache Commons IO

### Frontend
- React 18.2
- React Router DOM 7.9.6
- Bootstrap 5.3.8
- Axios 1.13.2
- jQuery 3.7.1

### DevOps
- Docker
- Docker Compose
- Maven

## 📦 Prerequisites

- **Java**: JDK 17 or higher
- **Node.js**: v14 or higher
- **Maven**: 3.6 or higher
- **Docker**: (Optional) for containerized deployment

## 🚀 Installation

### Option 1: Local Development

#### Backend Setup
```bash
# Navigate to project root
cd s_100_exchange_set_builder

# Build the project
mvn clean install

# Run the backend
mvn spring-boot:run
```

Backend will be available at: `http://localhost:8080`

#### Frontend Setup
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm start
```

Frontend will be available at: `http://localhost:3000`

### Option 2: Docker Deployment

```bash
# Build and run with Docker Compose
docker-compose up --build

# Access the application
# Frontend: http://localhost
# Backend API: http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
```

## 📖 Usage

### 1. Configure Workspace
1. Navigate to **Settings**
2. Enter workspace directory path (e.g., `C:\workspace\s100`)
3. Click **Set Workspace**

### 2. Add Resources
1. Navigate to **Resources**
2. Click **+ Add Resource**
3. Select resource type (Private Key, Certificate, Public Key)
4. Upload file
5. Click **Upload**

### 3. Create Exchange Set
1. Navigate to **Exchange Sets**
2. Click **+ Create Exchange Set**
3. Enter name
4. Click **Create**

### 4. Add Content
1. Click on an Exchange Set to view details
2. Click **+ Add Content**
3. Select content type (Dataset, Support File, Catalogue)
4. Upload file
5. Click **Add**

### 5. Sign Content
1. In Exchange Set detail view, click **Sign** on a content item
2. Select private key
3. Click **Sign**

### 6. Sign Exchange Set
1. In Exchange Sets list, click **Sign** on an Exchange Set
2. Select private key
3. Click **Sign**

### 7. Package Exchange Set
1. In Exchange Sets list, click **Package**
2. Download will start automatically

### 8. Lock/Unlock
- **Lock**: Prevents modifications, signing, packaging, and deletion
- **Unlock**: Allows modifications

## 📚 API Documentation

### Swagger UI
Access interactive API documentation at: `http://localhost:8080/swagger-ui.html`

### Key Endpoints

#### Workspace
- `POST /api/workspace` - Set workspace directory
- `GET /api/workspace` - Get current workspace

#### Resources
- `GET /api/resources` - List all resources
- `POST /api/resources` - Upload resource (multipart/form-data)
- `DELETE /api/resources/{id}` - Delete resource

#### Exchange Sets
- `GET /api/exchange-sets` - List all exchange sets
- `GET /api/exchange-sets/{id}` - Get exchange set details
- `POST /api/exchange-sets` - Create exchange set
- `DELETE /api/exchange-sets/{id}` - Delete exchange set
- `POST /api/exchange-sets/{id}/lock` - Lock exchange set
- `POST /api/exchange-sets/{id}/unlock` - Unlock exchange set
- `POST /api/exchange-sets/{id}/sign` - Sign exchange set
- `POST /api/exchange-sets/{id}/package` - Package exchange set

#### Content
- `GET /api/content/{exchangeSetId}` - List content items
- `POST /api/content/{exchangeSetId}` - Add content (multipart/form-data)
- `DELETE /api/content/{id}` - Remove content
- `POST /api/content/{id}/sign` - Sign content item

## 🧪 Testing

### Backend Tests
```bash
# Run unit tests
mvn test

# Run with coverage
mvn test jacoco:report
```

### Frontend Tests
```bash
cd frontend
npm test
```

## 🌐 Deployment

### Production Build

#### Backend
```bash
mvn clean package -DskipTests
java -jar target/s_100_exchange_set_builder-0.0.1-SNAPSHOT.jar
```

#### Frontend
```bash
cd frontend
npm run build
# Serve the build folder with a static server
```

### Docker Production
```bash
docker-compose -f docker-compose.yml up -d
```

## 📁 Project Structure

```
s_100_exchange_set_builder/
├── src/
│   ├── main/
│   │   ├── java/com/chandu/s_100_exchange_set_builder/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   ├── util/
│   │   │   └── exception/
│   │   └── resources/
│   │       └── application.yml
│   └── test/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── App.js
│   │   └── index.css
│   ├── public/
│   └── package.json
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## ⚠️ Risks & Dependencies

### Risks
1. **File System Access**: Application requires read/write access to workspace directory
2. **Certificate Validation**: Requires valid certificates for signing operations
3. **Disk Space**: Large exchange sets may require significant disk space
4. **Security**: Private keys stored in workspace must be secured

### Dependencies
- **IHO S-100 Standards**: Compliance with S-100 XML schema
- **BouncyCastle**: For cryptographic operations
- **File System**: Reliable local file system access

### Mitigation
- Implement proper error handling for file operations
- Validate all inputs
- Provide clear error messages
- Regular backups of workspace directory
- Secure workspace directory with appropriate permissions

## 📄 License

This project is developed as per IHO S-100 standards for educational and production use.

## 🤝 Contributing

For contributions, please follow standard Git workflow:
1. Fork the repository
2. Create a feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request

## 📞 Support

For issues and questions, please refer to the project documentation or create an issue in the repository.

---

**Version**: 1.0.0  
**Last Updated**: 2025-11-26

# Implementation Plan - S-100 Exchange Set Builder

# Goal Description
Build a production-ready "S-100 Exchange Set Builder" web application to create, manage, sign, and package S-100 Exchange Sets. The system will consist of a Spring Boot backend and a React frontend, adhering to the specified C4 architecture and strict user stories.

## User Review Required
> [!IMPORTANT]
> - **Workspace Access**: The application requires read/write access to the local file system for the Workspace directory.
> - **Security**: Private keys and certificates will be stored in the configured workspace. Ensure the workspace directory is secure.

## Proposed Changes

### Architecture Strategy (C4)
- **Context**: User interacts with Web App -> API -> Workspace/DB.
- **Container**:
    - **Web App**: React 18, Bootstrap 5, Axios.
    - **API Application**: Spring Boot 3 (Java 17), Spring Data JPA.
    - **Database**: H2 (embedded for portability) or MySQL (configurable). *Plan assumes H2 for standalone ease unless specified otherwise, but code will be JPA agnostic.*
    - **Filesystem**: Local directory for Workspace.

### Backend (Spring Boot)
- **Package Structure**: `com.chandu.s_100_exchange_set_builder`
    - `config`: Swagger, CORS, App Config.
    - `controller`: REST Endpoints.
    - `service`: Business Logic.
    - `repository`: Data Access.
    - `model`: Entities & DTOs.
    - `util`: XML generation, Digital Signatures, File I/O, Zip.
    - `exception`: Global handler.

#### [NEW] Backend Files
- `pom.xml`: Dependencies (Spring Boot Starter Web, Data JPA, Validation, OpenAPI, etc.).
- `src/main/resources/application.yml`: Config.
- `src/main/java/com/chandu/s_100_exchange_set_builder/S100ExchangeSetBuilderApplication.java`: Main class.
- **Controllers**: `WorkspaceController`, `ResourceController`, `ExchangeSetController`, `ContentController`.
- **Services**: `WorkspaceService`, `ResourceService`, `ExchangeSetService`, `ContentService`.
- **Utils**: `S100XmlGenerator`, `SignatureUtils`, `PackageUtils`.

### Frontend (React)
- **Structure**:
    - `src/components`: Reusable UI components (Modals, Tables).
    - `src/pages`: Main views (Settings, Resources, ExchangeSets, Content).
    - `src/services`: API calls (Axios).
    - `src/App.js`: Routing.

#### [NEW] Frontend Files
- `package.json`: Dependencies.
- `src/index.css`: Global styles (Premium aesthetics).
- `src/App.js`: Main router.
- **Pages**: `Settings.jsx`, `Resources.jsx`, `ExchangeSetList.jsx`, `ExchangeSetDetail.jsx`.

### Deployment
- `Dockerfile.backend`: Multi-stage build for Spring Boot.
- `Dockerfile.frontend`: Nginx build for React.
- `docker-compose.yml`: Orchestration.

## Verification Plan

### Automated Tests
- **Backend**: JUnit 5 tests for Services and Controllers.
    - `mvn test`
- **Frontend**: Basic rendering tests.
    - `npm test`

### Manual Verification
- **Workspace**: Verify directory creation and persistence.
- **Resources**: Upload keys/certs and verify storage.
- **Exchange Set**: Create, add content, sign, and package. Verify `CATALOG.XML` content and `.zip` integrity.
- **Negative Scenarios**: Test locking, duplicate names, and permission errors via UI.

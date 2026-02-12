# 🔐 Spring Security JWT Authentication & Authorization

A comprehensive Spring Boot application demonstrating JWT-based authentication and role-based authorization with refresh
token support. This project showcases modern security practices using Spring Security 7 and Spring Boot 4.

## ✨ Features

- 🔑 **JWT Authentication** - Secure token-based authentication
- 🔄 **Refresh Token Support** - Long-lived refresh tokens stored in database
- 👥 **User Registration & Login** - Complete authentication flow
- 🛡️ **Role-Based Access Control (RBAC)** - Fine-grained authorization with roles and privileges
- 🍪 **HTTP-Only Cookies** - Secure token storage in cookies
- 📝 **OpenAPI/Swagger Documentation** - Interactive API documentation
- ✅ **Password Validation** - Strong password requirements
- 🚫 **Custom Error Handling** - Customized access denied and unauthorized handlers
- 🔒 **Spring Security Integration** - Full Spring Security configuration

## 🛠️ Tech Stack

### Core Framework
- **Java 25** (Temurin JDK)
- **Spring Boot 4.0.2**
- **Spring Security 7.0.3**
- **Spring Data JPA**
- **Hibernate ORM 7.2.1.Final**

### Database & Infrastructure
- **PostgreSQL 18** - Production database (via Docker)
- **Docker & Docker Compose** - Containerization
- **HikariCP** - High-performance JDBC connection pool

### Security & Authentication
- **JWT (jjwt 0.13.0)** - JSON Web Token implementation
  - jjwt-api 0.13.0
  - jjwt-impl 0.13.0
  - jjwt-jackson 0.13.0

### API Documentation & Validation
- **SpringDoc OpenAPI 3.0.1** - API documentation & Swagger UI
- **Jakarta Bean Validation 3.0** - Request validation
- **Jakarta Annotations 3.0**

### Development Tools
- **Maven 3.6+** - Build tool & dependency management
- **Lombok** - Boilerplate code reduction
- **Spring Boot DevTools** - Hot reload & development utilities
- **Spring Boot Docker Compose Support** - Automatic container management

### Testing
- **Spring Boot Starter Test** - Testing framework
- **Spring Security Test** - Security testing utilities
- **JUnit 5** - Unit testing framework

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **JDK 25** (Temurin/Eclipse Adoptium recommended) or compatible JDK
- **Maven 3.6+** or use the included Maven wrapper (`./mvnw`)
- **Docker & Docker Compose** (recommended) or **PostgreSQL 18+**
- **Git** (for cloning the repository)

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/security.git
cd security
```

### 2. Database Setup

#### Option A: Using Docker Compose (Recommended)

Start the PostgreSQL 18 container using Docker Compose:

```bash
docker compose up -d
```

This will start a PostgreSQL 18 database with the following configuration:

- **Host:** localhost
- **Port:** 5433
- **Database:** db_security
- **Username:** yu71
- **Password:** 53cret

The database data will be persisted in the `./postgres_data` directory.

To stop the database:

```bash
docker compose down
```

To stop and remove all data:

```bash
docker compose down -v
```

#### Option B: Manual PostgreSQL Installation

If you prefer to install PostgreSQL manually, create a database:

```sql
CREATE
DATABASE db_security;
```

### 3. Configuration

The default `application.yml` is configured to work with the Docker Compose setup:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/db_security
    username: yu71
    password: 53cret
```

If you're using a manual PostgreSQL installation, update the configuration accordingly:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/db_security
    username: postgres
    password: ${POSTGRES_PASSWORD}  # Set this environment variable
```

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Or using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on **http://localhost:8086**

## 📚 API Documentation

Once the application is running, access the Swagger UI for interactive API documentation:

```
http://localhost:8086/swagger-ui.html
```

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint                            | Description                  | Auth Required |
|--------|-------------------------------------|------------------------------|---------------|
| `POST` | `/api/v1/auth/register`             | Register a new user          | ❌             |
| `POST` | `/api/v1/auth/authenticate`         | Login and get JWT tokens     | ❌             |
| `POST` | `/api/v1/auth/refresh-token`        | Refresh access token         | ❌             |
| `POST` | `/api/v1/auth/refresh-token-cookie` | Refresh token via cookie     | ❌             |
| `POST` | `/api/v1/auth/logout`               | Logout and invalidate tokens | ❌             |
| `GET`  | `/api/v1/auth/info`                 | Get authentication info      | ❌             |

### Authorization Endpoints

| Method   | Endpoint                 | Description           | Required Role     | Required Privilege |
|----------|--------------------------|-----------------------|-------------------|--------------------|
| `GET`    | `/api/v1/admin/resource` | Admin read resource   | `ADMIN`           | `READ_PRIVILEGE`   |
| `DELETE` | `/api/v1/admin/resource` | Admin delete resource | `ADMIN`           | `DELETE_PRIVILEGE` |
| `POST`   | `/api/v1/user/resource`  | User create resource  | `ADMIN` or `USER` | `WRITE_PRIVILEGE`  |
| `PUT`    | `/api/v1/user/resource`  | User update resource  | `ADMIN` or `USER` | `UPDATE_PRIVILEGE` |

## 💡 Usage Examples

### Register a New User

```bash
curl -X POST http://localhost:8086/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstname": "John",
    "lastname": "Doe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
  }'
```

### Authenticate (Login)

```bash
curl -X POST http://localhost:8086/api/v1/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
  }'
```

**Response:**

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer"
}
```

Tokens are also set as HTTP-only cookies automatically.

### Access Protected Resource

```bash
curl -X GET http://localhost:8086/api/v1/admin/resource \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

Or if using cookies (set automatically during login):

```bash
curl -X GET http://localhost:8086/api/v1/admin/resource \
  --cookie "jwt-cookie=YOUR_JWT_TOKEN"
```

### Refresh Token

```bash
curl -X POST http://localhost:8086/api/v1/auth/refresh-token \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "YOUR_REFRESH_TOKEN"
  }'
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/fr/mossaab/security/
│   │   ├── config/              # Security configuration classes
│   │   │   ├── SecurityConfiguration.java
│   │   │   ├── ApplicationSecurityConfig.java
│   │   │   ├── JwtAuthenticationFilter.java
│   │   │   ├── CustomAccessDeniedHandler.java
│   │   │   └── Http401UnauthorizedEntryPoint.java
│   │   ├── controller/          # REST controllers
│   │   │   ├── AuthenticationController.java
│   │   │   └── AuthorizationController.java
│   │   ├── entities/            # JPA entities
│   │   │   ├── User.java
│   │   │   └── RefreshToken.java
│   │   ├── enums/               # Enumerations
│   │   │   ├── Role.java
│   │   │   ├── Privilege.java
│   │   │   └── TokenType.java
│   │   ├── service/             # Business logic
│   │   │   ├── AuthenticationService.java
│   │   │   ├── JwtService.java
│   │   │   ├── RefreshTokenService.java
│   │   │   └── impl/            # Service implementations
│   │   ├── repository/          # Data access layer
│   │   │   ├── UserRepository.java
│   │   │   └── RefreshTokenRepository.java
│   │   ├── payload/             # DTOs
│   │   │   ├── request/
│   │   │   └── response/
│   │   ├── validation/          # Custom validators
│   │   ├── exception/           # Custom exceptions
│   │   └── handlers/            # Exception handlers
│   └── resources/
│       └── application.yml      # Application configuration
└── test/                        # Test files
```

## 🔒 Security Features

### Roles and Privileges

The application implements a role-based access control system:

**Roles:**

- `ADMIN` - Full access with all privileges
- `USER` - Limited access with read and write privileges

**Privileges:**

- `READ_PRIVILEGE` - Read access
- `WRITE_PRIVILEGE` - Create access
- `UPDATE_PRIVILEGE` - Update access
- `DELETE_PRIVILEGE` - Delete access

### Token Configuration

- **Access Token Expiration:** 15 minutes (900,000 ms)
- **Refresh Token Expiration:** 15 days (1,296,000,000 ms)
- **Token Storage:** HTTP-only cookies for enhanced security

### Password Requirements

The application enforces strong password validation. Ensure your password meets the requirements defined in the
`StrongPassword` validator.

## ⚙️ Configuration

### JWT Configuration

Configure JWT settings in `application.yml`:

```yaml
application:
  security:
    jwt:
      secret-key: YOUR_SECRET_KEY  # Change this in production!
      expiration: 900000  # 15 minutes
      cookie-name: jwt-cookie
      refresh-token:
        expiration: 1296000000  # 15 days
        cookie-name: refresh-jwt-cookie
```

**⚠️ Security Note:** Always change the `secret-key` in production environments!

## 🧪 Testing

Run the test suite:

```bash
mvn test
```

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check
the [issues page](https://github.com/yourusername/security/issues).

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

⭐ If you find this project helpful, please consider giving it a star!
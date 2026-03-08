# 🌍 Foreign Trading System

A **Spring Boot based Foreign Trading Management System** that simulates international trade between **Importers, Exporters, and Banks**.

The system manages:

* Product trading
* Shipment tracking
* Foreign currency transactions
* Bank fund verification
* Email OTP verification
* Secure authentication using **JWT**
* Exception handling for secure operations

---

# 📌 Project Overview

The **Foreign Trading System** simulates real-world international trade operations where three entities interact:

### 👤 Importer

Requests products from exporters and receives shipments.

### 🏭 Exporter

Provides products and fulfills trade requests.

### 🏦 Bank

Verifies account balances and performs fund transfers between importer and exporter accounts.

---

# 🏗 System Architecture

```
Client (Frontend / Postman)
        │
        ▼
REST Controllers
        │
        ▼
Service Layer
(Business Logic)
        │
        ▼
Repository Layer
(Spring Data JPA)
        │
        ▼
PostgreSQL Database
```

---

# 👥 System Actors

## 1️⃣ Importer

Features:

* Create trade request
* View trade status
* Track shipments

---

## 2️⃣ Exporter

Features:

* Add products
* View trade requests
* Accept trades
* Ship products
* Mark delivery

---

## 3️⃣ Bank

Features:

* Verify importer balance
* Transfer funds
* Handle international transactions

---

# 🔄 Trade Lifecycle

```
Importer creates trade request
        │
        ▼
Trade Status = PENDING
        │
        ▼
Exporter accepts trade
        │
        ▼
Trade Status = ACCEPTED
        │
        ▼
Bank verifies and transfers funds
        │
        ▼
Trade Status = VERIFIED
        │
        ▼
Exporter ships product
        │
        ▼
Trade Status = SHIPPED
        │
        ▼
Exporter delivers product
        │
        ▼
Trade Status = DELIVERED
```

---

# 🗂 Project Structure

```
Foreign-Trading-System
│
├── Controller
│     ├── ImporterController
│     ├── ExporterController
│     └── BankController
│
├── Service
│     ├── ImporterService
│     ├── ExporterService
│     ├── BankService
│     ├── EmailService
│     └── VerifyOtpService
│
├── Repository
│     ├── UserRepository
│     ├── ProductRepo
│     ├── TradeRepository
│     ├── ShipmentRepo
│     ├── BankAccountRepo
│     └── VerifyOtpRepo
│
├── Model
│     ├── Users
│     ├── Importer
│     ├── Exporter
│     ├── Banker
│     ├── Product
│     ├── Trade
│     ├── Shipment
│     ├── Bank
│     ├── BankAccount
│     └── VerificationOTP
│
├── DTO
│     ├── ProductDTO
│     ├── ProductFullDetailsDTO
│     ├── TradeRequestDTO
│     ├── TradeFullDetailsDTO
│     ├── FundTransferRequest
│     └── ForexResponse
│
├── Exceptions
│     ├── BalanceDeclinedException
│     ├── BankNotFoundException
│     ├── ExporterNotFoundException
│     ├── InvalidUserException
│     ├── NoAccountFoundException
│     ├── OTPExpiredException
│     ├── OTPMismatchException
│     └── UnauthorizedUserException
│
└── Config
      ├── JwtUtil
      ├── JwtFilter
      └── SecurityConfig
```

---

# ⚠ Exception Handling

The system includes **custom exception classes** to ensure robust error handling.

### Implemented Exceptions

* `BalanceDeclinedException`
  Thrown when importer balance is insufficient.

* `BankNotFoundException`
  Thrown when a bank cannot be found.

* `ExporterNotFoundException`
  Thrown when exporter details are invalid.

* `InvalidUserException`
  Thrown for invalid user operations.

* `NoAccountFoundException`
  Thrown when bank account does not exist.

* `OTPExpiredException`
  Thrown when OTP has expired.

* `OTPMismatchException`
  Thrown when OTP entered is incorrect.

* `UnauthorizedUserException`
  Thrown when user authentication fails.

---

# 🔐 Security Features

The system uses **Spring Security with JWT authentication**.

Features:

* Password encryption using **BCrypt**
* Stateless authentication using **JWT**
* Secure login system
* Role-based API protection

Example request header:

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 📧 Email OTP Verification

Email verification is implemented using **OTP authentication**.

Features:

* 4-digit OTP generation
* OTP validity: **10 minutes**
* Email delivery using **JavaMailSender**
* HTML email template

---

# 💱 Forex Support

The system supports **currency-based international trading**.

Features:

* Product currency specification
* Bank account currency codes
* Forex response mapping using external APIs

---

# 📦 Shipment System

After fund verification:

1. Exporter ships product.
2. Shipment record is created.
3. Delivery date is automatically assigned.

---

# 🗄 Database

Database used:

**PostgreSQL (PSQL)**

Main tables:

* users
* importer
* exporter
* banker
* product
* trade
* shipment
* bank
* bank_account
* verification_otp

---

# ⚙ Technologies Used

### Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate

### Database

* PostgreSQL

### Other Tools

* JWT Authentication
* JavaMailSender
* Lombok
* Maven

---

# 🚀 API Endpoints

## Importer APIs

```
POST   /api/create/trade
GET    /api/get/trade
```

---

## Exporter APIs

```
POST   /api/exporter/product/add
GET    /api/exporter/product/get
GET    /api/exporter/trade/get
PUT    /api/exporter/accept/{trade_id}
PUT    /api/exporter/ship/{trade_id}
PUT    /api/exporter/deliver/{trade_id}
```

---

## Bank APIs

```
POST   /api/bank/transfer
GET    /api/bank/trade/accepted
```

---

# 🧪 Sample Trade Request

```
POST /api/create/trade
```

Request Body

```json
{
  "productId": 1,
  "quantity": 10,
  "importerId": 2
}
```

---

# 🏦 Sample Fund Transfer

```
POST /api/bank/transfer
```

```json
{
  "importerAccNo": "IMP1001",
  "exporterAccNo": "EXP2001",
  "fund": 5000,
  "tradeId": 10
}
```

---

# 🛠 Setup Instructions

### 1 Clone Repository

```
git clone https://github.com/yourusername/foreign-trading-system.git
```

---

### 2 Configure PostgreSQL

Update **application.properties**

```
spring.datasource.url=jdbc:postgresql://localhost:5432/foreign_trading
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

### 3 Run Application

```
mvn spring-boot:run
```

---

### 4 Test APIs

Use:

* Postman
* Swagger
* Frontend Application

---

# 📈 Future Improvements

Possible enhancements:

* Real-time Forex API integration
* Microservice architecture
* Docker deployment
* Frontend using React or Angular
* Payment gateway integration
* Shipment tracking APIs

---

# 👨‍💻 Author

Developed as a **Spring Boot backend project** simulating international trade operations between importers, exporters, and banks.

---

# 📄 License

This project is for **educational purposes**.

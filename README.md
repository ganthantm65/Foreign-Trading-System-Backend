# Foreign Trading System Backend 🌍

This repository contains the backend implementation for a Foreign Trading System, developed in Java. It provides the necessary services and APIs to manage foreign trading operations.

## 🛡️ Badges

[![Build Status](https://img.shields.io/travis/ganthantm65/Foreign-Trading-System-Backend.svg)](https://travis-ci.org/ganthantm65/Foreign-Trading-System-Backend)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/technologies/javase-downloads.html)

## ✨ Features

*   **User Management:** Secure authentication and authorization for different user roles (e.g., Importer, Exporter, Banker).
*   **Bank Operations:** Functionality to manage bank accounts, process fund transfers, and handle foreign exchange transactions.
*   **Trade Processing:** Endpoints to facilitate the core operations of a foreign trading system.
*   **Security:** Implements JWT-based security for API access.
*   **Exception Handling:** Comprehensive exception handling for various scenarios like invalid users, insufficient funds, and bank not found errors.

## 🚀 Installation

To set up the project locally, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/ganthantm65/Foreign-Trading-System-Backend.git
    ```

2.  **Navigate to the project directory:**
    ```bash
    cd Foreign-Trading-System-Backend
    ```

3.  **Build the project using Maven:**
    ```bash
    ./mvnw clean install
    ```

4.  **Configure Environment Variables:**
    Create a `.env` file in the root of the project and populate it with the necessary database credentials and JWT secrets. An example structure:
    ```env
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/tradingdb
    SPRING_DATASOURCE_USERNAME=dbuser
    SPRING_DATASOURCE_PASSWORD=dbpassword
    JWT_SECRET=your_super_secret_key
    JWT_EXPIRATION_MS=86400000
    ```
    *Note: Ensure you have a PostgreSQL database running and a database named `tradingdb` created with the specified user and password.*

5.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will start on `http://localhost:8080` by default.

## 💡 Usage

This section provides examples of common API endpoints. Ensure the application is running and you have obtained a JWT token for authenticated requests.

### User Authentication 🔑

**Login Endpoint:**
```http
POST /api/auth/login
```
**Request Body (JSON):**
```json
{
  "username": "importer@example.com",
  "password": "password123"
}
```
**Response Body (JSON):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Bank Operations 🏦

**Get Bank Account Balance:**
```http
GET /api/bank/balance/{accountNumber}
```
**Headers:**
```
Authorization: Bearer <your_jwt_token>
```

**Transfer Funds:**
```http
POST /api/bank/transfer
```
**Headers:**
```
Authorization: Bearer <your_jwt_token>
```
**Request Body (JSON):**
```json
{
  "fromAccountNumber": "1234567890",
  "toAccountNumber": "0987654321",
  "amount": 1000.50,
  "currency": "USD"
}
```

### Exporter Operations 📦

**Register New Exporter:**
```http
POST /api/exporter/register
```
**Headers:**
```
Authorization: Bearer <your_jwt_token>
```
**Request Body (JSON):**
```json
{
  "name": "Exporter Corp",
  "contactPerson": "John Doe",
  "email": "exporter@example.com",
  "address": "123 Export St"
}
```

## 🤝 Contributing

We welcome contributions to improve the Foreign Trading System Backend. Please follow these guidelines:

1.  **Fork the repository.**
2.  **Create a new branch** for your feature or bug fix (`git checkout -b feature/your-feature-name`).
3.  **Make your changes** and ensure they are well-tested.
4.  **Commit your changes** with clear and concise commit messages.
5.  **Push to the branch** (`git push origin feature/your-feature-name`).
6.  **Open a Pull Request** with a detailed description of your changes.

Please ensure your code adheres to the existing coding style and conventions.

## 📜 License

This project is not currently under any specified license.

---

<p align="center">
  <a href="https://readmeforge.app?utm_source=badge">
    <img src="https://readmeforge.app/badge.svg" alt="Made with ReadmeForge" height="20">
  </a>
</p>

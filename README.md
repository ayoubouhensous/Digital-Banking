# Digital Banking Application

## Project Overview
The Digital Banking Application is a comprehensive Spring Boot-based backend system designed to manage banking operations. It provides a RESTful API for managing customers, bank accounts (both current and savings accounts), and financial operations (credits and debits).

## Architecture
The application follows a layered architecture pattern with clear separation of concerns:

1. **Presentation Layer**: REST controllers that handle HTTP requests and responses
2. **Business Logic Layer**: Services that implement the business logic
3. **Data Access Layer**: Repositories that interact with the database
4. **Domain Layer**: Entity models that represent the business domain

## Components

### Controllers
The application provides the following REST controllers:

#### BankAccountController
Handles operations related to customers, bank accounts, and transactions:
- Customer management (create, search, list, delete)
- Account management (create, get, update, delete, list)
- Operation history retrieval

Endpoints:
- `POST /customers` - Create a new customer (ADMIN only)
- `GET /customers/search` - Search customers by name (USER only)
- `GET /customers` - List all customers
- `GET /accounts` - List bank accounts with pagination
- `GET /accounts/{id}` - Get a specific bank account
- `POST /accounts` - Create a new bank account
- `PUT /accounts/{id}` - Update a bank account
- `DELETE /accounts/{id}` - Delete a bank account (ADMIN only)
- `GET /accounts/{id}/history` - Get the transaction history of a bank account
- `DELETE /customers/{id}` - Delete a customer (ADMIN only)

#### SecurityController
Handles authentication and security-related operations:
- User authentication
- Profile information retrieval

Endpoints:
- `POST /auth/login` - Authenticate a user and generate a JWT token
- `GET /auth/profile` - Get the current user's profile information

### Services

#### BankAccountService
Defines the business logic interface for banking operations:
- Customer management
- Account management
- Operation management

#### BankAccountServiceImpl
Implements the BankAccountService interface with the following features:
- Customer creation, retrieval, and deletion
- Bank account creation, retrieval, update, and deletion
- Operation history retrieval
- Pagination support for account listing

### Repositories

#### BankAccountRepository
Provides data access for bank accounts with standard CRUD operations.

#### CustomerRepository
Provides data access for customers with standard CRUD operations and custom query methods:
- `findByNameContains(String name)` - Find customers by partial name match

#### OperationRepository
Provides data access for operations with standard CRUD operations and custom query methods:
- `findByBankAccountId(Long accountId)` - Find operations by bank account ID

### Models

#### BankAccount
Base class for bank accounts with the following attributes:
- id: Unique identifier
- createdAt: Account creation date
- balance: Current balance
- status: Account status (CREATED, ACTIVATED, SUSPENDED)
- customer: Associated customer
- operations: List of operations performed on the account

Uses single table inheritance strategy with a discriminator column.

#### CurrentAccount
Extends BankAccount with an additional attribute:
- overDraft: Maximum allowed overdraft amount

#### SavingAccount
Extends BankAccount with an additional attribute:
- interestRate: Interest rate for the savings account

#### Customer
Represents a bank customer with the following attributes:
- id: Unique identifier
- name: Customer name
- email: Customer email
- bankAccounts: List of bank accounts owned by the customer

#### Operation
Represents a financial operation with the following attributes:
- id: Unique identifier
- date: Operation date
- amount: Operation amount
- operationType: Type of operation (CREDIT or DEBIT)
- bankAccount: Associated bank account

### DTOs (Data Transfer Objects)

#### BankAccountDTO
Data transfer object for bank accounts with the following attributes:
- id: Unique identifier
- balance: Current balance
- createdAt: Account creation date
- customer: Associated customer (as CustomerDTO)
- accountType: Type of account ("SAVING" or "CURRENT")
- interestRate: Interest rate for savings accounts (nullable)
- overDraft: Maximum allowed overdraft for current accounts (nullable)
- operations: List of operations (as OperationDTO)

#### CustomerDTO
Data transfer object for customers with the following attributes:
- id: Unique identifier
- name: Customer name
- email: Customer email

#### OperationDTO
Data transfer object for operations with the following attributes:
- id: Unique identifier
- date: Operation date
- amount: Operation amount
- operationType: Type of operation (CREDIT or DEBIT)

### Mappers

#### BankAccountMapper
Provides methods for converting between entity objects and DTOs:
- fromCustomer/fromCustomerDTO: Convert between Customer and CustomerDTO
- fromBankAccount/fromBankAccountDTO: Convert between BankAccount and BankAccountDTO
- fromOperation: Convert from Operation to OperationDTO

### Security

The application uses Spring Security with JWT (JSON Web Token) for authentication and authorization:

#### SecurityConfig
Configures security for the application:
- JWT-based authentication with custom secret key
- In-memory user details service with predefined users
- Stateless session management
- CORS configuration
- Method-level security with @PreAuthorize annotations
- Password encoding with BCrypt

#### Authentication Flow
1. Client sends credentials to `/auth/login`
2. Server authenticates the user and generates a JWT token
3. Client includes the token in the Authorization header for subsequent requests
4. Server validates the token and authorizes the request based on the user's roles

#### Authorization
The application uses role-based access control with two roles:
- USER: Can search customers and perform basic operations
- ADMIN: Can create and delete customers and accounts

## How to Run the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Steps
1. Clone the repository
2. Navigate to the project directory
3. Run `mvn clean install` to build the project
4. Run `mvn spring-boot:run` to start the application
5. The application will be available at `http://localhost:8080`

### API Testing
You can test the API using tools like Postman or curl:

1. Authenticate:
```
POST http://localhost:8080/auth/login
Body: { "username": "admin", "password": "12345" }
```

2. Use the returned token in the Authorization header for subsequent requests:
```
GET http://localhost:8080/customers
Header: Authorization: Bearer <token>
```

## Additional Information

### Database Initialization
The application initializes the database with sample data on startup:
- Three customers: Hassan, Yassine, and AICHA
- Each customer has one current account and one savings account
- Each account has 10 random operations (credits and debits)

### Security Notes
- The application uses an in-memory user details service with two predefined users:
  - Username: ayoub, Password: 12345, Role: USER
  - Username: admin, Password: 12345, Roles: ADMIN, USER
- JWT tokens expire after 600 seconds (10 minutes)
- The application uses a custom secret key for JWT signing and verification

### Technologies Used
- Spring Boot: Framework for building Java applications
- Spring Data JPA: Simplifies data access with JPA
- Spring Security: Provides authentication and authorization
- JWT: JSON Web Token for stateless authentication
- Lombok: Reduces boilerplate code
- BCrypt: Secure password hashing
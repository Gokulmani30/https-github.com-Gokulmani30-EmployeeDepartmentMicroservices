# Employee and Department Microservices

A REST API project built using **Java, Spring Boot, Spring Data JPA, and MySQL**. This project contains two independent microservices: Employee Service and Department Service. Each service manages its own data and database.

## Table of Contents

- [Project Overview](#project-overview)
- [Objectives](#objectives)
- [Technologies Used](#technologies-used)
- [Project Architecture](#project-architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Database Setup](#database-setup)
- [Application Configuration](#application-configuration)
- [How to Run the Project](#how-to-run-the-project)
- [Employee Service APIs](#employee-service-apis)
- [Department Service APIs](#department-service-apis)
- [Sample API Requests](#sample-api-requests)
- [Validation and Exception Handling](#validation-and-exception-handling)
- [Database Persistence Testing](#database-persistence-testing)
- [Postman Testing](#postman-testing)
- [Troubleshooting](#troubleshooting)
- [Future Enhancements](#future-enhancements)
- [Author](#author)

## Project Overview

The project implements two Spring Boot REST microservices:

### 1. Employee Service

Employee Service manages employee information, including:

- Employee ID
- Employee name
- Email address
- Salary
- Department name

It provides REST APIs to create, retrieve, update, and delete employee records.

### 2. Department Service

Department Service manages department information, including:

- Department ID
- Department name
- Department description
- Employee name

It provides REST APIs to create, retrieve, update, and delete department records.

Each microservice runs independently and connects to its own MySQL database.

## Objectives

- Develop RESTful CRUD APIs using Spring Boot.
- Integrate Spring Data JPA with MySQL.
- Store and retrieve real application data.
- Implement request validation.
- Handle application exceptions and errors.
- Test APIs using Postman.
- Verify that database records persist after application restarts.
- Maintain and document the source code using Git and GitHub.

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Application development |
| Spring Web | REST API development |
| Spring Data JPA | Database access |
| Hibernate | JPA implementation and ORM |
| MySQL | Relational database |
| Maven | Dependency management and build |
| Jakarta Bean Validation | Input validation |
| Postman | API testing |
| Git | Version control |
| GitHub | Source code hosting |
| Spring Tool Suite | Development environment |

## Project Architecture

```text
                 Client / Postman
                        |
              +---------+---------+
              |                   |
              v                   v
       Employee Service    Department Service
          Port 8081            Port 8082
              |                   |
              v                   v
       Spring Data JPA     Spring Data JPA
              |                   |
              v                   v
       MySQL Database      MySQL Database
       employeeservice     departmentservice
```

The two services have separate application configurations and databases. In the current implementation, Employee Service stores `departmentName` as a string. This does not automatically create a REST communication link between the two microservices.

## Project Structure

The following is a typical structure. Adjust package and class names to match your implementation.

```text
EmployeeDepartmentMicroservices/
|
|-- employee-service/
|   |-- src/
|   |   |-- main/
|   |   |   |-- java/
|   |   |   |   |-- com/example/employee/
|   |   |   |   |   |-- EmployeeServiceApplication.java
|   |   |   |   |   |-- controller/
|   |   |   |   |   |-- entity/
|   |   |   |   |   |-- repository/
|   |   |   |   |   |-- service/
|   |   |   |   |   |-- exception/
|   |   |   |   |-- resources/
|   |   |   |       |-- application.properties
|   |   |-- test/
|   |-- pom.xml
|
|-- department-service/
|   |-- src/
|   |   |-- main/
|   |   |   |-- java/
|   |   |   |   |-- com/example/department/
|   |   |   |   |   |-- DepartmentServiceApplication.java
|   |   |   |   |   |-- controller/
|   |   |   |   |   |-- entity/
|   |   |   |   |   |-- repository/
|   |   |   |   |   |-- service/
|   |   |   |   |   |-- exception/
|   |   |   |-- resources/
|   |   |       |-- application.properties
|   |-- pom.xml
|
|-- postman/
|   |-- Employee-Department-Collection.json
|
|-- README.md
|-- .gitignore
```

## Prerequisites

Install the following software before running the project:

1. Java Development Kit (JDK).
2. Spring Tool Suite or another Java IDE.
3. Apache Maven, if not using the IDE's bundled Maven.
4. MySQL Server.
5. MySQL Workbench (optional).
6. Postman.
7. Git.

Verify Java and Maven installations:

```bash
java -version
mvn -version
```

Use a Java version supported by your Spring Boot version.

## Database Setup

Start MySQL Server and execute the following SQL statements.

### Create databases

```sql
CREATE DATABASE IF NOT EXISTS employeeservice;
CREATE DATABASE IF NOT EXISTS departmentservice;
```

### Verify databases

```sql
SHOW DATABASES;
```

### Employee database

```sql
USE employeeservice;

SHOW TABLES;

SELECT * FROM employees;
```

### Department database

```sql
USE departmentservice;

SHOW TABLES;

SELECT * FROM departments;
```

Spring Data JPA and Hibernate can create or update tables automatically when configured with `spring.jpa.hibernate.ddl-auto=update`.

The existing application data should be preserved when using this setting in normal development, but it is not a substitute for database backups or a production schema migration strategy.

## Application Configuration

### Employee Service

File: `employee-service/src/main/resources/application.properties`

```properties
spring.application.name=employee-service
server.port=8081

spring.datasource.url=jdbc:mysql://localhost:3306/employeeservice
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:change-me}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Department Service

File: `department-service/src/main/resources/application.properties`

```properties
spring.application.name=department-service
server.port=8082

spring.datasource.url=jdbc:mysql://localhost:3306/departmentservice
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:change-me}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

**Important:** Replace `change-me` with your actual local database password, or configure `DB_USERNAME` and `DB_PASSWORD` as environment variables. Never upload real passwords to GitHub.

Ensure that both projects include the Spring Data JPA and MySQL Connector dependencies in their `pom.xml` files.

## How to Run the Project

### Step 1: Start MySQL

Ensure that MySQL Server is running and that both databases exist.

### Step 2: Configure the services

Check the database URL, username, password, and server port in each `application.properties` file.

### Step 3: Start Employee Service

Open `EmployeeServiceApplication.java` in Spring Tool Suite.

Right-click and select:

**Run As → Spring Boot App**

Employee Service should be available at:

`http://localhost:8081`

### Step 4: Start Department Service

Open `DepartmentServiceApplication.java`.

Right-click and select:

**Run As → Spring Boot App**

Department Service should be available at:

`http://localhost:8082`

### Step 5: Test the APIs

Open Postman and send requests to the endpoints listed below.

## Employee Service APIs

Base URL: `http://localhost:8081`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/employees` | Create an employee |
| GET | `/employees` | Retrieve all employees |
| GET | `/employees/{id}` | Retrieve an employee by ID |
| PUT | `/employees/{id}` | Update an employee |
| DELETE | `/employees/{id}` | Delete an employee |

### Sample employee JSON

```json
{
  "name": "Vasanth",
  "email": "vasu13@abc.com",
  "departmentName": "developer",
  "salary": 40000
}
```

### Example requests

Create employee:

`POST http://localhost:8081/employees`

Retrieve all employees:

`GET http://localhost:8081/employees`

Retrieve employee by ID:

`GET http://localhost:8081/employees/3`

Update employee:

`PUT http://localhost:8081/employees/3`

```json
{
  "name": "Vasanth Kumar",
  "email": "vasu13@abc.com",
  "departmentName": "developer",
  "salary": 45000
}
```

Delete employee:

`DELETE http://localhost:8081/employees/3`

Replace `3` with the actual employee ID you want to update or delete. Do not delete records you need to retain.

## Department Service APIs

Base URL: `http://localhost:8082`

| HTTP Method | Endpoint | Description |
|---|---|---|
| POST | `/departments` | Create a department |
| GET | `/departments` | Retrieve all departments |
| GET | `/departments/{id}` | Retrieve a department by ID |
| PUT | `/departments/{id}` | Update a department |
| DELETE | `/departments/{id}` | Delete a department |

### Sample department JSON

```json
{
  "departmentName": "SAP",
  "description": "Accounts",
  "employeeName": "Ramya"
}
```

### Example requests

Create department:

`POST http://localhost:8082/departments`

Retrieve all departments:

`GET http://localhost:8082/departments`

Retrieve department by ID:

`GET http://localhost:8082/departments/4`

Update department:

`PUT http://localhost:8082/departments/4`

```json
{
  "departmentName": "SAP Support",
  "description": "SAP support and accounting",
  "employeeName": "Ramya"
}
```

Delete department:

`DELETE http://localhost:8082/departments/4`

Replace `4` with the actual department ID you want to update or delete.

**Note:** Confirm the JSON property names and endpoint paths against your actual Department entity and controller. The sample assumes your entity uses `departmentName`, `description`, and `employeeName`.

## Validation and Exception Handling

The project requirements include request validation and exception handling.

Examples of useful validations include:

- Employee name must not be blank.
- Email must have a valid format.
- Salary must meet the application's configured constraints.
- Department name must not be blank.
- Department description must follow configured length constraints.

Use Jakarta Bean Validation annotations such as `@NotBlank`, `@Email`, `@Positive`, and `@Size` where appropriate.

For exception handling, a service may return `404 Not Found` when a requested record does not exist and `400 Bad Request` for invalid request data. The exact responses depend on the implementation.

Document only the validation rules and exception handlers that are actually implemented in your source code.

## Database Persistence Testing

Verify that data remains stored in MySQL after restarting each service.

### Employee Service

1. Create an employee using Postman.
2. Query the database:

```sql
USE employeeservice;
SELECT * FROM employees;
```

3. Stop Employee Service.
4. Start Employee Service again.
5. Send `GET http://localhost:8081/employees`.
6. Confirm that the created employee remains available.

### Department Service

1. Create a department using Postman.
2. Query the database:

```sql
USE departmentservice;
SELECT * FROM departments;
```

3. Stop Department Service.
4. Start Department Service again.
5. Send `GET http://localhost:8082/departments`.
6. Confirm that the created department remains available.

Successful results demonstrate that the application is storing data persistently in MySQL rather than only in application memory.

## Postman Testing

Test the following operations for both services:

- Create a record using POST.
- Retrieve all records using GET.
- Retrieve a record by ID using GET.
- Update a record using PUT.
- Delete a test record using DELETE.
- Verify the corresponding database changes.
- Restart each service and verify persistence.

### Suggested Postman collection

Export your Postman collection as JSON and save it at:

`postman/Employee-Department-Collection.json`

Include example request bodies and the correct URLs for both services.

### Evidence for project submission

Include screenshots showing:

1. Both Spring Boot services starting successfully.
2. Successful POST and GET requests in Postman.
3. Successful PUT and DELETE requests.
4. Employee records in the `employeeservice` database.
5. Department records in the `departmentservice` database.
6. Data still available after restarting each service.

Only mark tests as successful after you have actually run them.

## Troubleshooting

### MySQL access denied

Check the database username and password.

### Communications link failure

Ensure MySQL Server is running and that the configured database port is correct.

### Unknown database

Create the required database or correct the JDBC URL.

### Port already in use

Stop the application using the port or configure another available port.

### 404 Not Found

Check the controller's `@RequestMapping` and endpoint paths.

### 400 Bad Request

Check the JSON property names, required fields, data types, and validation messages.

### 500 Internal Server Error

Review the Spring Boot console logs for the exception and its root cause.

## Future Enhancements

Potential improvements include:

- Inter-service communication using `Rest Client` or Open Feign.
- DTOs and Map Struct for request/response mapping.
- Pagination and sorting.
- Swagger/Open API documentation.
- Spring Security and JWT authentication.
- Unit and integration testing.
- Docker support.
- Service discovery and centralized configuration.
- Database schema migrations using Flyway or Liquibase.
- CI/CD integration using GitHub Actions.

These are proposed enhancements, not claims that they are already implemented.

## Author

**Name:** Gokul M

**GitHub:** https://github.com/Gokulmani30/https-github.com-Gokulmani30-EmployeeDepartmentMicroservices

**Project:** Employee and Department Microservices

**Purpose:** Demonstration of REST APIs, Spring Boot, JPA, MySQL persistence, and API testing.

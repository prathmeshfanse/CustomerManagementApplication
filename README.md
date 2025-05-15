# Customer Management System - Spring Boot Application

## Table of Contents
- [Overview](#overview)
- [How to Build and Run](#how-to-build-and-run)
- [Sample API Requests](#sample-api-requests)
- [Accessing H2 Database Console](#accessing-h2-database-console)
- [API Documentation (Swagger)](#api-documentation-swagger)
- [Assumptions](#assumptions)

---

## Overview
This is a Spring Boot-based Customer Management System that provides RESTful APIs to manage student data, including creating, updating, retrieving, and deleting student records.

---

## How to Build and Run

## Build the application
mvn clean install

## Run the application
mvn spring-boot:run

## The application will be accessible at:
http://localhost:8080/customers/allCustomers

### Prerequisites
- Java 17+  
- Maven 3.6+  
- Postman or any API testing tool (optional)

### Steps
1. **Clone the repository**
   ```bash
   git clone https://github.com/prathmeshfanse/Student-Management.git
   cd student-management-system

### Sample API Requests

Get all customers 
GET /customers/allCustomers
 Example : http://localhost:8080/customers/allCustomers

Get customer by ID
GET /customer/{id}
 Example : http://localhost:8080/customers/8fea00e2-ee5c-445f-a9f2-6ce901583f86

Get customer by Name
GET /customer?name=?
 Example : http://localhost:8080/customers?name=happy

Get customer by Email
GET /customers?email=?
 Example : http://localhost:8080/customers?email=happy@gmail.com

Add a new customer
POST /customers
 Example : http://localhost:8080/customers

Content-Type: application/json

{
    "name" : "happy",
    "email" : "happpy@gmail.com",
    "annualSpend" : 1000.55,
    "lastPurchedDate" : "2024-04-10"
}

Update customer
PUT customers/{id}
 Example : http://localhost:8080/customers/8fea00e2-ee5c-445f-a9f2-6ce901583f86
Content-Type: application/json
{
         "name": "sad",
        "email": "sad@gmail.com",
        "annualSpend": 10000.55,
        "lastPurchedDate": "2025-10-10"
}

Delete customer
DELETE /customers/{id}
 Example : http://localhost:8080/customers/8fea00e2-ee5c-445f-a9f2-6ce901583f86

### Accessing H2 Database Console

H2 console can be accessed at:
 http://localhost:8080/h2-console

Configuration in application.properties:
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=

### API Documentation (Swagger)

You can explore and test all endpoints through Swagger UI:
 http://localhost:8080/swagger-ui/index.html
 
Dependency in pom.xml:
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>

### Assumptions

Application uses in-memory H2 DB (data resets on restart).
No authentication is implemented (for now).
Validation is basic; can be enhanced.
All API responses are in JSON format.
Swagger is enabled for easier API exploration.

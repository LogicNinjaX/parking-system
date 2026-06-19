# 🚗 Parking System – Parking Sharing Platform API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange?logo=mysql)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/Auth-JWT-black)]()
[![Build](https://img.shields.io/badge/Build-Passing-success)]()
[![OpenAPI](https://img.shields.io/badge/API%20Docs-Swagger-green?logo=swagger)]()
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> A **Spring Boot-based Peer-to-Peer Parking Platform** where parking owners list spaces and drivers book them seamlessly using a built-in wallet system, secure JWT authentication, and automated email notifications.

---

## 🌟 Overview

**Spot Share** enables individuals to monetize unused parking spaces while helping drivers find affordable parking easily.

The platform includes:

* JWT authentication
* Role-based access
* Wallet payments
* Booking lifecycle
* Email notifications with templates
* Swagger API docs

This project is built using **clean architecture** and production-ready patterns.

---

## 🚀 Live Demo

| Service | Link |
|----------|------|
| Swagger UI | https://parking-api-spkc.onrender.com/swagger-ui/index.html |
| OpenAPI JSON | https://parking-api-spkc.onrender.com/v3/api-docs |

> ⚠️ Render free instances may take 30–60 seconds to wake up after inactivity.

---

## ✨ Features

🔹 **Authentication & Authorization** — Secure JWT login and role-based access
🔹 **Parking Spot Management** — Owners can list and manage parking spaces
🔹 **Search & Booking** — Drivers search by location/date and book instantly
🔹 **Wallet System** — Recharge wallet and pay for bookings
🔹 **Owner Earnings** — Booking amount credited to owner wallet
🔹 **Email Notifications** —

* Booking confirmation
* Listing confirmation
* Wallet recharge confirmation

🔹 **Reviews & Ratings** — Users can rate parking spots
🔹 **Swagger Docs** — Test APIs interactively

---

## 🧰 Tech Stack

| Layer            | Technology              |
| ---------------- | ----------------------- |
| **Language**     | Java 21                 |
| **Framework**    | Spring Boot 3           |
| **Database**     | MySQL                   |
| **ORM**          | Spring Data JPA         |
| **Security**     | Spring Security + JWT   |
| **Mapping Tool** | MapStruct               |
| **Validation**   | Jakarta Validation      |
| **Email**        | Spring Mail + Thymeleaf |
| **API Docs**     | Springdoc OpenAPI       |
| **Build Tool**   | Maven                   |

---

## 👥 Roles

| Role              | Permissions                    |
| ----------------- | ------------------------------ |
| **Vehicle Owner** | Search, Book, Pay, Review      |
| **Parking Owner** | List spot, View bookings, Earn |
| **Admin**         | Manage users & listings        |

---

## ⚙️ Maven Dependencies

<details>
<summary>Click to view dependencies</summary>

```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- MapStruct -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.6.3</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.6.3</version>
    <scope>provided</scope>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.6</version>
    <scope>runtime</scope>
</dependency>

<!-- Email -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>

<!-- Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Swagger -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.9</version>
</dependency>

<!-- Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

</details>

---

## ⚙️ Configuration (`application.yml`)

```yaml
spring:
  application:
    name: parking


  datasource:
    url: ${DB_URL}
    username: ${DB_UNAME}
    password: ${DB_PASS}
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL8Dialect

  messages:
    basename: messages

  mail:
    host: ${SMTP_HOST}
    port: ${SMTP_PORT}
    from: ${MAIL_FROM}
    username: ${SMTP_UNAME}
    password: ${SMTP_PASS}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true


jwt:
  secret: ${JWT_SECRET}
  expiration: ${JWT_EXP} # in milliseconds
cors:
  allowed-origins: ${ALLOWED_ORIGINS} # cors origins (,) separated
```

---

## 🔑 Environment Variables

| Variable   | Description    |
| ---------- | -------------- |
| DB_URL     | Database URL   |
| DB_UNAME   | DB username    |
| DB_PASS    | DB password    |
| SMTP_HOST  | SMTP host      |
| SMTP_PORT  | SMTP port      |
| SMTP_UNAME | SMTP username  |
| SMTP_PASS  | SMTP password  |
| JWT_SECRET | JWT secret     |
| JWT_EXP    | JWT expiration |

---

## 🏃 How to Run

### Prerequisites

* Java 21+
* Maven
* MySQL
* SMTP credentials

### Steps

```bash
git clone https://github.com/LogicNinjaX/parking-system.git
cd parking-system
mvn spring-boot:run
```

Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧭 Sample Endpoints

| Method | Endpoint                  | Description     |
| ------ | ------------------------- | --------------- |
| POST   | `/api/v1/auth/login`      | Login           |
| POST   | `/api/v1/parkings`   | List parking     |
| GET    | `/api/v1/parkings`   | Search          |
| POST   | `/api/v1/bookings/{parking-id}/book`        | Book            |
| POST   | `/api/v1/wallets/recharge` | Recharge wallet |

---

## 🏗️ Architecture

```plaintext
Client → Controller → Service → Repository → Database
                      ↓
                  Wallet Service
                      ↓
                 Email Service
                      ↓
                 SMTP Server
```

---

## 🔗ER DIAGRAM <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Sleeping%20Face.png" alt="Sleeping Face" width="25" height="25" />
![er_diagram](/src/main/resources/images/er_diagram.jpeg)

## 🔗 Data Flow Diagram (DFD – Level 1) <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Smilies/Sleeping%20Face.png" alt="Sleeping Face" width="25" height="25" />
![er_diagram](/src/main/resources/images/dfd_lvl1.jpeg)
---

## 🔮 Future Enhancements

* Razorpay/Stripe integration
* Redis caching
* Image upload (S3)
* Admin dashboard
* Microservices version

---

## 👨‍💻 Author

**Nitish Kr Sahni**
Java Backend Developer | Spring Boot

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Nitish%20Sahni-blue?logo=linkedin)](https://www.linkedin.com/in/nitish-sahni/)
[![GitHub](https://img.shields.io/badge/GitHub-LogicNinjaX-black?logo=github)](https://github.com/LogicNinjaX)

---

# 👾 Keep Building

<img src="https://user-images.githubusercontent.com/74038190/225813708-98b745f2-7d22-48cf-9150-083f1b00d6c9.gif" width="500">

---

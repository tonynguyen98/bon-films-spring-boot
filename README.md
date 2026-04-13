# Bon Films Back-End

A back-end server to store reviews and films.

- Model View Controller (MVC) design & RESTful APIs with Spring Boot
- Dependency injection with EntityManager and Spring Boot
- ORM using Jakarta JPA / Hibernate
- MySQL database for persisting data
- Docker and AWS for hosting

# Requirements

- OpenJDK 25.0.2
- Maven (bundled wrapper available: `./mvnw`)
- MySQL database for production/local run
- Optional: Docker for containerized deployment

# Local initialization / setup

1. Clone the repository and open the project root.
2. Ensure the Maven wrapper is executable:
   ```bash
   chmod +x ./mvnw
   ```
3. Configure your MySQL connection in `src/main/resources/application.properties`.
   - Example for modern MySQL servers (MySQL 8+ / 9.x compatibility):
     ```properties
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.datasource.url=jdbc:mysql://localhost:3306/bon-films?useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC
     spring.datasource.username=YOUR_DB_USER
     spring.datasource.password=YOUR_DB_PASSWORD
     ```
   - If your MySQL server requires SSL, remove `useSSL=false` and set the proper SSL parameters.

4. Build and compile the project:
   ```bash
   ./mvnw -U -DskipTests compile
   ```
5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

# Project structure

- `src/main/java/com/comp586/bonfilms`
  - `BonFilmsSpringBootApplication.java` — app entry point
  - `CorsConfig.java` — CORS configuration for API access
  - `controllers/` — REST controllers for films and reviews
  - `entities/` — JPA models for `Film` and `Review`
  - `impls/` — service implementations using `EntityManager`
  - `models/` — additional DTOs / combined model types
  - `services/` — service interfaces for business logic
- `src/main/resources/application.properties` — runtime database and JPA config
- `src/test/resources/application.properties` — test database config using H2

# API endpoints

All endpoints are rooted at `/api`.

## Film endpoints

- `GET /api/films`
  - Returns all films.
  - Response: `200 OK` with JSON array.

- `GET /api/film/{id}`
  - Returns a film by ID.
  - Response: `200 OK` with JSON film or `404 Not Found`.

- `POST /api/film/create`
  - Creates a new film record.
  - Request body: JSON `Film` object.
  - Response: `201 Created` with saved film.

- `GET /api/film/{id}/reviews`
  - Returns reviews for a specific film.
  - Response: `200 OK` with JSON array or `404 Not Found`.

## Review endpoints

- `GET /api/review/{id}`
  - Returns a review by ID.
  - Response: `200 OK` with JSON review or `404 Not Found`.

- `POST /api/review/create`
  - Creates a new review.
  - Request body: JSON `Review` object.
  - Response: `201 Created` with saved review.

- `PUT /api/review/{id}`
  - Updates review rating and text.
  - Request body example:
    ```json
    {
      "rating": "4",
      "review": "Updated text"
    }
    ```
  - Response: `201 Created` with updated review or `404 Not Found`.

- `DELETE /api/review/{id}`
  - Deletes a review by ID.
  - Response: `202 Accepted` or `404 Not Found`.

# Data model overview

## Film

Fields include:

- `id`
- `title`
- `genre`
- `studio`
- `director`
- `topBilling`
- `synopsis`
- `reviews`

## Review

Fields include:

- `id`
- `rating`
- `review`
- `userReviewedId`
- `film`
- `dateReviewed`

# Testing

The project includes a test configuration that uses an in-memory H2 database.

```bash
./mvnw test
```

# API Server

After startup, the API is exposed under:

- `http://localhost:8080/api`

# Front-end repository

- https://github.com/bon-films/bon-films-front-end

# Features

- Two tables joined using JPA/Hibernate
- Docker MySQL server for database support
- Custom Dockerfile for containerized hosting
- AWS EC2 deployment with NGINX web server
- Cloudflare for HTTPS certificate management

# Bon Films Back-End

A Spring Boot REST API for managing films and reviews.

- Spring Boot 3.2.12
- Spring Data JPA repositories for data access
- Jakarta JPA / Hibernate ORM
- MySQL runtime support and H2 for tests
- Maven wrapper with Java 21 compatibility
- CORS configuration for API clients

# Requirements

- Java 21 (OpenJDK 21)
- Maven wrapper (`./mvnw`)
- MySQL database for production/local development
- Optional: Docker for containerized deployment

# Local setup

1. Clone the repository and open the project root.
2. Make the Maven wrapper executable:
   ```bash
   chmod +x ./mvnw
   ```
3. Configure your database connection in `src/main/resources/application.properties`.
   Example:
   ```properties
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.url=jdbc:mysql://localhost:3306/bon-films?useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
   spring.datasource.username=YOUR_DB_USER
   spring.datasource.password=YOUR_DB_PASSWORD
   ```
4. Build the project:
   ```bash
   ./mvnw clean package
   ```
5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

# Project structure

- `src/main/java/com/comp586/bonfilms`
  - `BonFilmsSpringBootApplication.java` — application entry point
  - `CorsConfig.java` — API CORS configuration
  - `controllers/` — REST controllers for film, review, and auth endpoints
  - `entities/` — JPA entity models for `Film`, `Review`, and `User`
  - `repositories/` — Spring Data JPA repository interfaces
  - `services/` — service interfaces for business logic
  - `impls/` — service implementations
  - `models/` — DTOs and combined view objects
- `src/main/resources/application.properties` — runtime database/JPA config
- `src/test/resources/application.properties` — test database config using H2

# API endpoints

## Film endpoints

- `GET /api/films`
  - Returns all films.
  - Response: `200 OK` with JSON array.

- `GET /api/films/{id}`
  - Returns a film by ID.
  - Response: `200 OK` with JSON film or `404 Not Found`.

- `POST /api/films`
  - Creates a new film.
  - Request body: JSON `Film` object.
  - Response: `201 Created` with saved film.

- `GET /api/films/{id}/reviews`
  - Returns reviews for a specific film.
  - Response: `200 OK` with JSON array or `404 Not Found`.

## Review endpoints

- `GET /api/reviews/{id}`
  - Returns a review by ID.
  - Response: `200 OK` with JSON review or `404 Not Found`.

- `POST /api/reviews`
  - Creates a new review.
  - Request body: JSON `Review` object.
  - Response: `201 Created` with saved review.

- `PUT /api/reviews/{id}`
  - Updates a review.
  - Request body example:
    ```json
    {
      "rating": 4,
      "review": "Updated text"
    }
    ```
  - Response: `200 OK` with updated review or `404 Not Found`.

- `DELETE /api/reviews/{id}`
  - Deletes a review by ID.
  - Response: `204 No Content` or `404 Not Found`.

## Combined review view

- `GET /api/film-reviews`
  - Returns combined film and review data.
  - Response: `200 OK` with JSON array.

## Auth endpoints

- `POST /auth/login`
  - Request body: JSON `AuthRequest` with `email` and `password`
  - Response: `200 OK` with JSON `AuthResponse`
    - `token` — temporary auth token for frontend storage
    - `user` — user object containing `id` and `email`

- `POST /auth/register`
  - Request body: JSON `AuthRequest` with `email` and `password`
  - Response: `201 Created` when registration succeeds

- `POST /auth/forgot-password`
  - Request body: JSON `AuthRequest` with `email` only
  - Response: `202 Accepted` for password reset flow

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

## User

Fields include:

- `id`
- `email`

# Testing

The project includes unit and controller tests using an in-memory H2 database.

```bash
./mvnw test
```

# API server

After startup, the API is available at:

- `http://localhost:8080`

# Notes

- The service layer now uses Spring Data JPA repositories instead of manual `EntityManager` queries.
- The project compiles with Java 21 and the bundled Maven wrapper.

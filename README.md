# Task Manager REST API

A Spring Boot-based educational project designed to manage tasks. It implements a complete REST API development lifecycle, including controllers, global exception handling, and comprehensive Unit testing.

## 🛠 Tech Stack
*   **Java 17 / 21**
*   **Spring Boot 3** (Web, Data JPA)
*   **PostgreSQL** (Database)
*   **Lombok** (Boilerplate reduction)
*   **JUnit 5 + Mockito** (Unit testing)
*   **Maven** (Build tool)

## 🚀 Features
*   **CRUD Operations** for tasks (Create, Read, Update, Delete).
*   **Search & Filtering**:
    *   Filter by completion status (`isDone`).
    *   Search by description (case-insensitive substring match).
*   **Sorting** capabilities for task lists.
*   **Global Exception Handling** (`@RestControllerAdvice`):
    *   Custom JSON error responses with appropriate HTTP status codes (400, 404, 500).
    *   Input validation handling (`@Valid`, `MethodArgumentNotValidException`).

## 🧪 Testing
The project is covered by Unit tests using **Mockito**:
*   Isolated testing of business logic (`TaskService`).
*   Mocking of repository and mapper layers.
*   Coverage of both positive and negative scenarios (e.g., *Task Not Found*).

## 📦 How to Run
1.  Clone the repository.
2.  Configure `application.properties` (set your PostgreSQL credentials).
3.  Run the application using Maven or your IDE.

Have a good day!

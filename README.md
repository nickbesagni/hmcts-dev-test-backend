# HMCTS Dev Test Backend

## Overview

This is a Spring Boot application for managing tasks. It provides RESTful endpoints for creating, retrieving, updating, and deleting tasks. The application uses PostgreSQL as the database and includes unit tests, integration tests, functional tests, and smoke tests to ensure the application's functionality.

## Prerequisites

- Java 21
- PostgreSQL
- Gradle
- Docker (optional, for running PostgreSQL in a container)

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/nickbesagnihmcts-dev-test-backend
cd hmcts-dev-test-backend
```

### Set Up PostgreSQL
You can either install PostgreSQL locally or use Docker to run PostgreSQL in a container.

#### Using Docker
```sh
docker run --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=testdb -p 5432:5432 -d postgres
```

#### Locally
1. Install PostgreSQL.
2. Create a database named `testdb`.
3. Set the password for the `postgres` user to `postgres`.
4. Configure the Application
5. Update either the application.yaml file or apply environment variables with the correct database connection details if necessary.


### Build and Run the Application
#### Using Gradle
```sh
./gradlew clean build
./gradlew bootRun
```

### Running Tests
#### Unit Tests
```sh
./gradlew test
Integration Tests
```

```sh
./gradlew verify
```


#### Functional Tests
```sh
./gradlew verify -Pfunctional
```

#### Smoke Tests
```sh
./gradlew verify -Psmoke
```

## API Endpoints
### Create a Task
```http
POST /tasks
Content-Type: application/json

{
  "title": "Sample Task",
  "description": "This is a sample task",
  "status": "Pending",
  "dueDateTime": "2025-04-16T22:00:00"
}
```

### Get a Task by ID
```http
GET /tasks/{id}
```

### Update a Task
```http
PUT /tasks/{id}
Content-Type: application/json

{
  "title": "Updated Task",
  "description": "This is an updated task",
  "status": "Completed",
  "dueDateTime": "2025-04-16T22:00:00"
}
```

### Delete a Task
```http
DELETE /tasks/{id}
```

### Get All Tasks
```http
GET /tasks
```

## Notes
Database Configuration: The application uses PostgreSQL as the database. The application.yaml file is configured to use environment variables for database connection details, allowing flexibility in different environments (development, testing, production).

Testing: The application includes unit tests, integration tests, functional tests, and smoke tests to ensure comprehensive test coverage. This helps in identifying issues early and ensures the reliability of the application.

Task Entity: The Task entity is annotated with @Entity, @Id, and @GeneratedValue to define it as a JPA entity. The @JsonFormat annotation is used to ensure the correct format of the dueDateTime field.

Controller: The TaskController provides RESTful endpoints for managing tasks. The endpoints are designed to handle JSON requests and responses, making it easy to interact with the API.

Error Handling: The application includes basic error handling for common scenarios, such as task not found.

## Next steps
Validation: Adding validation to the Task entity to ensure that required fields are provided and have valid values. Use annotations like @NotNull, @Size, and @Pattern.

Error Handling: Improving error handling by adding custom exception handlers to provide more informative error messages and HTTP status codes.

Security: Implementing security measures to protect the API endpoints. Use Spring Security to add authentication and authorization.

Documentation: Adding API documentation using Swagger or Spring REST Docs to provide a clear and interactive API specification.

CI/CD: Set up a continuous integration and continuous deployment (CI/CD) pipeline to automate the build, test, and deployment process.

Performance: Optimize the application for performance by tuning the database queries and connection pool settings.

Scalability: Ensure the application can scale horizontally by running multiple instances behind a load balancer.

Monitoring: Implement monitoring and logging to track the application's performance and identify issues in real-time.

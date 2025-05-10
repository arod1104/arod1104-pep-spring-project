# Spring Social Media Blog API

## Project Description

This project is a backend REST API for a hypothetical social media micro-blogging application. It manages user accounts and messages, supporting user registration, login, message creation, retrieval, update, and deletion. The backend is built using the Spring Boot framework, leveraging Spring MVC for REST endpoints and Spring Data JPA for database operations.

## Technologies Used

- Java 11
- Spring Boot 2.5.5
- Spring Data JPA
- Spring MVC
- H2 Database (in-memory, for development/testing)
- Maven (build tool)
- JUnit (testing)

## Features

- User registration with validation and duplicate username checks
- User login with authentication
- Create, retrieve, update, and delete messages
- Retrieve all messages or messages by user
- RESTful API endpoints following best practices
- In-memory H2 database for easy setup and testing
- Comprehensive unit and integration tests

To-do list:

- Add user password encryption
- Implement pagination for message retrieval
- Add API documentation (Swagger/OpenAPI)
- Enhance error handling and validation

## Project Responsibilities & Metrics

- Achieve at least 90% code coverage with unit and integration tests
- Maintain a code quality grade of A using static analysis tools
- Ensure build process completes in under 30 seconds on a standard machine
- Keep total lines of code under 2,000 for maintainability
- Enforce successful build and test execution on every commit via CI
- Document all public classes and methods with Javadoc
- Resolve all critical and major code smells before merging PRs
- Maintain zero failed tests in the main branch at all times

## Getting Started

### Clone the repository

```powershell
# Windows (PowerShell)
git clone https://github.com/your-username/arod1104-pep-spring-project.git
cd arod1104-pep-spring-project
```

```bash
# Unix/Linux/MacOS
git clone https://github.com/your-username/arod1104-pep-spring-project.git
cd arod1104-pep-spring-project
```

### Build and run the application

```powershell
# Windows (PowerShell)
mvn clean install
mvn spring-boot:run
```

```bash
# Unix/Linux/MacOS
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080` using the in-memory H2 database.

## Usage

The API exposes the following endpoints:

- `POST /register` — Register a new user
- `POST /login` — User login
- `POST /messages` — Create a new message
- `GET /messages` — Retrieve all messages
- `GET /messages/{messageId}` — Retrieve a message by ID
- `DELETE /messages/{messageId}` — Delete a message by ID
- `PATCH /messages/{messageId}` — Update a message by ID
- `GET /accounts/{accountId}/messages` — Retrieve all messages by a user

You can use tools like Postman or curl to interact with the API. See the user stories in this README for detailed request/response expectations.

## License

This project uses the following license: [MIT](https://opensource.org/license/mit).

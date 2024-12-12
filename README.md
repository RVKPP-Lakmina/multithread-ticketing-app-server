# Ticket Manager Backend

This is a Spring Boot backend application for managing real-time event ticketing using MongoDB and WebSocket.

## Features

- Integration with MongoDB for data persistence
- Real-time communication using WebSocket
- RESTful API endpoints for managing ticketing-related operations
- Logging with configurable levels
- Configurable CORS support

## Prerequisites

Before setting up the application, ensure you have the following installed:

- Java 17 or later
- Maven 3.6 or later
- MongoDB Atlas account (or any MongoDB instance)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd <repository-folder>
```

### 2. Configure the Application

#### Option 1: Using `application.properties`

Update the `src/main/resources/application.properties` file with the following:

```properties
spring.application.name=ticketManager
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-url>/ticketingapp?retryWrites=true&w=majority&appName=ticketing-app-cluster
spring.data.mongodb.database=ticketingapp
logging.level.org.springframework.web.socket=DEBUG
```

#### Option 2: Using `application.yml`

Alternatively, update the `src/main/resources/application.yml` file:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://<username>:<password>@<cluster-url>/ticketingapp?retryWrites=true&w=majority&appName=ticketing-app-cluster
      database: ticketingapp

cors:
  allowed:
    origins: "http://localhost:5173"
    methods: "GET,POST,PUT,DELETE,OPTIONS"
```

### 3. Install Dependencies

Ensure all dependencies are installed by running:

```bash
mvn clean install
```

### 4. Run the Application

Start the application by running:

```bash
mvn spring-boot:run
```

The application will start on the default port `8080`. You can verify by accessing:

```
http://localhost:8080
```

### 5. WebSocket Integration

WebSocket endpoints are enabled and can be accessed at:

```
ws://localhost:8080/ticketing-websocket
```

### 6. Testing

To run the tests, use:

```bash
mvn test
```

## Dependencies

The following dependencies are used in the project:

- **Spring Boot Starter Data MongoDB**: For MongoDB integration
- **Spring Boot Starter Web**: For building RESTful APIs
- **Spring Boot Starter WebSocket**: For real-time communication
- **Lombok**: To reduce boilerplate code
- **Spring Boot Starter Test**: For testing purposes

## Logging

Logging is configured to capture detailed WebSocket activities. Modify the log level in `application.properties` or `application.yml` as needed.

## CORS Configuration

CORS is configured to allow requests from `http://localhost:5173`. Update the `cors.allowed.origins` property in `application.yml` if your frontend runs on a different URL.

## MongoDB Setup

1. Create a MongoDB database named `ticketingapp`.
2. Replace `<username>` and `<password>` in the `spring.data.mongodb.uri` with your MongoDB Atlas credentials.
3. Replace `<cluster-url>` with your MongoDB cluster URL.

## License

This project is licensed under the [MIT License](LICENSE).

## Contact

For questions or support, please contact [your email].


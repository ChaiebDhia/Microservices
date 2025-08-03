# EasyTrip - A Microservices-Based Travel Management Platform

EasyTrip is a comprehensive travel management platform utilizing a modern microservices architecture. It is designed to provide a robust and scalable solution for managing travel-related services, including user accounts, reservations, destination management, agency services, and user reviews.

The project is built with **Spring Boot** for the backend microservices and **Angular** for the frontend user interface. It leverages **Eureka Server** for service discovery, **Spring Cloud Gateway** for routing, and **Spring Cloud Config** for centralized configuration.

## 🚀 Technologies

* **Backend**: Spring Boot, Spring Cloud (Eureka, Gateway, Config)
* **Frontend**: Angular
* **Database**: MySQL, H2 (for specific services)
* **Inter-service Communication**: Feign Client
* **Containerization**: Docker

## 📂 Project Structure

The project is organized into multiple microservices, each with a specific responsibility.

* `ApiGateway-main`: The entry point for all client requests, routing them to the appropriate microservice.
* `Avis-service`: Manages user reviews and feedback.
* `eurkaServer-main`: The service discovery server, allowing services to find and communicate with each other.
* `FrontEnd`: The Angular-based user interface.
* `gestion-destination-service`: Handles the management of travel destinations.
* `reservation-service`: Manages all travel reservation processes.
* `service-config`: The configuration server for all microservices.
* `Service-agence`: Manages travel agencies and their services.
* `transportService`: Handles transportation-related information and bookings.
* `user-service`: Manages user accounts, authentication, and profiles.

---

## 🛠️ Microservice Details

### 📢 Microservice - Gestion des Avis (`AvisService`)

* **Description**: This microservice is responsible for the management of user reviews and feedback on travel experiences. It's a key part of the `EasyTrip` platform, ensuring that users can share their opinions and that the content is moderated.
* **Key Features**:
    * Add a new review with user validation via `user-service`.
    * Retrieve, update, and delete reviews.
    * Advanced search and filtering by keyword, travel ID, and approval status.
    * Review moderation (approve/reject).
    * Relevance-based sorting of reviews.
    * User reactions (like/dislike).
* **Architecture**: Spring Boot, MySQL, Feign Client for inter-service communication.
* **Endpoints**: See original documentation for detailed endpoints.

### 📍 MicroService - Gestion des Destinations (`DestinationService`)

**Developed by**: Chaieb Dhia
* **Description**: This service manages all tourist destinations available on the `EasyTrip` platform. It allows for the creation, modification, and retrieval of detailed destination information.
* **Key Features**:
    * Add new destinations with details (name, description, location).
    * Retrieve, update, and delete destination information.
    * Advanced search by name, location, and category.
    * Popularity-based sorting of destinations.
    * Media storage integration for images and videos.
* **Architecture**: Spring Boot, H2 Database, Feign Client.
* **Endpoints**: See original documentation for detailed endpoints.

### 👤 MicroService - Gestion des Utilisateurs (`UserService`)

* **Description**: This is the core service for user management. It handles all aspects of user accounts, from registration to authentication and profile management. Other services, like the `AvisService`, rely on this service to validate user existence and retrieve user details.
* **Key Features**:
    * User registration and authentication.
    * User profile management.
    * Secure password handling.
    * APIs for other services to query user information.
* **Architecture**: Spring Boot, MySQL, Spring Security.

### ✈️ MicroService - Réservations (`ReservationService`)

* **Description**: This service is responsible for managing the entire reservation lifecycle. It handles everything from creating new reservations to tracking their status and processing payments.
* **Key Features**:
    * Create, view, and manage travel reservations.
    * Integration with other services (e.g., `transportService`, `Service-agence`) to book travel components.
    * Payment processing and status updates.
    * Reservation history and tracking.
* **Architecture**: Spring Boot, MySQL.

### 🚉 MicroService - Transport (`TransportService`)

* **Description**: The `transportService` manages all information related to transportation options within the `EasyTrip` platform. This includes flights, trains, buses, and rental cars, along with their associated details.
* **Key Features**:
    * Manage transport details (type, schedule, price).
    * Search and filter transport options.
    * Integration with the `ReservationService` for booking.
* **Architecture**: Spring Boot, MySQL.

### 🏢 MicroService - Agence de Voyage (`Service-agence`)

* **Description**: This service handles the management of travel agencies and the services they offer. It provides a platform for agencies to list their packages and for users to discover and book them.
* **Key Features**:
    * Agency registration and profile management.
    * Listing of travel packages and services.
    * Search and filter packages by agency.
* **Architecture**: Spring Boot, MySQL.

## ⚙️ How to Run the Project

### Prerequisites

* Java 17+
* Maven
* Node.js & npm (for the Angular FrontEnd)
* Docker (Optional, for containerization)

### Backend Setup

1.  Clone this repository: `git clone https://github.com/your-username/your-repository-name.git`
2.  Navigate to each microservice directory (e.g., `Avis-service`).
3.  Build the project using Maven: `mvn clean install`
4.  Run the services: `mvn spring-boot:run`

**Note**: You must run the `eurkaServer-main` and `service-config` first, followed by the `ApiGateway-main` and then the rest of the services.

### Frontend Setup

1.  Navigate to the `FrontEnd` directory.
2.  Install dependencies: `npm install`
3.  Run the application: `ng serve`
4.  Access the application at `http://localhost:4200` in your browser.

---

## 🤝 Contribution

This project was developed as a group effort. We welcome any feedback or suggestions.

## 📜 License

This project is licensed under the MIT License.

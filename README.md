# E-Commerce Backend System

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-blue)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0-red)](https://redis.io/)
[![Kafka](https://img.shields.io/badge/Apache%20Kafka-3.6.0-black)](https://kafka.apache.org/)
[![JWT](https://img.shields.io/badge/JWT-0.11.5-lightgrey)](https://jwt.io/)

A robust and scalable e-commerce backend API built with Spring Boot, designed to handle user management, product catalog, shopping cart, order processing, and payment integration. This system leverages modern technologies for high performance, security, and real-time data processing.

## 🚀 Features

- **User Management**: Registration, authentication, and role-based access control (Admin, User)
- **Product Catalog**: CRUD operations for products and categories
- **Shopping Cart**: Add, update, and remove items from cart
- **Order Management**: Place orders, track status, and manage order history
- **Payment Processing**: Integration with multiple payment methods
- **Real-time Messaging**: Kafka Streams for event-driven architecture
- **Caching**: Redis for session management and performance optimization
- **Security**: JWT-based authentication and authorization
- **Email Notifications**: SMTP integration for order confirmations and alerts
- **Database**: MySQL with JPA/Hibernate for data persistence
- **Testing**: Comprehensive unit and integration tests

## 🛠 Tech Stack

### Backend
- **Java 17**: Core programming language
- **Spring Boot 3.2.5**: Framework for building RESTful APIs
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: ORM for database interactions
- **Spring Kafka**: Integration with Apache Kafka for messaging
- **Spring Data Redis**: Caching and session management
- **Spring Mail**: Email service integration

### Database & Messaging
- **MySQL 8.0**: Primary relational database
- **Redis 7.0**: In-memory data structure store for caching
- **Apache Kafka 3.6.0**: Distributed event streaming platform

### Tools & Libraries
- **Maven 3.9.0**: Build automation and dependency management
- **Lombok**: Reduces boilerplate code
- **Jackson**: JSON processing
- **JWT**: JSON Web Tokens for secure authentication
- **H2 Database**: In-memory database for testing

### Infrastructure
- **Docker Compose**: Container orchestration for local development

## 📋 Prerequisites

Before running this application, ensure you have the following installed:

- **Java 17** or higher
- **Maven 3.9.0** or higher
- **MySQL 8.0** or higher
- **Redis 7.0** or higher
- **Docker** and **Docker Compose** (for local infrastructure setup)

## 🔧 Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/e-commerce-backend.git
   cd e-commerce-backend
   ```

2. **Set up the database**:
   - Install MySQL and create a database named `ecom`
   - Update `src/main/resources/application.properties` with your MySQL credentials

3. **Configure external services**:
   - Set up Redis server
   - Configure Kafka (using Confluent Cloud or local setup)
   - Update email SMTP settings in `application.properties`

4. **Build the project**:
   ```bash
   mvn clean install
   ```

## ⚙️ Configuration

The application uses `application.properties` for configuration. Key settings include:

- **Database**: MySQL connection details
- **Redis**: Host and port configuration
- **Kafka**: Bootstrap servers, authentication (SASL_SSL)
- **Email**: SMTP server configuration
- **JWT**: Secret key and expiration settings
- **Security**: CORS and other security configurations

For production, use environment variables or external configuration files to override sensitive properties.

## ▶️ Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using Docker Compose (for local infrastructure)
```bash
docker-compose up -d
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📚 API Endpoints

### Public Endpoints
- `GET /api/public/products` - Get all products
- `POST /api/public/register` - User registration
- `POST /api/public/login` - User login

### User Endpoints (Authenticated)
- `GET /api/user/cart` - Get user's cart
- `POST /api/user/cart/add` - Add item to cart
- `POST /api/user/order` - Place an order
- `GET /api/user/orders` - Get user's orders

### Admin Endpoints (Admin Role Required)
- `POST /api/admin/product` - Add new product
- `PUT /api/admin/product/{id}` - Update product
- `DELETE /api/admin/product/{id}` - Delete product
- `GET /api/admin/orders` - Get all orders
- `PUT /api/admin/order/{id}/status` - Update order status

For detailed API documentation, refer to the Swagger UI at `http://localhost:8080/swagger-ui.html`

## 🧪 Testing

Run the test suite using Maven:
```bash
mvn test
```

The project includes unit tests for services and integration tests for controllers.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

For questions or support, please contact:
- **Email**: omphopse19@gmail.com

---

*Built with ❤️ using Spring Boot and modern Java technologies*</content>
<parameter name="filePath">d:\Study Material\IBF\java\Projects\ecom\README.md
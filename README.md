# Asset Service
This service is responsible for managing military assets including vehicles, equipment, and supplies. It provides functionalities to create, read, update, and delete assets. This service provides functionality for asset location tracking.

## Features
- Asset Management: Create, read, update, and delete assets.
- Location Tracking: Track the real-time location of assets.
- Search and Filter: Search and filter assets based on various criteria.

## API Documentation
The OpenAPI Specification for the Asset Service can be found at `/api-docs`. For swagger ui, visit `/swagger-ui.html`.

## Technologies Used
- Java 21
- Spring Boot
- Hibernate
- PostgreSQL
- Docker
- Kubernetes
- OpenAPI/Swagger

## Environment Variables
```
DB_USER=
DB_PASSWORD=
DB_NAME=
DB_URL=
API_GATEWAY_URL=
KAFKA_BOOTSTRAP_SERVERS=
```
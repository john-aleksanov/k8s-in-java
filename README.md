## Overview

This project is a multi-module Gradle setup featuring two interacting Spring Boot microservices and a separate `service-registry` microservice.

### Resource Service

- **Deployment**: Deployed on embedded Tomcat in a docker container with a DNS name of `resource-service` and a `8085:8085` port mapping to host.
- **Responsibilities**:
    - Accepts an MP3 file.
    - Extracts and saves the content to a postgres DB deployed in a docker container with a DNS name of `resource-db` and a `5432:5432` port mapping to host.
    - Extracts metadata and sends it to the Song Service via HTTP.
    - Provides endpoints to fetch song content by ID and batch-remove song contents using a list of IDs.

### Song Service

- **Deployment**: Deployed on embedded Tomcat in a docker container with a DNS name of `song-service` and a `8086:8086` port mapping to host.
- **Responsibilities**:
    - Saves received metadata to a postgres DB deployed in a docker container with a DNS name of `song-db` and a `5433:5432` port mapping to host.
    - Provides endpoints to fetch song metadata by ID and batch-remove song metadata using a list of IDs.

### Service Registry
- **Deployment**: Deployed on embedded Tomcat in a docker container with a DNS name of `service-registry` and a `8761:8761` port mapping to host.
- **Responsibilities**: This is a Eureka service registry that registers Eureka-aware applications and provides their coordinates to clients.


## How to Run

Build the projects running `./gradlew assemble` in the root project directory and in the `service-registry` directory.
Run `docker-compose up` in the root project directory.

## Notes & Limitations

This project serves as a basic example to demonstrate a microservices architecture. Due to its simplicity, several
shortcuts and hardcoding have been implemented. In a production setting, the following improvements would be made:

1. **Testing**: Currently, no tests are available. A TDD approach would be adopted.
2. **Observability**: Logging and performance metrics would be added.
3. **Persistent store**: At present, db container ports are mapped to host ports for ease of debugging. In production, an `expose` directive would be used to expose ports for inter-container communication. Also, instead of generating DDL on the fly, a DB change management framework like Liquibase or Flyway would be used.
3. **Architecture**: Domain-Driven Design / ports - adapters architecture would be used for better decoupling.
4. **Localization**: Spring's `MessageSource` would be used for message resolution.
5. **Error Handling**:
    - More robust error handling mechanisms would be in place.
    - HTTP error handling would be added in Resource Service's `SongClient` class.
6. **Data Mapping**: Instead of using `ObjectMapper`, a well-configured framework like ModelMapper or MapStruct would be used.
7. **Transactional Integrity**: The current setup lacks transactional boundaries, leading to potential data inconsistencies.
8. **API first**: An OpenAPI description of the services' APIs would be provided.
9. **Load balancing**: There is no load balancing employed by `Resource Service` in relation to `Song Service`. The first instance from Eureka is taken and used.
10. Etc., etc.

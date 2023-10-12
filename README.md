## Overview

This project is a multi-module Gradle setup featuring two interacting Spring Boot microservices.

### Resource Service

- **Port**: Deployed on embedded Tomcat at `localhost:8085`.
- **Responsibilities**:
    - Accepts an MP3 file.
    - Extracts and saves the content to a persistent store.
    - Extracts metadata and sends it to the Song Service via HTTP.
    - Provides endpoints to fetch song content by ID and batch-remove song contents using a list of IDs.

### Song Service

- **Port**: Deployed on embedded Tomcat at `localhost:8086`.
- **Responsibilities**:
    - Saves received metadata to a persistent store.
    - Provides endpoints to fetch song metadata by ID and batch-remove song metadata using a list of IDs.

## How to Run

Run the services using IntelliJ IDEA or another IDE. Use the main classes `ResourceServiceApplication` and `SongServiceApplication`.

## Notes & Limitations

This project serves as a basic example to demonstrate a microservices architecture. Due to its simplicity, several
shortcuts and hardcoding have been implemented. In a production setting, the following improvements would be made:

1. **Testing**: Currently, no tests are available. A TDD approach would be adopted.
2. **Observability**: Logging and performance metrics would be added.
3. **Persistent store**: At present, an in-memory DB is used. In the future lessons I will update it to a local docker container.
3. **Architecture**: Domain-Driven Design (DDD) would be used for better decoupling.
4. **Localization**: Spring's `MessageSource` would be used for message resolution.
5. **Error Handling**:
    - More robust error handling mechanisms would be in place.
    - HTTP error handling would be added in Resource Service's `SongClient` class.
6. **Data Mapping**: Instead of using `ObjectMapper`, a well-configured framework like ModelMapper or MapStruct would be used.
7. **Transactional Integrity**: The current setup lacks transactional boundaries, leading to potential data inconsistencies.
8. **API first**: an OpenAPI description of the services' APIs would be provided.
8. Etc., etc.

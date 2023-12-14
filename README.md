## Developments since preceding task
This branch introduces a new song property, genre. It's added as a separate flyway migration to update the DB schema 
as well as throughout the song service. song service version is updated to 0.2 in the Deployment k8s object.

To verify the setup:
1. Switch to the previous branch:
```shell
git checkout 2-deployment-and-config-2-probes
```
2. Deploy version 0.1 of the services:
```shell
./deploy.sh
```
3. Switch back to the current branch:
```shell
git checkout 2-deployment-and-config-3-deployment-strategies
```

4. Run the new version of the deploy script that will do a rolling update of song service:
./deploy.sh
```

## Overview

This project is a multi-module Gradle setup featuring two interacting Spring Boot microservices: resource-service
and song-service deployed in a k8s cluster 'dev-marvel'.

### Resource Service

- **API**: see [this postman collection](k8s-overview.postman_collection.json)
- **Responsibilities**:
    - Accepts an MP3 file (the resource).
    - Extracts and saves the content to a Postgres DB.
    - Extracts metadata and sends it to the Song Service via HTTP.
    - Provides endpoints to fetch song content by ID and batch-remove song contents using a list of IDs.

### Song Service

- **API**: see [this postman collection](k8s-overview.postman_collection.json)
- **Responsibilities**:
    - Saves received metadata to a Postgres DB.
    - Provides endpoints to fetch song metadata by ID and batch-remove song metadata using a list of IDs.

## Notes & Limitations

This project serves as a basic example to demonstrate a microservices architecture and deployment in a k8s cluster. 
Due to its simplicity, several shortcuts and hardcoding have been implemented. In a production setting, the 
following improvements would be made:

1. **Testing**: Currently, no tests are available. A TDD approach would be adopted.
2. **Observability**: Logging and performance metrics would be added.
3. **Architecture**: Domain-Driven Design / ports - adapters architecture would be used for better decoupling.
4. **Localization**: Spring's `MessageSource` would be used for message resolution.
5. **Error Handling**:
    - More robust error handling mechanisms would be in place.
    - HTTP error handling would be added in Resource Service's `SongClient` class.
6. **Data Mapping**: Instead of using `ObjectMapper`, a well-configured framework like ModelMapper or MapStruct would be
   used.
7. **Transactional Integrity**: The current setup lacks transactional boundaries, leading to potential data
   inconsistencies.
8. **API first**: An OpenAPI description of the services' APIs would be provided.
9. **Load balancing**: There is no load balancing employed by `Resource Service` in relation to `Song Service`. The
   first instance from Eureka is taken and used.
10. Etc., etc.

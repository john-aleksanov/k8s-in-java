## Developments since preceding task

This branch introduces liveness and readiness probes. Spring Boot Actuator has been added to serve as the provider.
To model liveness and readiness failures, just edit either service's Deployment object and change the liveness and / 
or readiness port to any other value, e.g., 8087. Reapply the manifests and observe the service failing to start.

To verify the setup:
1. Run the deploy script to build the project, build docker images and push them to the repo, and deploy the k8s 
   cluster. Don't forget to change the repo in the script to your own Docker Hub repo.
```shell
./deploy.sh
```
2. Observe the deployments start and describe them to verify liveness / readiness probes have been applied:
```shell
kubectl describe deployment song-service -n dev-marvel
```
You will see something like:
```shell
    Liveness:   http-get http://:8086/actuator/health delay=5s timeout=1s period=5s #success=1 #failure=2
    Readiness:  http-get http://:8086/actuator/health delay=5s timeout=1s period=5s #success=1 #failure=2
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

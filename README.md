## Developments since preceding task

This branch introduces config maps and secrets. Now, Postgres usernames and passwords that each service uses are 
stored as cluster secrets and ingested into the services upon cluster start. Additionally, Spring configuration
files (application.yml) are stored in config maps and mounted to service containers as volumes. Finally, Flyway was 
introduced to manage database versioning, and a bootstrap db schema script has been provided.

To verify the setup:
1. Build the project from the root directory:
```shell
./gradlew build
```
2. Build the docker images and push to your repo in Docker Hub:
```shell
docker build -t <repo>/resource-service resource-service/
docker push <repo>/resource-service

docker build -t <repo>/song-service song-service/
docker push <repo>/song-service
```
3. Apply all manifests:
```shell
kubectl apply -f k8s -f resource-service/k8s f song-service/k8s
```

4. Use [the postman collection](k8s-overview.postman_collection.json) to submit an mp3 file to resource service:
```shell
POST http://localhost:30002/resources
```

A convenience [deploy.sh script](./deploy.sh) has been provided to ease the deployment. Just replace the repo with 
your own Docker Hub repo and run it to deploy the cluster.

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

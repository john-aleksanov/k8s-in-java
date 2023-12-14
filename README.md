## Developments since preceding task
This branch does not add any additional capabilities but just describes how to perform a rollback of song service to 
version 0.1.

1. Check the deployment history:
```shell
kubectl rollout history deployment/song-service -n dev-marvel
```
2. Check the revision history:
```shell
kubectl rollout history deployment/song-service -n dev-marvel --revision=<revision-number>
```
3. Roll back to the previous deployment revision:
```shell
kubectl rollout undo deployment/song-service -n dev-marvel
```

4. Run the new version of the deploy script that will do a rolling update of song service:
```shell
./deploy.sh
```

It's worth noting though that since we introduced flyway migrations and [V2__add_song_genre.sql](./song-service/src/main/resources/db/migration/V2__add_song_genre.sql)
got applied, rollbacks to v0.1 will fail as Flyway has applied the above script and noted it as applied in the metadata
table. When the rollback is performed, the script is absent on the classpath, hence the failure. Some of the 
workarounds to this problem are:
1. Introduce scripts that, when applied, roll back the DB to the previous state.
2. Make backups of the DB often and roll it back manually.

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

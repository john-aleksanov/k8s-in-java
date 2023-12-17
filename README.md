## Developments since preceding task
This branch adds Helm to the party:
1. The `./helm` directory that contains the parent Helm chart and the respective values.
2. The `./resource-service/helm` and `./song-service/helm` directories that contain helm charts for the services.

To deploy the application using Helm:
1. Build the dependencies (resource-service and song-service):
```shell
cd helm
helm dependencies build
```
These commands will package the respective helm files into tarball archives in the `./helm/charts` directory
2. Check the revision history:
```shell
helm install my-application .
```
This will bring up the k8s cluster comprising the two services and all relating k8s objects.
3. Check that the cluster is up and running:
```shell
kubectl get pods,services,deployments,replicasets,statefulsets,configmaps,secrets --namespace dev-marvel
```
4. To spin up the cluster with some non-default values, run:
```shell
helm upgrade my-application . --set namespace=<your-namespace>, song-service.replicaCount=<value>,resource-service.replicaCount=<value>
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

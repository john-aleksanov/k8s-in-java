## Developments since preceding task
This branch adds an nginx ingress controller to the setup:
* [Charts.yaml](./helm/Chart.yaml)
* [k8s ingress](./helm/templates/manifest.yml)
To provide an example of URL rewriting, the k8s ingress is configured to forward all requests for `/resources/v1` to 
  `/resources`.

To deploy the application:
1. Add the `ingress-nginx` repo to local helm:
```shell
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
```
2. Update parent chart dependencies:
```shell
cd ./helm
helm dependencies update
```
3. Install the application:
```shell
helm install my-application .
```
4. Check that the cluster is up and running:
```shell
kubectl get pods,services,deployments,replicasets,statefulsets,configmaps,secrets,ingress --namespace dev-marvel
```
5. Send a request to add a song to the application (assuming you have a `123.mp3` file in your ~/Downloads folder):
```shell
curl -X POST -F "file=@~/Downloads/123.mp3" http://localhost:80/resources
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

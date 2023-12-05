## Developments since preceding task
This branch introduces the concept of persistence.
1. A new [persistence manifest file](resource-service/k8s/persistence.yml) has been added for resource service. It 
   creates a custom `local-storage` storage class, a `local-pv` persistent volume, a `local-pvc` persistent volume 
   claim in the `dev-marvel` namespace.
2. The [resource service manifest](resource-service/k8s/manifest.yml) has been updated to add a volume mmount that 
   corresponds to the PVC above, to the resource-service deployment.

To verify the volume works as intended:
1. Deploy the two manifests:
```shell
kubectl apply -f resource-service/k8s/persistence.yml -f resource-service/k8s/manifest.yml
```
2. Get the names of the pods:
```shell
kubectl get pods -n dev-marvel
```
3. Execute the shell in one of the pods and create a file in the `my-storage` folder:
```shell
kubectl exec -it <pod1-name> -n dev-marvel /bin/sh
touch /my-storage/my-file.txt
exit
```
4. Execute the shell in the other pod and verify the file exists:
```shell
kubectl exec -it <pod2-name> -n dev-marvel /bin/sh
cat /my-storage/my-file.txt
```

## Overview

This project is a multi-module Gradle setup featuring two interacting Spring Boot microservices: resource-service 
and song-service deployed in a k8s cluster 'dev-marvel'.

### Resource Service

- **API**: see [this postman collection](k8s-overview.postman_collection.json)
- **Responsibilities**:
    - Accepts an MP3 file (the resource).
    - Extracts and saves the content to a file-based H2 DB.
    - Extracts metadata and sends it to the Song Service via HTTP.
    - Provides endpoints to fetch song content by ID and batch-remove song contents using a list of IDs.

### Song Service

- **API**: see [this postman collection](k8s-overview.postman_collection.json)
- **Responsibilities**:
    - Saves received metadata to a file-based H2 DB.
    - Provides endpoints to fetch song metadata by ID and batch-remove song metadata using a list of IDs.

## How to Run

1. Build the projects running `./gradlew assemble` in the root project directory.
2. Build docker images:
```shell
docker build -t johnsallison/song-service ./song-service
docker build -t johnsallison/resource-service ./resource-service
```
3. Apply the k8s manifest files in the `song-service/k8s` and `resource-service/k8s` directories:
```shell
kubectl apply -f ./song-service/k8s/manifest.yml -f ./resource-service/k8s/manifest.yml
```

This will deploy a k8s cluster with a namespace `dev-marvel` and two replicas of each service.

## Notes & Limitations

This project serves as a basic example to demonstrate a microservices architecture. Due to its simplicity, several
shortcuts and hardcoding have been implemented. In a production setting, the following improvements would be made:

1. **Testing**: Currently, no tests are available. A TDD approach would be adopted.
2. **Observability**: Logging and performance metrics would be added.
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

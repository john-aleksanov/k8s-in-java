./gradlew build

docker build -t johnsallison/resource-service:latest resource-service
docker push johnsallison/resource-service:latest

docker build -t johnsallison/song-service:latest song-service
docker push johnsallison/song-service

kubectl delete -f k8s -f resource-service/k8s -f song-service/k8s
kubectl apply -f k8s -f resource-service/k8s -f song-service/k8s

./gradlew build

docker build -t johnsallison/resource-service:0.1 resource-service
docker push johnsallison/resource-service:0.1

docker build -t johnsallison/song-service:0.1 song-service
docker push johnsallison/song-service:0.1

kubectl delete -f k8s -f resource-service/k8s -f song-service/k8s
kubectl apply -f k8s -f resource-service/k8s -f song-service/k8s

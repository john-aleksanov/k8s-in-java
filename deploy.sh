./gradlew build

docker build -t johnsallison/song-service:0.2 song-service
docker push johnsallison/song-service:0.2

kubectl apply -f k8s -f resource-service/k8s -f song-service/k8s

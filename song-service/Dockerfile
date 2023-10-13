FROM amazoncorretto:21.0.0-alpine

COPY ./build/libs/song-service.jar song-service.jar

ENTRYPOINT ["java", "-jar", "song-service.jar"]

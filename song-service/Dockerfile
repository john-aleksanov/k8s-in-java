FROM amazoncorretto:21.0.0-alpine

ENV SERVICE_NAME=song-service
COPY ./build/libs/${SERVICE_NAME}.jar ${SERVICE_NAME}.jar

ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=postgres -jar ${SERVICE_NAME}.jar"]
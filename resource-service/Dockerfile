FROM amazoncorretto:21.0.0-alpine

ENV SERVICE_NAME=resource-service

COPY ./build/libs/${SERVICE_NAME}.jar ${SERVICE_NAME}.jar

ENTRYPOINT java -jar ${SERVICE_NAME}.jar
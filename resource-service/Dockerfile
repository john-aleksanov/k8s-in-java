FROM amazoncorretto:21.0.0-alpine

ENV SERVICE_NAME=resource-service
COPY ./build/libs/${SERVICE_NAME}.jar ${SERVICE_NAME}.jar

ENTRYPOINT ["sh", "-c", "java -Dspring.config.location=/config/ \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
-jar ${SERVICE_NAME}.jar"]
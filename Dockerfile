# syntax=docker/dockerfile:1
FROM openjdk:21-jdk

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD [ "./mvnw","springboot:run" ]


FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean package -Dskiptest

FROM openjdk:21-jdk
COPY --from=build /app/demo-0.01-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
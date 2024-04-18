FROM maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -Dskiptest

FROM openjdk:21-jdk
COPY --from=build /app/demo-0.01-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
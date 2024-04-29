FROM jelastic/maven:3.9.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /target/ConStructCompany-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]



## Sử dụng OpenJDK 17 làm base image
#FROM openjdk:21-jdk
#
## Set thư mục làm việc mặc định trong container
#WORKDIR /app
#
## Copy file .jar từ thư mục target vào thư mục /app trong container
#COPY target/*.jar app.jar
#
### Expose cổng mà ứng dụng Spring Boot sẽ chạy trên
##EXPOSE 8080
#
## Lệnh chạy khi container được khởi động
#ENTRYPOINT ["java", "-jar", "app.jar"]
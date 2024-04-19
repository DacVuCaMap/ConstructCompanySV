# Sử dụng OpenJDK 17 làm base image
FROM openjdk:21-jdk

# Set thư mục làm việc mặc định trong container
WORKDIR /app

# Copy file .jar từ thư mục target vào thư mục /app trong container
COPY target/*.jar app.jar

## Expose cổng mà ứng dụng Spring Boot sẽ chạy trên
#EXPOSE 8080

# Lệnh chạy khi container được khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]
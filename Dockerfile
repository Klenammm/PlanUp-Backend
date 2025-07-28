# Use official Java 17 JDK base image
FROM eclipse-temurin:17-jdk

# Set working directory inside the container
WORKDIR /app

# Copy your JAR file into the container
COPY target/planup-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (or use Render's dynamic PORT)
EXPOSE 8080

# Start the Spring Boot app
CMD ["java", "-jar", "app.jar"]

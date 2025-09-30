# Use official Java 17 JDK image
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

# Expose any port (optional)
EXPOSE 8081

# Use Render's PORT environment variable
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]

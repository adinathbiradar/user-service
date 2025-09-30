# Use official OpenJDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set work directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/*.jar app.jar

# Expose the port your service runs on
EXPOSE 8081 

# Environment variables for Spring Boot
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

# Run the JAR
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

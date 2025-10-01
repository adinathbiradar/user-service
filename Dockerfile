# ----------------------
# Stage 1: Build the app
# ----------------------
FROM maven:3.9.3-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the JAR
RUN mvn clean package -DskipTests

# ----------------------
# Stage 2: Run the app
# ----------------------
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render will override with $PORT if needed)
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8081}"]

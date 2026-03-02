# Use a lightweight JDK 21 image as build stage
FROM eclipse-temurin:21-jdk-alpine AS build

# Set workdir
WORKDIR /app

# Copy Maven files first to leverage caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

# Download dependencies only
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests

# -----------------------------
# Run stage
# -----------------------------
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/ITHub-0.0.1-SNAPSHOT.jar app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
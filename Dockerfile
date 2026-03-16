# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (caching layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Install curl for healthcheck (optional but recommended)
RUN apk --no-cache add curl

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built jar
COPY --from=build /app/target/*.jar app.jar

# Create a volume for temporary files
VOLUME /tmp

# IMPORTANT: Do NOT EXPOSE a specific port - Render ignores this
# Do NOT set SERVER_PORT as environment variable - Render provides PORT

# Health check to help Render verify the app is running
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Run the application - Render's PORT will be used automatically via server.port=${PORT:8080}
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]
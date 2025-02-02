# Build stage
FROM gradle:7.6.1-jdk17 AS builder
WORKDIR /app

# Copy build files
COPY build.gradle settings.gradle ./

# Create the wrapper
RUN gradle wrapper

# Copy source code
COPY src/ src/

# Build the application and generate SBOM
RUN ./gradlew build cyclonedxBom --no-daemon --info

# Run stage
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
COPY --from=builder /app/build/reports/bom.json sbom.json

EXPOSE 8080
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"] 
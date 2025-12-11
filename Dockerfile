# Stage 1: Build the project using Maven and JDK 8
FROM maven:3.8.6-openjdk-8 AS build

LABEL maintainer="vadim.zubchenko@outlook.com"

# Set working directory inside the container
WORKDIR /app

# Copy only pom.xml to cache dependencies first
COPY pom.xml .

# Download Maven dependencies (faster rebuilds if code changes)
RUN mvn dependency:go-offline

# Copy the entire source code
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Run the application with lightweight JRE
FROM openjdk:8-jre-alpine

# Set working directory for runtime
WORKDIR /app

# Copy the built JAR from the first stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

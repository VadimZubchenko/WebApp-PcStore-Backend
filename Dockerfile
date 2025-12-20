# Stage 1: Build the project using Maven
FROM maven:3.9.9-eclipse-temurin-8 AS build

LABEL maintainer="vadim.zubchenko@outlook.com"

WORKDIR /app

# Copy only pom.xml first to cache dependencies
COPY pom.xml .

# Download Maven dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM maven:3.9.9-eclipse-temurin-8

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

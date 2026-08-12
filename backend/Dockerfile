FROM maven:3.8-eclipse-temurin-8 AS build
WORKDIR /app
COPY backend/pom.xml ./pom.xml
COPY frontend ../frontend
COPY backend/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:8-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

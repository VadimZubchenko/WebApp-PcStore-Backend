FROM eclipse-temurin:8-jre

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

# Use the PORT environment variable provided by Azure Web App
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]

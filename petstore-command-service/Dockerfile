# --- Build stage ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy parent pom.xml
COPY . .

FROM  maven:3.9.6-eclipse-temurin-21

WORKDIR /app
COPY ./target/petstore-command-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]

# --- Build stage ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY . .

FROM  maven:3.9.6-eclipse-temurin-21

WORKDIR /app
COPY ./target/App_api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 7090
ENTRYPOINT ["java","-jar","/app/app.jar"]

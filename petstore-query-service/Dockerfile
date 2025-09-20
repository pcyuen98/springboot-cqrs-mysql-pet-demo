# --- Build stage ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy parent pom.xml
COPY pom.xml .

# Copy each module explicitly
COPY common ./common
COPY petstore-command-service ./petstore-command-service
COPY petstore-query-service ./petstore-query-service

# Build everything so parent + common + children are installed
RUN mvn -q -DskipTests install

FROM  maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY --from=build /workspace/petstore-query-service/target/petstore-query-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]

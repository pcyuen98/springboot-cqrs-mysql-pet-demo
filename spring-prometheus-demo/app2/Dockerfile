FROM maven:3.9.9-eclipse-temurin-17
WORKDIR /app
COPY . .
RUN mvn -q -e -DskipTests package
EXPOSE 8081
CMD ["java","-jar","target/app2-0.0.1-SNAPSHOT.jar"]

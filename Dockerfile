# Build stage
FROM maven: 3.8.6-openjdk-17 AS build
WORKDIR /app
COPY ..
RUN mvn clean install -DskipTests

# Package stage
FROM eclipse-temurin: 17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/todo-api-0.0.1-SNAPSHOT.jar todo-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todo-api.jar"]

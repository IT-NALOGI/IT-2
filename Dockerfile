# Stage 1: Build the Quarkus application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /usr/src/app
COPY pom.xml .
COPY src src
RUN mvn -B -DskipTests clean package

# Stage 2: Create the final Docker image
FROM adoptopenjdk:latest
WORKDIR /usr/app
COPY --from=build /usr/src/app/target/*-runner.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

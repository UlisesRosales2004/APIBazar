#
# Build stage
#
FROM maven:3.6.0-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/APIBazar-0.0.1.jar
COPY --from=build /home/app/${JAR_FILE} APIBazar.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","APIBazar.jar"]

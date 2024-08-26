
FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/APIBazar-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} APIBazar.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","APIBazar.jar"]

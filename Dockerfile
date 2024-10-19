#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY target/APIBazar-0.0.1.jar APIBazar.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "APIBazar.jar"]
# Dockerfile content
FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} dept-service.jar
ENTRYPOINT ["java","-jar","/dept-service.jar"]
EXPOSE 8081
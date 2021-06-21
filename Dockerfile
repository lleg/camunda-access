FROM openjdk:8-jdk-alpine
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} /target/access-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/access-0.0.1-SNAPSHOT.jar"]
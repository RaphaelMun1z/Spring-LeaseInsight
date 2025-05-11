FROM maven:3.9.8-eclipse-temurin-21 AS builder

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
COPY --from=builder target/*.jar app.jar
EXPOSE 8889
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar" ]
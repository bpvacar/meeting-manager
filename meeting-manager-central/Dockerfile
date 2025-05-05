# Dockerfile

# Build con Maven oficial
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos solo pom para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y empaquetamos
COPY src src
RUN mvn package -DskipTests -B

# Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/meeting-manager-*.jar ./meeting-manager.jar
ENTRYPOINT ["java","-jar","meeting-manager.jar"]

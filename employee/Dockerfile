# 1) Build con Maven
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app
# Copia pom y baja dependencias offline
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia código y compila
COPY src ./src
RUN mvn package -DskipTests -B

# 2) Imagen final con JRE ligero
FROM openjdk:17-jdk-slim

WORKDIR /app
# Copia el JAR compilado desde la etapa anterior
COPY --from=build /app/target/manager-employee-*.jar employee-service.jar

# Expone el puerto donde corre tu servicio
EXPOSE 8081

# Arranca la aplicación
ENTRYPOINT ["java", "-jar", "employee-service.jar"]

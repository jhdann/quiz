# Etapa 1: Compilar la aplicación con Maven
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Ejecutar la aplicación con Java (Usando Temurin que sí existe)
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8133
ENTRYPOINT ["java", "-jar", "app.jar"]
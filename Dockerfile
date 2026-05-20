FROM eclipse-temurin:17-jdk-alpine
COPY "./target/spring-futbol-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE "8133"
ENTRYPOINT ["java","-jar","app.jar"]

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/food-log-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4003

ENTRYPOINT ["java", "-jar", "app.jar"]
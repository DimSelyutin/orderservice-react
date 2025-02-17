FROM eclipse-temurin:17-jre-alpine

COPY /config/target/*.jar orderservice.jar

ENTRYPOINT ["java", "-jar", "orderservice.jar"]

FROM eclipse-temurin:17-jdk as build

WORKDIR /app

COPY . .

RUN sed -i 's/\r$//' mvnw

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build app/config/target/*.jar orderservice.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "orderservice.jar"]

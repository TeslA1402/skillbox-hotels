FROM gradle:8.10.2-jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY src/ src/

RUN gradle clean build

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
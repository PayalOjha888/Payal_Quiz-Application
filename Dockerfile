FROM eclipse-temurin:22-jre-alpine

WORKDIR /app

COPY target/quiz-app.jar app.jar

EXPOSE 8091

ENTRYPOINT ["java", "-jar", "app.jar"]
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/*.jar app.jar
RUN mkdir /logs
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

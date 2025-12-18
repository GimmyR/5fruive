FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/5fruive-0.0.1-SNAPSHOT.jar app.jar
COPY appdata/images appdata/images
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
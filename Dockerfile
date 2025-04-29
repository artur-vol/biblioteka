FROM eclipse-temurin:17-jre
WORKDIR /app
COPY library-server.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "library-server.jar"]


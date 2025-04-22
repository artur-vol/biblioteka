FROM gradle:7.6-jdk17 AS build
WORKDIR /app

COPY . /app

RUN gradle clean build --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/library-server.jar .

EXPOSE 8080
ENTRYPOINT ["java","-jar","library-server.jar"]


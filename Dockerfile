FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . . 
RUN mvn install -DskipTests=true

#deploy
FROM eclipse-temurin:21-jre-alpine
WORKDIR /run
COPY --from=build /app/target/projectJavaEE-0.0.1-SNAPSHOT.jar projectJavaEE-0.0.1-SNAPSHOT.jar
EXPOSE  8080
ENTRYPOINT ["java", "-jar", "/run/projectJavaEE-0.0.1-SNAPSHOT.jar"]

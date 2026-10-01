FROM public.ecr.aws/docker/library/maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . . 
RUN mvn install -DskipTests=true

#deploy
FROM public.ecr.aws/docker/library/eclipse-temurin:21-jre-alpine
WORKDIR /run
COPY --from=build /app/target/projectJavaEE-0.0.1-SNAPSHOT.jar projectJavaEE-0.0.1-SNAPSHOT.jar
RUN ls -la /run
EXPOSE  8080
ENTRYPOINT ["java", "-jar", "/run/projectJavaEE-0.0.1-SNAPSHOT.jar"]

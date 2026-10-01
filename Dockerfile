FROM public.ecr.aws/docker/library/maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . . 
RUN mvn install -DskipTests=true

#deploy
FROM public.ecr.aws/docker/library/eclipse-temurin:21-jre-alpine
WORKDIR /run
COPY --from=build /app/target/*.jar app.jar
RUN ls -la /run
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]

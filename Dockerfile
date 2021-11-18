FROM maven:3.6.3-jdk-11-slim AS build

WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM azul/zulu-openjdk-alpine:11.0.13-jre

COPY --from=build /workspace/target/*dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

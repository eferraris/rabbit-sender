FROM maven:3.6.3-jdk-11-slim AS build

WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

ENTRYPOINT ["java","-jar","./target/rabbit-sender-v1-jar-with-dependencies.jar"]

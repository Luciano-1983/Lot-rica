FROM openjdk:24-jdk-slim
WORKDIR /app
COPY target/springbootrestapi-0.0.1-SNAPSHOT.jar /app/springbootrestapi.jar
ENTRYPOINT ["java", "-jar", "springbootrestapi.jar"]
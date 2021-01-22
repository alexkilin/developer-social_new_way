FROM openjdk:8-jdk-alpine
COPY target/*.jar platform-0.0.1-SNAPSHOT.jar
COPY src/main/resources/logback-spring.xml /deployments/
EXPOSE 5561
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "platform-0.0.1-SNAPSHOT.jar"]
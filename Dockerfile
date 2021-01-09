FROM openjdk:8-jdk-alpine
COPY target/*.jar platform-0.0.1-SNAPSHOT.jar
EXPOSE 5557
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "platform-0.0.1-SNAPSHOT.jar"]


##FROM maven:3.6.1-jdk-8-alpine
##COPY pom.xml .
##RUN mvn verify clean --fail-never
## select parent image
#FROM maven:3.6.3-jdk-8
#
## copy the source tree and the pom.xml to our new container
##COPY target/*.jar /usr/dev_social/devsocial.jar
#RUN mvn clean install -DskipTests=true
## package our application code
##RUN mvn package -DskipTests=true
#
#EXPOSE 5557
#
#WORKDIR /usr/dev_social
#
## set the startup command to execute the jar
#CMD ["java", "-Dspring.profiles.active=local", "-jar", "devsocial.jar"]
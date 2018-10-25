FROM openjdk:8-jdk-alpine
COPY target/*.jar /usr/src/app/
WORKDIR /usr/src/app/
RUN sh -c 'touch /usr/src/app/person-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker","-Xmx200m", "-jar", "/usr/src/app/person-service.jar"]
EXPOSE 8080

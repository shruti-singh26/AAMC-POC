FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD ./target/person-service.jar /app/
RUN sh -c 'touch /app/person-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker","-Xmx200m", "-jar", "/app/person-service.jar"]
EXPOSE 8080
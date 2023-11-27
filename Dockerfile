FROM openjdk:17-jdk-alpine

WORKDIR /apps

COPY target/socialmedia-server-0.0.1-SNAPSHOT.jar /app

COPY src/main/resources/application-production.yml /app

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "socialmedia-server-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=production"]
FROM openjdk:11
COPY target/googlepubsub-spring-1.0.0-SNAPSHOT.jar /app/app.jar
workdir /app
cmd ["java","-jar","app.jar"]	
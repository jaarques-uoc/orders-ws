FROM openjdk:17-alpine
VOLUME /tmp
COPY build/libs/*-SNAPSHOT.jar fat.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fat.jar"]


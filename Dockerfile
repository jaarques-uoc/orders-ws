FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/*.jar fat.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fat.jar"]

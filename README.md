# orders-ws [![Build Status](https://travis-ci.com/jaarques-uoc/orders-ws.svg?branch=master)](https://travis-ci.com/jaarques-uoc/orders-ws)

Command line tools:
* Spring boot:
    * build: `./gradlew build`
    * run: `./gradlew bootRun`
* Docker:
    * build: `docker build --tag=orders-ws .`
    * run: `docker run -p 7003:8080 -t orders-ws`
    * stop: `docker stop $(docker ps -q --filter ancestor=orders-ws)`
    * stop all containers: `docker stop $(docker ps -a -q)`

* Urls:
    * Travis CI history: https://travis-ci.com/jaarques-uoc/orders-ws/
    * Docker image: https://cloud.docker.com/repository/docker/jaarquesuoc/orders-ws
    * Heroku app health-check: https://orders-ws.herokuapp.com/actuator/health
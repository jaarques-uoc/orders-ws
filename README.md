# orders-ws [![Build Status](https://travis-ci.com/jaarques-uoc/orders-ws.svg?branch=master)](https://travis-ci.com/jaarques-uoc/orders-ws)

Command line tools:
* Mongodb:
    * Installation:
        * `brew tap mongodb/brew`
        * `brew install mongodb-community@4.0`
    * Start: `mongod --config /usr/local/etc/mongod.conf`
* Spring boot:
    * build: `./gradlew build`
    * run: `./gradlew bootRun`
* Docker:
    * build: `docker build --tag=orders-ws .`
    * run: `docker run -p 7003:7003 -t orders-ws`
    * stop: `docker stop $(docker ps -q --filter ancestor=orders-ws)`
    * stop all containers: `docker stop $(docker ps -a -q)`

Initialization endpoint:
* `curl localhost:700/init`: It removes all the exixting orders from the DB.

Monitoring urls:
* Travis CI history: https://travis-ci.com/jaarques-uoc/orders-ws/
* Docker image: https://cloud.docker.com/repository/docker/jaarquesuoc/orders-ws
* Heroku app health-check: https://orders-ws.herokuapp.com/actuator/health

# Airport Timezone Service

[![Build Status](https://travis-ci.org/gembaboo/apt-timezone.svg?branch=master)](https://travis-ci.org/gembaboo/apt-timezone)

REST service to provide and maintain timezone information for airports.

## Requirements

For building:

- JDK 8
- Maven 3
- Docker
- Google Maps Time Zone API key (available [here](https://developers.google.com/maps/documentation/timezone/start#get-a-key))


For executing:
- Docker

Note: the Docker image is used as a demo execution environment for allowing a quick start.
It lacks security hardening and perfomance tuning, therefore it should not be used for production environments.


## Running

```
mvn -Dgoogle-api-key=<<API_KEY>> clean install
docker build -t gembamboo:apt-timezone target
docker run -p 8080:8080 -p 22:22 -p 2000:2000 gembamboo:apt-timezone
```

The application runs in a multi-service container with the following processes:
- java - the main application providing the rest services
- mongodb - used as a persistent cache
- ssh - for logging in to the container

##### The SwaggerUI 
http://localhost:8080/swagger-ui.html


#####SSH access 
```ssh root@127.0.0.1``` 
Password is root.


#####JMX access
JMX access is available on port 2000.



## Tech stack

- [Spring Boot](https://projects.spring.io/spring-boot)
- [REST](https://en.wikipedia.org/wiki/Representational_state_transfer) with [HATEOAS](https://spring.io/understanding/HATEOAS)
- [MongoDB](https://www.mongodb.com/what-is-mongodb) with [spring-data-mongodb](http://projects.spring.io/spring-data-mongodb/)
- [Spring Data](https://projects.spring.io/spring-data/) (with audit)
- [Apache Camel](http://camel.apache.org) (File loading, CSV parsing with Bindy, MongoDB persisting)
- [Quartz Scheduling](http://www.quartz-scheduler.org)
- [Swagger2](http://swagger.io)
- [JMX](https://en.wikipedia.org/wiki/Java_Management_Extensions) over HTTP
- [Maven](https://maven.apache.org)
- [Docker](https://www.docker.com)
- [Supervisord](https://docs.docker.com/engine/admin/multi-service_container/)

## Useful library examples
- [Lombok](https://projectlombok.org)
- [Jolokia](https://jolokia.org)
- [Open POJO](https://github.com/oshoukry/openpojo)
- [Joda Time](http://www.joda.org/joda-time/)

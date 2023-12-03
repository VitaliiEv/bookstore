# Bookstore microservice

Sample microservice containing:
* [resource server](localhost:8080/bookstore/api/) using Spring Data REST
* [auth server](localhost:8080/auth/) using Spring Authentication Server
* [discovery](localhost:8080/discovery/) using Spring Cloud Eureka
* [gateway](localhost:8080/gateway/) using Spring Cloud Gateway

# How to run

* copy [.env.template](.env.template) to `.env` and change variables if needed
* run `./gradlew jibDockerBuild` to build docker images
* run `/usr/bin/docker compose -f bookstore.yml -p microbookstore up -d`

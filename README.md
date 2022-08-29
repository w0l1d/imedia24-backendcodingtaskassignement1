# iMedia24 Coding challenge
A product shop api.


## Feature:
- create, read, update operations on products

## Technologies used :
* #### kotlin
* #### jdk 8
* #### gradle 6.8.2
* #### spring 2.4.3

## Prerequisites
* Docker

## Setting up the spring project
* Get a Docker Image
  * to create docker image from dockerfile
    * open terminal on the root folder of the project
    * run this command :
      * `docker build --build-arg JAR_FILE=build/libs/*.jar -t docker-imedia24-walid .`
  * to get online image :
    * `docker pull w0l1d/imedia24-walid-assignment`
    * `docker tag docker-imedia24-walid w0l1d/imedia24-walid-assignment `

* Start the application on port **_8090_** :
  * `docker run -p 8090:8080 -t docker-imedia24-walid .`



### Where to find source code ?
* #### [github](https://github.com/w0l1d/shop_imedia24_walid)
### Where to find docker image ?
* #### [docker image](https://hub.docker.com/repository/docker/w0l1d/imedia24-walid-assignment)

### Swagger Documentation is avaliable on : 
* #### [localhost:8080/swagger-ui/index.html](localhost:8080/swagger-ui/index.html)

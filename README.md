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
      * `docker build --build-arg JAR_FILE=build/libs/*.jar -t imedia24-backendcodingtaskassignement1 .`
  * to get online image :
    * `docker pull sedev17/imedia24-backendcodingtaskassignement1`
    * `docker tag imedia24-backendcodingtaskassignement1 sedev17/imedia24-backendcodingtaskassignement1`

* Start the application on port **_8090_** :
  * `docker run -p 8090:8080 -t sedev17/imedia24-backendcodingtaskassignement1 .`



### Where to find docker image ?
* #### [docker image](https://hub.docker.com/r/sedev17/imedia24-backendcodingtaskassignement1)



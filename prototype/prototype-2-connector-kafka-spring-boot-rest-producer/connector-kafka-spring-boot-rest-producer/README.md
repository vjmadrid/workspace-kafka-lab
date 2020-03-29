# connector-kafka-spring-boot-rest-producer

This project represents a **connector library (dependency)** related with converting a **REST** Post request to a **Kafka** message

It has 2 focuses

* Sending messages as string
* Sending a string message in an event wrapper

This library stands out for:

* Provides **constants classes**
* Provides **basic classes** : Controller, Service, ...
* Provides **converter REST controller** to Kafka message
* Provides **converter REST controller** to event in a Kafka message -> EDA





## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)
* [Docker](https://www.docker.com/) - Container Technology
* [Apache Kafka ](https://kafka.apache.org/) 

Dependencies with architecture projects

* **architecture-testing** [0.0.1-SNAPSHOT] : Architecture library for testing used in the test environment
* **architecture-event-driven** [0.0.1-SNAPSHOT] : Architecture library for event-driven

Dependencies with architecture projects

* **spring-boot-starter-parent** [2.2.0.RELEASE] : Spring Boot + Spring Framework
* **spring-boot-starter** [X] : Spring Boot Basic core
* **spring-boot-starter-test** [X] : Spring Boot testing library
* **spring-boot-starter-web** [X] : Spring Boot web library
* **spring-boot-devtools** [X] : Spring Boot Dev tools Library
* **spring-boot-starter-actuator** [X] : Spring Boot Actuators Library
* **spring-kafka** [X] : Spring - Kafka Integration
* **spring-kafka-test** [X] : Kafka testing library with Spring Framework

* **springfox-swagger2** [2.9.2] : Swagger
* **springfox-swagger-ui** [2.9.2] : Swagger UI

Important :

* The Spring Kafka version is linked to Apache Kafka Cliente Version 
* Require align Spring Kafka to Kafka broker for connect to
* [Compatibility](https://spring.io/projects/spring-kafka#kafka-client-compatibility)





## Prerequisites

Define what elements are needed to install the software

* Java 8 installed (1.5+ version required)
* Maven installed  (3+)
* Docker installed (19+)
* Kafka infraestructure + Topics





## Installation

Steps to follow

* Start a terminal
* To be located in the PATH of installation (the place where the project is located)
* Verify that the file "pom.xml" is available

Execute the following command

```bash
mvn clean install
```

The result will be the generation of an artifact in your Maven repository (local)






## Testing

This project has tests : Unit + Integration

Execute with IDE or Maven





## Deploy

Spring Boot

* Deploy Method 1 : Application (Spring Boot File)
* Deploy Method 2 : Spring Boot Run
* Deploy Method 3 : Execute JAR



### Deploy Method 1 : Application (Spring Boot File)

1. Execute Application.java File

* Default 
* Configure Java "Run Configurations" IDE -> Use "Environment" with -Dspring.profiles.active=<id_profile>


### Deploy Method 2 : Spring Boot Run

1. Execute the following command

```bash
mvn spring-boot:run
```

Optional : use profile


### Deploy Method 3 : Execute JAR

Use Spring profiles with Maven Profiles -> Special Integration

* spring.profiles.active=@spring.profiles.active@
* enable resource filtering

Package the application in a single/fat JAR file (executable JAR + All dependencies + Embedded Servlet Container if its a web applications)

To run the jar file use the following command 

In this case define : "dev", "uat" and "prod"

1. Execute the following command

```bash
mvn package

or

mvn package -P<id_profile>
```

Execute

```bash
java -jar target/connector-kafka-spring-boot-rest-producer-0.0.1-SNAPSHOT.jar
```

Use default environment -> dev or <id_profile> environment





## Use

Custom Library


## Apache Kafka Configuration

* Installation and Configuration : Apache Kafka (Document **doc/README-installation-configuration-kafka**)

* Requires using Kafka utilities

* Create topics (Defined in spring configuration file) :

```bash
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-messages

kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-events
```


### Use Browser


**POST http://localhost:8081/kafka-connector/api/message**

Require BODY with message (String)


**POST http://localhost:8081/kafka-connector/api/event**

Require BODY with message (String)

Generate Response with :

```bash
{  
   "id":"d24472ca-ddde-46fc-85ac-dac1d7e479aa",
   "parentId":"",
   "name":"Send Message",
   "type":"CREATE",
   "author":"",
   "expirationSeconds":0,
   "payload":"Esto es una prueba",
   "createdDate":1551791424933,
   "updatedDate":null,
   "deletedDate":null
}
```





## Swagger

The service will accept HTTP GET requests at :

```bash
http://localhost:8081/kafka-connector/v2/api-docs
```

And return JSON with meta inforamtion of the API


Launching swagger UI swagger-ui.html


```bash
http://localhost:8081/kafka-connector/swagger-ui.html
```





## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning.
To see the available versions access the repository tags






## Authors

* **Víctor Madrid**

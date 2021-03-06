# connector-kafka-spring-boot-listener-consumer

This project represents a **connector library (dependency)** related with consuming Kafka messages

It has 2 focuses

* Consuming messages as string
* Consuming a string message in an event wrapper

Esta librería destaca por :

* Provides **listener classes**





## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)
* [Docker](https://www.docker.com/) - Container Technology
* [Apache Kafka ](https://kafka.apache.org/) 

Dependencies with architecture projects

N/A

Third Party Dependencies

* **spring-boot-starter-parent** [2.2.0.RELEASE] : Spring Boot + Spring Framework
* **spring-boot-starter** [X] : Spring Boot Basic core
* **spring-boot-starter-test** [X] : Spring Boot testing library
* **spring-kafka** [X] : Spring - Kafka Integration
* **spring-kafka-test** [X] : Kafka testing library with Spring Framework

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

N/A





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
java -jar target/connector-kafka-spring-boot-listener-consumer-0.0.1-SNAPSHOT.jar
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


## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning.
To see the available versions access the repository tags






## Authors

* **Víctor Madrid**

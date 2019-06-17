# demo-kafka-spring-boot-basic-with-config

This project represents a basic example of working with **Kafka** and configuration classes

Send and receive "Hello World! "+new Date() message

This projects stands out for:

* Provides **projets class** : receiver and sender
* Provides **basic configuration classes** : receiver and sender

## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Apache Kafka ](https://kafka.apache.org/) 2.11-1.1.1
* [Spring Boot](https://spring.io/projects/spring-boot) 2.0.0.RELEASE
* [Spring](https://spring.io)
 

Dependencies with architecture projects

N/A

Third Party Dependencies

* **spring-boot-starter** [2.0.0.RELEASE] : Spring Boot + Spring Framework 
* **spring-kafka** [2.1.4.RELEASE] : Spring - Kafka Integration
* **spring-boot-starter-test** [2.0.0.RELEASE] : Spring Boot testing library
* **spring-kafka-test** [2.0.0.RELEASE] : Kafka testing library with Spring Framework

## Prerequisites

Define what elements are needed to install the software

* Java 8 installed (1.5+ version required)
* Maven installed  (3+)
* Kafka infraestructure + Topics

## Apache Kafka Configuration

* Installation and Configuration : Apache Kafka (Document **doc/README-installation-configuration-kafka**)

* Create topics :

```bash
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-2
```

## Installation

Steps to follow

* Start a terminal
* To be located in the PATH of installation (the place where the project is located)
* Verify that the file "pom.xml" is available

Execute the following command

```bash
mvn clean install
```

The result will be the generation of an artifact in your maven repository

## Testing

This project has tests

## Deploy

N/A

## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning. 
To see the available versions access the repository tags

## Authors

* **Víctor Madrid**

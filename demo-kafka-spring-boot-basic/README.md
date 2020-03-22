# demo-kafka-spring-boot-basic

This project represents a basic example of working with **Kafka**

Send and receive "Hello World! "+new Date() message

This projects stands out for:

* Provides **projets class** : receiver y sender





## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Apache Kafka ](https://kafka.apache.org/)
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)

Dependencies with architecture projects

N/A

Third Party Dependencies

* **spring-boot-starter-parent** [2.2.0.RELEASE] : Spring Boot + Spring Framework 
* **spring-boot-starter** [X] : Spring Boot Basic core
* **spring-boot-starter-test** [X] : Spring Boot testing library
* **spring-kafka** [X] : Spring - Kafka Integration
* **spring-kafka-test** [X] : Kafka testing library with Spring Framework





## Prerequisites

Define what elements are needed to install the software

* Java 8 installed (1.5+ version required)
* Maven installed  (3+)
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

N/A





## Use

Custom Library

## Apache Kafka Configuration

* Installation and Configuration : Apache Kafka (Document **doc/README-installation-configuration-kafka**)

* Create topics :

```bash
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-2

```





## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning. 
To see the available versions access the repository tags





## Authors

* **Víctor Madrid**

# connector-kafka-spring-annotation

This project represents a **connector library (dependency)** related with use of special **custom Kafka annotation** and **AOP**

This library stands out for:

* Provides **@SendToKafka** annotation which allows to send the result to a topic passed by parameter
* Provides **@ReceiveToKafka** annotation que which allows to receive from a topic passed by parameter
* Provides a mechanism to differentiate when a topic is provided by identifier or by Spring Boot property
* Provides an automatic activation mechanism for all @ReceiveToKafka annotations to initially become @KafkaListeners





## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)
* [Docker](https://www.docker.com/) - Container Technology
* [Apache Kafka ](https://kafka.apache.org/) 

Dependencies with architecture projects

N/A

Dependencies with architecture projects

* **spring-aop** [5.2.0.RELEASE] : Framework Spring for AOP Integration (+ Core, ...)
* **aspectjweaver** [1.8.13] : Librería que incluye el uso de advices con ASpectj
* **spring-kafka** [2.1.4.RELEASE] : Library Spring - Kafka Integration
* **slf4j-simple** [1.7.25] : Framework Logging


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

## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning.
To see the available versions access the repository tags






## Authors

* **Víctor Madrid**

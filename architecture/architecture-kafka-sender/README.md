# architecture-kafka-sender

This project represents a **architecture library (dependency)** related with **elements common to a sender/producer** that use **Apache Kafka** to develop the different parts in a homogeneous way

This library stands out for:

* Provides **constants classes**
* Provides  **different configurations elements**
* Provides **utility classes** to facilitaty testing with certain elements : converters, callback, transformers, etc.
* Provides  a proposal of **generic producer**
* Provides  a proposal of **generic sender service** (sync and async)


## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Apache Kafka ](https://kafka.apache.org/) 2.11-1.1.1

Dependencies with architecture projects

* **architecture-kafka-testing** [1.0.0-SNAPSHOT] : Architecture library for testing used in the test environment
* **architecture-kafka-common** [1.0.0-SNAPSHOT] : Architecture library to provide global elements to projects with Kafka

Dependencies with architecture projects

N/A

## Prerequisites

Define what elements are needed to install the software

* Java 8 installed (1.5+ version required)
* Maven installed  (3+)
* Kafka infraestructure

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

Custom Library

## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning. 
To see the available versions access the repository tags

## Authors

* **Víctor Madrid**

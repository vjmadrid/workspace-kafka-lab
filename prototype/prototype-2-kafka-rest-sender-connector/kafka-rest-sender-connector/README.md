# kafka-rest-sender-connector

Este proyecto representa un tipo de **conector** a medida para **Kafka** basado en **REST**  

Este conector destaca por :

* Proporcionar una **controlador** encargado de recibir las peticiones relacionadas con Kafka
* Proporciona la funcionalidad de envio de mensajes ad-hoc en el cuerpo de la petición POST 
* Proporciona la funcionalidad de envio de mensajes dentro de un evento -> Arquitectura EDA







## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring](https://spring.io)
* [Docker](https://www.docker.com/) - Container Technology
* [Apache Kafka ](https://kafka.apache.org/) 

Dependencies with architecture projects

* **architecture-testing** [1.0.0-SNAPSHOT] : Librería de arquitectura para testing utilizada en el ámbito de test
* **architecture-event-driven** [1.0.0-SNAPSHOT] : Librería de arquitectura para proporcionar los elementos relacionados con la gestión de eventos

Third Party Dependencies

* **spring-boot-starter-parent** [2.2.0.RELEASE] : Spring Boot + Spring Framework
* **spring-boot-starter** [X] : Spring Boot Basic core
* **spring-boot-starter-test** [X] : Spring Boot testing library
* **spring-boot-starter-web** [X] : Spring Boot web library
* **spring-boot-devtools** [X] : Spring Boot Dev tools Library
* **spring-boot-starter-actuator** [X] : Spring Boot Actuators Library

* **springfox-swagger2** [2.9.2] : Swagger
* **springfox-swagger-ui** [2.9.2] : Swagger UI





## Prerequisites

Define what elements are needed to install the software

* Java 8 installed (1.5+ version required)
* Maven installed  (3+)
* Docker installed (19+)


### Configuración Apache Kafka

* Se requiere arrancar Zookeeper

zookeeper-server-start.bat ..\..\config\zookeeper.properties

* Se requiere arrancar un Broker

kafka-server-start.bat ..\..\config\server-0.properties

* Por defecto el ejemplo requiere arrancar un broker en el puerto **9090**
* La ubicación del nodo se puede moficiar en el fichero : **application.yml**

* Se requiere crear los topics :

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-messages

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-events


## Instalación

Pasos a seguir 

* Arrancar la consola
* Situarse en el PATH de instalación (el lugar donde se encuentra el proyecto)
* Verificar que se encuentra disponible el fichero "pom.xml"

Ejecutar el siguiente comando

```bash
mvn clean install
```

El resultado será la generación de un artefacto en tu repositorio maven


## Testing

Este proyecto dispone de test

Nota : utilizar todos los comandos de maven


La aplicación se puede probar desde una aplicación como POSTMAN realizando la siguiente peticion :

Importante : verificar que se cumplen todos los elementos que aparecen en la URL

**POST http://localhost:8081/kafka-connector/api/message**

Rellenando la parte de "Body" con el mensaje que se quiera (por ejemplo : "Esto es una prueba")  ya que este sera el mensaje 

**POST http://localhost:8081/kafka-connector/api/event**

Rellenando la parte de "Body" con el mensaje que se quiera (por ejemplo : "Esto es una prueba") ya que este será el atributo payload dentro de evento

Ejemplo de respuesta

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


Swagger 

http://localhost:8081/kafka-connector/v2/api-docs

http://localhost:8081/kafka-connector/swagger-ui.html


## Despliegue

Se podrán utiliar diferentes entornos de ejecución

**Entorno Development / Dev**
java -jar kafka-rest-sender-connector-1.0.0-SNAPSHOT.jar --spring.profile.active=dev


**Entorno Production / Prod**
java -jar kafka-rest-sender-connector-1.0.0-SNAPSHOT.jar --spring.profile.active=prod



## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning.
To see the available versions access the repository tags





## Authors

* **Víctor Madrid**

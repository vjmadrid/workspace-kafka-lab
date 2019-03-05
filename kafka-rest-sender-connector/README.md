# kafka-rest-sender-connector

Este proyecto representa un tipo de **conector** a medida para **Kafka** basado en **REST**  

Este conector destaca por :

* Proporcionar una **controlador** encargado de recibir las peticiones relacionadas con Kafka
* Proporciona la funcionalidad de envio de mensajes ad-hoc en el cuerpo de la petición POST 
* Proporciona la funcionalidad de envio de mensajes dentro de un evento -> Arquitectura EDA

## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de dependencias
* [Apache Kafka ](https://kafka.apache.org/) 2.11-1.1.1
* Spring Boot
 

Dependencias con proyectos de arquitectura

* **architecture-testing** [1.0.0-SNAPSHOT] : Librería de arquitectura para testing utilizada en el ámbito de test
* **architecture-event-driven** [1.0.0-SNAPSHOT] : Librería de arquitectura para proporcionar los elementos relacionados con la gestión de eventos

Dependencias terceros

* **spring-boot-starter** [2.0.0.RELEASE] : Framework Spring Boot
* **spring-kafka** [2.1.4.RELEASE] : Librería de integración Spring y Kafka
* **spring-boot-starter-test** [2.0.0.RELEASE] : Librería de testing para Spring Boot
* **spring-kafka-test** [2.0.0.RELEASE] : Librería de testing para Spring Boot


## Prerrequisitos

Se definen que elementos se necesitan para instalar el software

* Tener instalado Java 8 (Se requiere versión 1.5+)
* Tener instalado Maven (Se aconseja que sea 3+)
* Tener instalado / configurado Apache Kafka
* Creación de los topicos necesarios


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



## Despliegue

Se podrán utiliar diferentes entornos de ejecución

**Entorno Development / Dev**
java -jar kafka-rest-sender-connector-1.0.0-SNAPSHOT.jar --spring.profile.active=dev


**Entorno Production / Prod**
java -jar kafka-rest-sender-connector-1.0.0-SNAPSHOT.jar --spring.profile.active=prod



## Versionado

**Nota :** Se utiliza [SemVer](http://semver.org/) para el versionado. 
Para ver las versiones disponibles acceder a los tags del repositorio

## Autores

* **Víctor Madrid**

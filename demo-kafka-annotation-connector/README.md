# demo-kafka-annotation-connector

Este proyecto representa un ejemplo básico para trabajar con **Kafka** y el conector **kafka-annotation-connector**

Esta librería destaca por :

* Proporcionar **clases de proyecto** : lanzador de spring boot, configuración , etc
* Proporcionar **clases processor** que permite trabajar con topics


## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de dependencias
* [Apache Kafka ](https://kafka.apache.org/) 2.11-1.1.1
* Spring Boot 

Dependencias con proyectos de arquitectura

No aplica 

Dependencias terceros

* **spring-boot-starter** [2.0.0.RELEASE] : Framework Spring Boot
* **kafka-annotation-connector** [1.0.0-SNAPSHOT] : Connector para trabajar con Kafka mediante el uso de anotaciones
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

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-1

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topic-2



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

## Despliegue

No aplica


## Versionado

**Nota :** Se utiliza [SemVer](http://semver.org/) para el versionado. 
Para ver las versiones disponibles acceder a los tags del repositorio

## Autores

* **Víctor Madrid**

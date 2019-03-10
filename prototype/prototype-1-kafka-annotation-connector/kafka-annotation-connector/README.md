# kafka-annotation-connector

Este proyecto representa un tipo de **conector** a medida para **Kafka** basado en **anotaciones** y **AOP**

Este conector destaca por :

* Proporcionar una anotación **@SendToKafka** que permite enviar el resultado a un topic pasado por parámetro
* Proporcionar una anotación **@ReceiveToKafka** que permite recibir de un topic pasado por parámetro
* Proporciona un mecanismo para diferenciar cuando se facilita un topic por identificador o bien por propiedad de Spring Boot
* Proporciona un mecanismo de activación automatica de todas las anotaciones @ReceiveToKafka para convertirse inicialmente en @KafkaListeners

## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de dependencias
* [Apache Kafka ](https://kafka.apache.org/) 2.11-1.1.1
 

Dependencias con proyectos de arquitectura

No aplica 

Dependencias terceros

* **spring-aop** [5.0.4.RELEAS] : Framework Spring con la integración para AOP
* **aspectjweaver** [1.8.13] : Librería que incluye el uso de advices con ASpectj
* **spring-kafka** [2.1.4.RELEASE] : Librería de integración Spring y Kafka
* **slf4j-simple** [1.7.25] : Librería de log simple


## Prerrequisitos

Se definen que elementos se necesitan para instalar el software

* Tener instalado Java 8 (Se requiere versión 1.5+)
* Tener instalado Maven (Se aconseja que sea 3+)

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

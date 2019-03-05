# architecture-common

Este proyecto representa a una **librería de arquitectura** relacionada con **elementos comunes a cualquier proyecto** para desarrollar las diferentes partes de forma homogenea

Esta librería destaca por :

* Proporcionar **clases de constantes globales**
* Proporcionar **clases de utilidades** que facilitarán el trabajo con diferentes elementos : conversores, transformadores, etc.
* Proporcionar una **excepción generica** "AcmeException" para disponer de un tipo de excepción diferenciadora en el proyecto (el resto de excepciones deberían de heredar de ella)
* Proporciona un **componente abstracto de generación de entidades o modelos** 


## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de dependencias

Dependencias con proyectos de arquitectura

* **architecture-testing** [1.0.0-SNAPSHOT] : Librería de arquitectura para testing utilizada en el ámbito de test

Dependencias terceros

* **commons-lang3** [3.8.1] : Framework de utilidades para las clases
* **slf4j-api** [1.7.25] : Framework para la definición de loggin
* **log4j-over-slf4j** [1.10.19] : Implementación de log4j
* **logback** [1.2.3] : Framework de logging


## Prerrequisitos

Se definen que elementos se necesitan para instalar el software

* Tener instalado Java 8 (Se requiere versión 1.5+)
* Tener instalado Maven (Se aconseja que sea 3+)


### Instalación

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

# demo-java-kafka-debezium-boot

Este proyecto representa un tipo de **conector** a medida para **Kafka** basado en el patrón **CDC** y el uso de **connectores de Kafka**, para ello se encarga de atacar al fichero "Commit Log" de la base de datos MySQL    

Este conector destaca por :

* Proporcionar una **controlador** encargado de recibir las peticiones relacionadas con el CRUD de Customer sobre la base de datos MySQL
* Proporcionar una **controlador** encargado de registrar el conecctor contra la base de datos
* Proporciona un listener que estará a la escucha de todo lo recibido por Kafka y que tendra que ver con las acciones ejecutadas en la base de datos

## Stack Tecnológico

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de dependencias
* [Apache Kafka ](https://kafka.apache.org/)
* Spring Boot
* Debezium
* MySQL
 

Dependencias terceros

* **spring-boot-starter** [2.0.0.RELEASE] : Framework Spring Boot
* **spring-boot-starter-test** [2.0.0.RELEASE] : Librería de testing para Spring Boot
* **spring-boot-starter-data-jpa** [2.0.0.RELEASE] : Librería de persistencia con implementación en JPA para Spring Boot
* **spring-boot-starter-web** [2.0.0.RELEASE] : Librería de trabajo para aplicaciones web para Spring Boot
* **spring-boot-starter-actuator** [2.0.0.RELEASE] : Librería de actuadores para Spring Boot
* **spring-kafka** [2.1.4.RELEASE] : Librería de integración Spring y Kafka
* **spring-kafka-test** [2.1.4.RELEASE] : Librería de testing para Spring Boot
* **mysql-connector-java** [5.1.45.RELEASE] : Librería de conexion a java con mysql
* **springfox-swagger2** [2.9.2] : Librería para el uso de Swagger
* **commons-lang3** [3.7] : Librería de utilidades para clases


## Prerrequisitos

Se definen que elementos se necesitan para instalar el software

* Tener instalado Java 8 (Se requiere versión 1.5+)
* Tener instalado Maven (Se aconseja que sea 3+)
* Tener instalado / configurado Apache Kafka
* Creación de los topicos necesarios


## Ejecución del proyecto

### 1. Arranque de la infraestructura basada en contenedores docker

``` bash
$ cd docker/debezium
$ docker-compose up -d
```

### 2. Arranque de la aplicación SpringBoot

``` bash
$ mvn clean install spring-boot:run
```

### 3. Registro del KafkaConnector

Accedemos a la API de la Aplicación Web y ejecución del método POST '/api/v1/kafka-connector/register'

- http://localhost:8080/kafka-cdc/swagger-ui.html

### 4. Test de la aplicación

#### 4.a. Test con la consola de mysql del contenedor 'mysql':

*Nota: Login/Password de acceso a consola mysql: 'mysqluser' / 'mysqlpw'*

``` bash
$ docker exec -it mysql /bin/bash
> mysql -u mysqluser -p
mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| inventory          |
+--------------------+
2 rows in set (0.00 sec)

mysql> INSERT INTO inventory.customers (first_name, last_name, email) VALUES ('User1', 'User1LastName', 'user1@acme.org');
```

#### 4.b. Test con la API RESTful de la Aplicación Web:

- Accedemos a la URL: http://localhost:8080/cdc/swagger-ui.html
- Ejecutamos cualquier método de REST de modificación de datos en la consola de Swagger (por ejemplo, POST /api/v1/customer)

#### 4.c. Acceso al actuator de SpringBoot

- http://localhost:8080/cdc/actuator


### 5. Parada de la infraestructura basada en contenedores docker

``` bash
$ cd docker/debezium
$ docker-compose down -v --rmi all
```

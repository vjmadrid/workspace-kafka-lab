# prototype-3-kafka-debezium-connector

This prototype represents an example of projects/modules to work with **Debezium**.

It stands out for :

* XXXX

## Technological Stack

* Java 8
* [Maven 3](https://maven.apache.org/) - Gestión de dependencias
* Spring Boot
* Docker
* Zookeeper + Apache Kafka
* MySql
* Kafka-Connect
 
Dependencies with architecture projects

N/A

Dependencies with third parties libraries

Depends of the project/module


## Prerequisites

Define what elements are needed to install the software

* Java 8 installed
* Maven installed (3+ version required)
* Docker installed


## Installation

Step to follow

### Start Zookeper

Start a new terminal

Start the container with the next command : 

```bash
docker run -it --rm --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 debezium/zookeeper:0.9
```

Parameters :

* -it : Mode interactive for you can see going on the container
* --rm : Renove the container when it stopped
* --name : Name of the container
* -p : ports

Important : Zookeeper is listenning on port 2181

Verify in the terminal de correct start

```bash
INFO  [main:NIOServerCnxnFactory@89] - binding to port 0.0.0.0/0.0.0.0:2181
```

### Start Kafka

Note : Similar to say start one broker in Kafka Platform

Start a new terminal

Start the container with the next command : 

```bash
docker run -it --rm --name kafka -p 9092:9092 --link zookeeper:zookeeper debezium/kafka:0.9
```

Parameters :

* -it : Mode interactive for you can see going on the container
* --rm : Renove the container when it stopped
* --name : Name of the container
* -p : ports
* --link : warns where to find another element

> Important : Debeziumk warning
>
>If we wanted to connect to Kafka from outside of a Docker container, then we’d want Kafka to advertise its address via the Docker host, which we could do by adding -e ADVERTISED_HOST_NAME= followed by the IP address or resolvable hostname of the Docker host, which on Linux or Docker on Mac this is the IP address of the host computer (not localhost).

Important : Kafka is listening on port 9092

zookeeper.connect = 172.17.0.2:2181
PLAINTEXT://172.17.0.3:9092

Verify in the terminal de correct start

```bash
INFO  [main:Logging@66] - [KafkaServer id=1] started
```

### Start MySQL

Start a new terminal

Start the container with the next command : 

```bash
docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw debezium/example-mysql:0.9
```

Parameters :

* -it : Mode interactive for you can see going on the container
* --rm : Renove the container when it stopped
* --name : Name of the container
* -p : ports
* -e : create environment variables

Important : MySQL is listening on port 3306

Verify in the terminal de correct start

```bash
Version: '5.7.25-log'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server (GPL)
```


### Start Kafka Connect

Start a new terminal

Start the container with the next command : 

```bash
docker run -it --rm --name connect -p 8083:8083 -e GROUP_ID=1 -e CONFIG_STORAGE_TOPIC=my_connect_configs -e OFFSET_STORAGE_TOPIC=my_connect_offsets -e STATUS_STORAGE_TOPIC=my_connect_statuses --link zookeeper:zookeeper --link kafka:kafka --link mysql:mysql debezium/connect:0.9
```

Parameters :

* -it : Mode interactive for you can see going on the container
* --rm : Renove the container when it stopped
* --name : Name of the container
* -p : ports
* -e : create environment variables

Important : Kafka Connect is listening on port 8083

Verify in the terminal de correct start

```bash
Finished starting connectors and tasks   [org.apache.kafka.connect.runtime.distributed.DistributedHerder]
```

Execute verify de rest API is alive :  localhost:8083

Verify the result 

For example : 

```bash
{
    "version": "2.1.1",
    "commit": "21234bee31165527",
    "kafka_cluster_id": "XbR8XMTmQs6QJ0p-fjVF0Q"
}
```

Execute verify de rest API connectors is readly :  localhost:8083/connectors/

Verify the result 

* Show the list of connectors actives

For example : 

```bash
[]
```

#### Load Connector

Execute verify de rest API connectors is readly : POST  localhost:8083/connectors/


```bash
{
  "name": "inventory-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": "1",
    "database.hostname": "mysql",
    "database.port": "3306",
    "database.user": "debezium",
    "database.password": "dbz",
    "database.server.id": "184054",
    "database.server.name": "dbserver1",
    "database.whitelist": "inventory",
    "database.history.kafka.bootstrap.servers": "kafka:9092",
    "database.history.kafka.topic": "schema-changes.inventory"
  }
}
```
172.17.0.5

http://172.17.0.5:8083/

docker run -it --rm --name kafka-watcher --link zookeeper:zookeeper debezium/kafka:0.9 watch-topic -a -k dbserver1.inventory.customers

## Testing

Depends of the project/module

## Deploy

N/A

## Versioning

**Note :** [SemVer](http://semver.org/) is used for the versioning. 
To see the available versions access the repository tags

## Authors

* **Víctor Madrid**

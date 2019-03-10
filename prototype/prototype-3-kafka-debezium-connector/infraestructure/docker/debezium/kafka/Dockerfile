FROM debezium/kafka:0.5

# jmx-export
ENV PROMETHEUS_PORT=7072

# kafka-manager
ENV JMX_PORT=9999

USER root

RUN mkdir $KAFKA_HOME/jmx-exporter
COPY ./jmx-exporter/jmx_prometheus_javaagent-0.6.jar $KAFKA_HOME/jmx-exporter/jmx_prometheus_javaagent-0.6.jar
COPY ./jmx-exporter/kafka-0-8-2.yml $KAFKA_HOME/jmx-exporter/kafka-0-8-2.yml
RUN chown -R kafka $KAFKA_HOME/jmx-exporter && \
    chgrp -R kafka $KAFKA_HOME/jmx-exporter

# jmx-export
ENV KAFKA_OPTS -javaagent:jmx-exporter/jmx_prometheus_javaagent-0.6.jar=$PROMETHEUS_PORT:jmx-exporter/kafka-0-8-2.yml

# kafka-manager
ENV KAFKA_JMX_OPTS -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=zookeeper -Djava.net.preferIPv4Stack=true

RUN env

# jmx-export
EXPOSE ${PROMETHEUS_PORT}

# kafka-manager
EXPOSE ${JMX_PORT}

package com.acme.kafka.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.acme.kafka.demo.constant.DemoKafkaAnnotationConfigConstant;

@Configuration
@ComponentScan(basePackages = { DemoKafkaAnnotationConfigConstant.GENERIC_PACKAGE })
public class DemoKafkaAnnotationConfig {

}

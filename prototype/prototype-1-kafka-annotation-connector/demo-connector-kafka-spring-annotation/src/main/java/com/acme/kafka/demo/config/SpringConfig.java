package com.acme.kafka.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acme.kafka.demo.constant.DemoKafkaAnnotationConfigConstant;

@Configuration
@ComponentScan(basePackages = { DemoKafkaAnnotationConfigConstant.GENERIC_PACKAGE })
public class SpringConfig {

}

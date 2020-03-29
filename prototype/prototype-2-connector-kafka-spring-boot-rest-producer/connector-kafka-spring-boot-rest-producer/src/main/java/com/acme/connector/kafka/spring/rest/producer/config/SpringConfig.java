package com.acme.connector.kafka.spring.rest.producer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acme.connector.kafka.spring.rest.producer.constant.SpringConfigConstant;

@Configuration
@ComponentScan(basePackages = { SpringConfigConstant.BASE_PACKAGE })
public class SpringConfig {

}

package com.acme.architecture.kafka.sender.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acme.architecture.kafka.sender.constant.SpringKafkaSenderConfigConstant;

@Configuration
@ComponentScan(basePackages = { SpringKafkaSenderConfigConstant.BASE_PACKAGE })
public class SpringConfig {

}

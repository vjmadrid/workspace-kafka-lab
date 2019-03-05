package com.acme.kafka.connector.rest.sender.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acme.kafka.connector.rest.sender.constant.SpringConfigConstant;

@Configuration
@ComponentScan(basePackages = { SpringConfigConstant.BASE_PACKAGE })
public class SpringConfig {

}

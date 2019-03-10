package com.acme.kafka.connector.listener.receiver.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.acme.kafka.connector.listener.receiver.constant.SpringConfigConstant;

@Configuration
@ComponentScan(basePackages = { SpringConfigConstant.BASE_PACKAGE })
public class SpringConfig {

}

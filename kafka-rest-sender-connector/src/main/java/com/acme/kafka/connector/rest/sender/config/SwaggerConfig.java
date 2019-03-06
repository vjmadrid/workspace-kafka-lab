package com.acme.kafka.connector.rest.sender.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acme.kafka.connector.rest.sender.constant.SpringConfigConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //Enable swagger 2.0 spec
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {
		 return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(getApiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage(SpringConfigConstant.BASE_PACKAGE))
	                .paths(PathSelectors.ant("/api/*"))
	                .build();
	}

	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Kafka REST Sender API")
				.description("Kafka REST Sender Connector")
				.license("Acme License")
				.version("1.0").build();
	}

}
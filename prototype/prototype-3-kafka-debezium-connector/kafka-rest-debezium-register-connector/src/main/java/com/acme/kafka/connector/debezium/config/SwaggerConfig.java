package com.acme.kafka.connector.debezium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.acme.kafka.connector.debezium.constant.SpringConfigConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
        	.select()                                  
        	.apis(RequestHandlerSelectors.basePackage(SpringConfigConstant.BASE_PACKAGE))              
        	.paths(PathSelectors.any())
        	.build()
        	.apiInfo(getApiInfo());
    }
    
    private ApiInfo getApiInfo() {
    	return new ApiInfoBuilder()
        	.title("Inventory REST API")
        	.description("Inventory REST API based on Debezium (CDC")
        	.build();
    }
   
}
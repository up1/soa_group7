package com.shenzhentagram;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
@ComponentScan({"com.shenzhentagram.controller", "com.shenzhentagram.config"})
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public Docket authenticateAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("0.0.1-GATEWAY")
				.apiInfo(apiInfo())
				.select()
					.apis(RequestHandlerSelectors.any())
					.paths(apiPaths())
				.build()
				.pathMapping("/")
				.useDefaultResponseMessages(false);
	}

	private Predicate<String> apiPaths() {
		return or(
				regex("/auth.*"),
				regex("/users.*"),
				regex("/posts.*"),
				regex("/notifications.*")
		);
	}

	private ApiInfo apiInfo() {
		// TODO Add version by getting from pom.xml
		return new ApiInfoBuilder()
				.title("Shenzhentagram API GATEWAY")
				.description("Shenzhentagram API GATEWAY, Spring boot REST-client service")
				.build();
	}



}

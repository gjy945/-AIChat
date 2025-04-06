package com.example.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Swagger文档，只有在测试环境才会使用
 */
@Configuration
public class SwaggerConfiguration{

	@Bean
	public GroupedOpenApi baseRestApi() {
		return GroupedOpenApi.builder()
				.group("接口文档")
				.packagesToScan("com.example").build();
	}


	@Bean
	public OpenAPI springShopOpenApi() {
		return new OpenAPI()
				.info(new Info().title("接口文档")
						.description("接口文档，openapi3.0 接口，用于前端对接")
						.version("v0.0.1")
						.license(new License().name("").url("")));
	}
}

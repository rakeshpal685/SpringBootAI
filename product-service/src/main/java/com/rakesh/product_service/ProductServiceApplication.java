package com.rakesh.product_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // Enables this application to be a Eureka client
@OpenAPIDefinition(
		info = @Info(
				title = "Product Service API",
				version = "1.0",
				description = "API documentation for the Product Service"
		)
)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")//This is for automatically updating the table in the column createdBy and updatedBy
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

//This is for automatically updating the table in the column createdBy and updatedBy with the current logged in user
@Bean
public AuditorAware<String> auditorProvider() {
    return () -> Optional.of("system"); // Replace with user from SecurityContext in real use
}
	

}

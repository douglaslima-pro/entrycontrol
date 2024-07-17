package edu.douglaslima.entrycontrol.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Component
public class SwaggerConfig {
	
	public SecurityScheme createAPISecurityScheme() {
		return new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement()
						.addList("Bearer Authentication"))
				.components(new Components()
						.addSecuritySchemes("Bearer Authentication", createAPISecurityScheme()))
				.info(new Info()
						.title("EntryControl")
						.description("EntryControl é uma aplicação backend de gerenciamento de usuários.")
						.version("1.0")
						.termsOfService("Termo de uso: Open Source")
						.contact(new Contact()
								.name("Douglas Souza de Lima")
								.email("douglaslima-pro@outlook.com")
								.url("https://github.com/douglaslima-pro/entrycontrol")));
	}
}

package edu.douglaslima.entrycontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeansFactory {
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}

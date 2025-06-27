package com.tekworks.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@CrossOrigin	
public class WebConfig {
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") 
	            .allowedOrigins("http://localhost:3000","http://localhost:3001") 
	            .allowedMethods("GET", "POST", "PUT", "DELETE")
	            .allowedHeaders("*")
	            .allowCredentials(true);
			}
			
		};
		
	}

}

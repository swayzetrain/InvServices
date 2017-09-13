package com.swayzetrain.inventory.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@ComponentScan({"com.swayzetrain.inventory"})
@EntityScan("com.swayzetrain.inventory")
@EnableJpaRepositories("com.swayzetrain.inventory")
public class Init extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Init.class, args);
	}
	
	@Override 
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		return application.sources(Init.class); 	
		
	}
	
	 @Bean
	 public CommonsRequestLoggingFilter requestLoggingFilter() {
		 
		 CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
	     loggingFilter.setIncludeClientInfo(true);
	     loggingFilter.setIncludeQueryString(true);
	     
	     return loggingFilter;
	     
	 }
	
	
}
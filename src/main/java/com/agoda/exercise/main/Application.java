package com.agoda.exercise.main;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.agoda.exercise.interceptor.APIKeyValidationInterceptor;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.agoda" })
public class Application extends WebMvcConfigurerAdapter {

	@Autowired
	APIKeyValidationInterceptor apiKeyValidationInterceptor;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiKeyValidationInterceptor).addPathPatterns("/**");
	}

	@Bean
	public APIKeyValidationInterceptor getAPIKeyValidationInterceptor() {
		return new APIKeyValidationInterceptor();
	}

}

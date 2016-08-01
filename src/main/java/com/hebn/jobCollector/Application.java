package com.hebn.jobCollector;

import com.hebn.jobCollector.infrastructure.config.WebInitializer;
import com.hebn.jobCollector.infrastructure.integration.StackoverflowJobRssFeedInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

@Import({WebInitializer.class, StackoverflowJobRssFeedInitializer.class})
@Configuration
@EnableAutoConfiguration
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class).run(args);
		log.info("Spring Boot Started - profile : {} , {}",
				Arrays.toString(context.getEnvironment().getDefaultProfiles()),
				Arrays.toString(context.getEnvironment().getActiveProfiles()));
	}


}

package com.hebn.jobCollector.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by coupang on 2016. 8. 1..
 */
@Import(PersistenceInitializer.class)
@Configuration
@ComponentScan("com.hebn.jobCollector.application")
public class ServiceInitializer {

}

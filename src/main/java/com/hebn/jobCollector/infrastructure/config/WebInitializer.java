package com.hebn.jobCollector.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by coupang on 2016. 8. 1..
 */
@Import(ServletInitializer.class)  // TODO - add SecurityInitializer
@Configuration
public class WebInitializer {

}

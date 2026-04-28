package dev.sorokin.school.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("dev.sorokin.school")
@PropertySource("classpath:application.properties")
public class AppConfig {
}


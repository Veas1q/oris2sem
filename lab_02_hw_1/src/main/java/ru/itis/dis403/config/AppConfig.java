package ru.itis.dis403.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("ru.itis.dis403")
@EnableWebMvc
public class AppConfig {

}

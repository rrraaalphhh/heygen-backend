package com.example.heygen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // or "/api/**"
                        .allowedOrigins("https://voluble-dusk-c75766.netlify.app")
                        .allowedMethods("*") // GET, POST, etc.
                        .allowedHeaders("*");
            }
        };
    }
}

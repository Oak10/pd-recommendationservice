package com.ex.moviesstorageservice.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //@CrossOrigin(exposedHeaders = {HttpHeaders.CONTENT_DISPOSITION}, value = "*", allowedHeaders = {"accountid-header"})
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowedOrigins("*")
                .exposedHeaders("*");
    }
}

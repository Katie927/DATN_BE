package com.DATN.Bej.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")  // URL pattern
                .addResourceLocations("file:D:/Spring/newVuePr/pimg/");
    }
}

//                .addResourceLocations("file:D:/Spring/newVuePr/BEJ/src/main/resources/static/images/");  // Local folder
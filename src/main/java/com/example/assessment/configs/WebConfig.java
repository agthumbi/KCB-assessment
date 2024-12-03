package com.example.assessment.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Update allowed origins as per your requirements
                .allowedMethods("POST", "OPTIONS","GET")
                .allowedHeaders("Authorization", "Content-Type", "Accept");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                response.addHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
                response.addHeader("X-XSS-Protection", "1; mode=block");
                response.addHeader("Content-Security-Policy", "default-src 'self'; img-src 'https://*'; child-src 'none';");
                response.addHeader("X-Content-Type-Options", "nosniff");
                response.addHeader("Access-Control-Allow-Origin", "*");
                return true;
            }
        });
    }
}


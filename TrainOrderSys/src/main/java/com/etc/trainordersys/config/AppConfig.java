package com.etc.trainordersys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home/login.html").setViewName("/home/login");
        registry.addViewController("/home/login").setViewName("/home/login");
        registry.addViewController("/home/index.html").setViewName("/home/index");
        registry.addViewController("/admin/index.html").setViewName("/admin/system/index");
        registry.addViewController("/admin/login.html").setViewName("/admin/system/login");
        registry.addViewController("/home/register.html").setViewName("/home/register");
    }
}

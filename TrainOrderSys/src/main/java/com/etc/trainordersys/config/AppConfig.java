package com.etc.trainordersys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home/login.html").setViewName("/home/login");
        registry.addViewController("/home/login").setViewName("/home/login");
        registry.addViewController("/home/index.html").setViewName("/home/index");
        registry.addViewController("/index").setViewName("/home/index");
        registry.addViewController("/admin/index.html").setViewName("/admin/system/index");
        registry.addViewController("/admin/login.html").setViewName("/admin/system/login");
        registry.addViewController("/home/register.html").setViewName("/home/register");
        registry.addViewController("/header.html").setViewName("/admin/common/header");
        registry.addViewController("/footer.html").setViewName("/admin/common/footer");
        registry.addViewController("/home/header").setViewName("/home/header");
        registry.addViewController("/home/footer").setViewName("/home/footer");
        registry.addViewController("/header-menu.html").setViewName("/admin/common/header-menu");
        registry.addViewController("/icons.html").setViewName("/admin/common/icons");
        registry.addViewController("/left-menu.html").setViewName("/admin/common/left-menu");
        registry.addViewController("/third-menu.html").setViewName("/admin/common/third-menu");
        registry.addViewController("face_login.html").setViewName("/home/face_login");
        registry.addViewController("face_regist.html").setViewName("/home/face_regist");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //addResourceHandler("/upload/**"): url请求路径，自定义的/upload/**
        //.addResourceLocations("C:\\Users\\31315\\Desktop\\upload\\"):图片存储的真实路径
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:C:\\Users\\Lenovo\\Desktop\\upload\\");
    }
}

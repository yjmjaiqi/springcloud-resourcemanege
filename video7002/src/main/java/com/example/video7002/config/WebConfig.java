package com.example.video7002.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 访问路径：http://localhost:7001/videos/cj_142207194912440320.png
         * "/avatar/**" 为前端URL访问路径
         * "file:" + uploadPath 是本地磁盘映射
         */
        registry.addResourceHandler("/videos/**").addResourceLocations("file:" + uploadPath);
    }

}
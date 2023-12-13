package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.JwtInterceptor;

// 註冊一個攔截器
public class WebConfig implements WebMvcConfigurer{

    @Autowired
    JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/**") // 攔截所有路徑
            .excludePathPatterns("/user/login", "/user/register"); // 排除登入和註冊
	}
}

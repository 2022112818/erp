package com.erp.common.config;

import com.erp.common.interceptor.GlobalInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GlobalInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/api/**")  // 拦截所有api请求
                .excludePathPatterns(
                        "/api/auth/login",       // 排除登录接口
                        "/swagger-ui/**",        // 排除swagger文档
                        "/v3/api-docs/**"        // 排除swagger文档
                );
    }
}


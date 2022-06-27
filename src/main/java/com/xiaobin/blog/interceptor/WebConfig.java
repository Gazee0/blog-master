package com.xiaobin.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截/admin下所有请求访问，必须登录后才可以访问
        String[] addPatterns={
                "/admin/**"
        };
        //要排除的路径，排除的路径不被拦截
        String[] excutePathPatterns={
                "/admin/login","/admin"
        };
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        //添加拦截器，添加拦截路径与排除路径

        registry.addInterceptor(loginInterceptor).addPathPatterns(addPatterns).excludePathPatterns(excutePathPatterns);
    }
}

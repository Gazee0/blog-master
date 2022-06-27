package com.xiaobin.blog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //未登录
        if (request.getSession().getAttribute("user")==null){

            System.out.println("拦截器执行");
            response.sendRedirect("/admin");
            return  false;
        }
        return true;
    }
}

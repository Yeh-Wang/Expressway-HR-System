package org.yeh.wang.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yeh.wang.interceptor.LoginInterceptor;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        //所有路径都被拦截
//        registration.addPathPatterns("/**");
        //添加不拦截路径
        registration.excludePathPatterns(
                "/**/login",
                "/**/register",
                "/**/get-role-auth",
                "/**",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css"
        );
    }
}

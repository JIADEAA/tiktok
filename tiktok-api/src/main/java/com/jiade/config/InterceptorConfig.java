package com.jiade.config;

import com.jiade.interceptor.CorsInterceptor;
import com.jiade.interceptor.PassportInterceptor;
import com.jiade.interceptor.UserTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: JIADE
 * @description: InterceptorConfig
 * @date: 2023/4/7 10:04
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private UserTokenInterceptor userTokenInterceptor;

    @Autowired
    private CorsInterceptor corsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(passportInterceptor)
                .addPathPatterns("/passport/getSMSCode");

        registry.addInterceptor(userTokenInterceptor)
                .addPathPatterns("/userInfo/modifyUserInfo")
                .addPathPatterns("/userInfo/modifyImage");
    }


}

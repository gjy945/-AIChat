package com.example.security.common.config;

import cn.hutool.core.util.ArrayUtil;
import com.example.security.common.adapter.AuthConfigAdapter;
import com.example.security.common.adapter.DefaultAuthConfigAdapter;
import com.example.security.common.filter.AuthFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 授权配置
 */
@Configuration
@EnableMethodSecurity
public class AuthConfig {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    @ConditionalOnMissingBean
    public AuthConfigAdapter authConfigAdapter() {
        return new DefaultAuthConfigAdapter();
    }


    @Bean
    @Lazy
    public FilterRegistrationBean<AuthFilter> filterRegistration(AuthConfigAdapter authConfigAdapter) {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        // 添加过滤器
        registration.setFilter(authFilter);
        // 设置过滤路径，/*所有路径
        registration.addUrlPatterns(ArrayUtil.toArray(authConfigAdapter.pathPatterns(), String.class));
        registration.setName("authFilter");
        // 设置优先级
        registration.setOrder(0);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }

}

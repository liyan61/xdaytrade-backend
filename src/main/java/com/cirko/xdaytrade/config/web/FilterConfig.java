package com.cirko.xdaytrade.config.web;

import com.cirko.xdaytrade.modules.cors.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CorsFilter> registration() {
        CorsFilter corsFilter = new CorsFilter();
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>(corsFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}

package com.cirko.xdaytrade.config.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class SecurityWhitelistHandler {
    // 默认白名单接口, 允许对于网站静态资源的无授权访问
    static final String[] DEFAULT_WHITE_LIST = {
        "/",
        "/*.html",
        "/favicon.ico",
        "/**/*.html",
        "/**/*.css",
        "/**/*.js",
    };

    // 允许无授权访问的 API
    static final String[] API_WHITE_LIST = {
        "/**/auth/login"
    };

    public void handle(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(DEFAULT_WHITE_LIST).permitAll()
                .antMatchers(API_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and();
    }
}

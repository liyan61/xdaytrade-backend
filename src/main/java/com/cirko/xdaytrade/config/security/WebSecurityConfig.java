package com.cirko.xdaytrade.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.cirko.xdaytrade.modules.auth.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier(value = "jwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置 UserDetailsService
                .userDetailsService(this.userDetailsService)
                // 使用 BCrypt 进行密码的 hash
                .passwordEncoder(passwordEncoder());
    }

    @Autowired
    private SecurityWhitelistHandler securityWhitelistHandler;
    @Autowired
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;

    // 装载 BCrypt 密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * 重写 authenticationManager 方法，解决 authenticationManager 无法注入的问题
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 由于使用的是 jwt，不需要 csrf
        httpSecurity.csrf().disable();
        // 基于 token，不需要 session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        // 除了白名单里的接口，其他接口都需要鉴权认证
        securityWhitelistHandler.handle(httpSecurity);
        // 禁用默认登陆页面
        httpSecurity.formLogin().disable();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加 jwt filter
        httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        // 登陆失败，返回 json
        httpSecurity.exceptionHandling().authenticationEntryPoint(authenticationEntryPointHandler);
    }
}

package com.cirko.xdaytrade.config.security;

import com.cirko.xdaytrade.helpers.response.CodeMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();

        if (e instanceof InsufficientAuthenticationException) {
            out.write(mapper.writeValueAsString(CodeMsg.NOT_LOGGED_IN));
        } else {
            out.write(mapper.writeValueAsString(CodeMsg.NO_PERMISSION));
        }

        out.flush();
        out.close();
    }
}

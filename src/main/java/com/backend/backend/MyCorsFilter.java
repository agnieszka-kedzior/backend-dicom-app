package com.backend.backend;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyCorsFilter extends CorsFilter {

    public MyCorsFilter(CorsConfigurationSource source) {
        super((CorsConfigurationSource) source);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        response.addHeader("Access-Control-Allow-Headers", "*");

        //if (response.getHeader("Access-Control-Allow-Origin") == null)
        //  response.addHeader("Access-Control-Allow-Origin", "*");
        filterChain.doFilter(request, response);
    }

}

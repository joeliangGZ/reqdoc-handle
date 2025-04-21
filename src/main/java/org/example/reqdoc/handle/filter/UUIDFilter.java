package org.example.reqdoc.handle.filter;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@WebFilter(urlPatterns = "/*")

public class UUIDFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uuid = UUID.randomUUID().toString();
        MDC.put("UUID",uuid);
        try{
            chain.doFilter(request,response);
        }finally {
            MDC.remove("UUID");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

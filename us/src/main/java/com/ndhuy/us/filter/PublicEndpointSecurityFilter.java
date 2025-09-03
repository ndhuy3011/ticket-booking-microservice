package com.ndhuy.us.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.ndhuy.us.annotation.PublicEndpoint;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PublicEndpointSecurityFilter extends OncePerRequestFilter {

    private final HandlerMappingIntrospector introspector;

    public PublicEndpointSecurityFilter(HandlerMappingIntrospector introspector) {
        this.introspector = introspector;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var matchableHandlerMapping = introspector.getMatchableHandlerMapping(request);
            if (matchableHandlerMapping != null) {
                HandlerExecutionChain handler = matchableHandlerMapping.getHandler(request);
                if (handler != null && handler.getHandler() instanceof HandlerMethod handlerMethod &&
                        (handlerMethod.hasMethodAnnotation(PublicEndpoint.class) ||
                        handlerMethod.getBeanType().isAnnotationPresent(PublicEndpoint.class))) {
                    
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        } catch (Exception ignored) {
            // Intentionally ignored: if handler mapping fails, proceed with normal filter chain
        }
        filterChain.doFilter(request, response);
    }
}

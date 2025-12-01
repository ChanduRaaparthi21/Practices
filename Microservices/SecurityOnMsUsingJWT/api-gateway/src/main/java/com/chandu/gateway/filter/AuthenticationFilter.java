package com.chandu.gateway.filter;

import com.chandu.gateway.util.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidate routeValidate;

    @Autowired
    private JwtUtilService jwtUtilService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            var request = exchange.getRequest();   // FIX: No casting

            if (routeValidate.isSecured.test(request)) {

                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                } else {
                    throw new RuntimeException("Invalid Authorization Header");
                }

                try {
                    jwtUtilService.validateToken(authHeader);
                } catch (Exception e) {
                    throw new RuntimeException("Unauthorized Access");
                }
            }

            return chain.filter(exchange);
        };
    }


    public static class Config {
    }
}
package com.chandu.gateway.filter;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidate {

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/auth/validate",
            "/auth/authenticate",
            "/eureka"
    );

    public Predicate<org.springframework.http.server.reactive.ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().startsWith(uri));

}

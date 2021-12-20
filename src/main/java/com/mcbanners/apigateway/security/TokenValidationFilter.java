package com.mcbanners.apigateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenValidationFilter extends AbstractGatewayFilterFactory<Void> {
    private final JwtHandler jwtHandler;
    private final AntPathMatcher pathMatcher;

    @Autowired
    public TokenValidationFilter(JwtHandler jwtHandler) {
        super(Void.class);

        this.jwtHandler = jwtHandler;
        this.pathMatcher = new AntPathMatcher();
    }

    @Override
    public GatewayFilter apply(Void config) {
        return (exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();

            // if it's a preflight, let it through
            if (req.getMethod() == HttpMethod.OPTIONS) {
                System.out.println("Letting preflight pass through filter!");
                return chain.filter(exchange);
            }

            String path = req.getPath().pathWithinApplication().value();

            for (String antPath : ProtectedRoute.getAntPaths()) {
                System.out.printf("Token validation checking path %s against %s%n", path, antPath);

                if (pathMatcher.match(antPath, path)) {
                    // if the accessed path is protected, then we'll validate the token
                    return validateToken(exchange, chain);
                }
            }

            System.out.println("Did not pass request through token validation filter. Passing through.");
            // otherwise, we'll just let it through
            return chain.filter(exchange);
        };
    }

    private Mono<Void> validateToken(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse res = exchange.getResponse();

        String header = exchange.getRequest().getHeaders().getFirst(jwtHandler.getHeader());
        if (header == null || !header.startsWith(jwtHandler.getPrefix())) {
            res.setStatusCode(HttpStatus.BAD_REQUEST);
            return res.setComplete();
        }

        try {
            // If the token successfully parses, Gateway says good enough. Banner API can decode it downstream to extract
            // more information.
            jwtHandler.parse(header.replace(jwtHandler.getPrefix(), ""));
        } catch (Exception ignored) {
            res.setStatusCode(HttpStatus.UNAUTHORIZED);
            return res.setComplete();
        }

        return chain.filter(exchange);
    }
}

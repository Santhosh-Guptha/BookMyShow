package com.gateway.filter;

import com.gateway.jwtUtil.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

	@Autowired
	private RouteValidator routeValidator;

	@Autowired
	private JwtUtil jwtUtil;

	public AuthorizationFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest modifiedReq = null;
			if (routeValidator.isSecured.test(exchange.getRequest())) {
				String REQUEST_URI = exchange.getRequest().getURI().toString();
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Authorization is missing");
				}
				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}
				try {
					jwtUtil.validateToken(authHeader);
					ServerHttpRequest request = exchange.getRequest();
					String co_relationId = request.getHeaders().getFirst("Co-relationID");
					if(co_relationId == null) {
						modifiedReq = request.mutate().header("Co-relationID", UUID.randomUUID().toString()).build();
						co_relationId = modifiedReq.getHeaders().getFirst("Co-relationID");
						exchange = exchange.mutate().request(modifiedReq).build();
					}
					return chain.filter(exchange);
				} catch (Exception exception) {
					throw new RuntimeException(exception.getMessage());
				}
			}
			return chain.filter(exchange);
		});
	}

}
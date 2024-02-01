package cn.caber.gateway.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(-1)
public class LogWebFilter implements WebFilter {


    @Autowired
    private ServerCodecConfigurer codecConfigurer;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return chain.filter(decorate(exchange));

    }

    public ServerWebExchange decorate(ServerWebExchange exchange) {
        ServerHttpResponse serverHttpResponse = new DecorateServerHttpResponse(exchange, codecConfigurer.getReaders());
        return exchange.mutate().request(exchange.getRequest()).response(serverHttpResponse).build();

    }
}

package cn.caber.gateway.filter;

import cn.caber.gateway.constant.GatewayConstant;
import cn.hutool.core.lang.ObjectId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
@Slf4j
@Order(10)
public class TranceFilter implements GlobalFilter {



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String tranceId = generateTraceId();
        if (exchange.getRequest().getHeaders().containsKey(GatewayConstant.TRACE_ID)) {
            String reqTranceId = exchange.getRequest().getHeaders().getFirst(GatewayConstant.TRACE_ID);
            if (StringUtils.isNotBlank(reqTranceId)) {
                tranceId = reqTranceId;
            }
        }
        setTraceId(tranceId);
        log.info("tranceId植入,tranceId:{}", tranceId);
        return chain.filter(exchange);
    }

    private static String generateTraceId() {
        return ObjectId.next();
    }


    public static void setTraceId(String traceId) {
        MDC.put(GatewayConstant.TRACE_ID, traceId);
    }

    public static void clearTraceId() {
        MDC.clear();
    }
}




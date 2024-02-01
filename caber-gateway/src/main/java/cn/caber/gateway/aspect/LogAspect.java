package cn.caber.gateway.aspect;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

//@Aspect
@Slf4j
public class LogAspect {

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    @Pointcut("execution(* org.springframework.web.reactive.DispatcherHandler.handle(..))")
    public void buried() {
    }


    @Around("execution(* org.springframework.web.reactive.DispatcherHandler.handle(..)) && args(exchange)")
    public Object pointAdvice(ProceedingJoinPoint joinPoint, ServerWebExchange exchange) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable ex = null;
        try {
            result = joinPoint.proceed();
            log.info("执行结果：", result);
            return result;
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            doLog(exchange, startTime, endTime);
        }
    }

    public void doLog(ServerWebExchange exchange, Long startTime, Long endTime) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_PATTERN);

        String method = exchange.getRequest().getMethodValue();
        String path = exchange.getRequest().getPath().value();
        AtomicReference<String> requestBody = new AtomicReference<>("");
        Flux<DataBuffer> body = exchange.getRequest().getBody();
        //body.subscribe(dataBuffer -> requestBody.set(dataBuffer.toString()));
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        DecorateServerHttpResponse originalResponse = (DecorateServerHttpResponse) exchange.getResponse();
        String responseBody = originalResponse.getBody();

        BuriedPointVO buriedPointVO = BuriedPointVO.builder()
                .requestParams(queryParams)
                .requestBody(requestBody.get())
                .requestBody(responseBody)
                .startTime(new TimeVO(startTime, formatTime(startTime, dateTimeFormat)))
                .endTime(new TimeVO(endTime, formatTime(endTime, dateTimeFormat)))
                .method(method)
                .requestURI(path).build();
        log.info("埋点日志：{}", JSON.toJSONString(buriedPointVO));
    }

    @Data
    @Builder
    private static class BuriedPointVO {
        private TimeVO startTime;
        private TimeVO endTime;
        private String requestURI;
        private String timeConsumed;
        private String method;
        private MultiValueMap<String, String> requestParams;
        private String requestBody;
        private String response;
        private String exceptionStack;
        private String clientIp;
        private String token;
    }

    @Data
    @AllArgsConstructor
    private static class TimeVO {
        private final long timestamp;
        private final String formatTime;
    }

    private String formatTime(long timestamp, SimpleDateFormat dateTimeFormat) {
        return dateTimeFormat.format(new Date(timestamp));
    }


}

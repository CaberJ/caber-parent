package cn.caber.gateway.aspect;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by shenhongxi on 2021/4/29.
 */
@Slf4j
public class DecorateServerHttpResponse extends ServerHttpResponseDecorator {

    private final List<HttpMessageReader<?>> messageReaders;

    private String body;

    public DecorateServerHttpResponse(ServerWebExchange exchange,
                                      List<HttpMessageReader<?>> messageReaders) {
        super(exchange.getResponse());
        this.messageReaders = messageReaders;
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        // 这里只是借用 ClientResponse 这个类获取修改之前的 body
        // server 端最终返回的是 ServerResponse/ServerHttpResponse
        ClientResponse clientResponse = prepareClientResponse(body);
        Mono<DataBuffer> modifiedBody = clientResponse.bodyToMono(byte[].class)
                .map(originalBody -> {
                    this.body = new String(originalBody, StandardCharsets.UTF_8);
                    log.info("body:{}", this.body);
                    return this.body.getBytes(StandardCharsets.UTF_8);
                }).map(encrypted -> getDelegate().bufferFactory().wrap(encrypted));
        return super.writeWith(modifiedBody);
    }


    private ClientResponse prepareClientResponse(Publisher<? extends DataBuffer> body) {
        ClientResponse.Builder builder = ClientResponse.create(HttpStatus.OK, messageReaders);
        return builder.body(Flux.from(body)).build();
    }

    public String getBody(){
        return body;
    }
}

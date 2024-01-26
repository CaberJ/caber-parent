package cn.caber.caberorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@FeignClient("goods")
public interface GoodsClient {

    @RequestMapping("/goods/getGoods")
    Mono<String> getGoods();
}

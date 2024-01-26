package cn.caber.cabergoods.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodController {

    @RequestMapping("/getGoods")
    public Mono<String> getGoods() {
        log.info("请求：getGoods");
        return Mono.just("goods");
    }

}

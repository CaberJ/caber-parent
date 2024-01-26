package cn.caber.caberorder.controller;

import cn.caber.caberorder.client.GoodsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private GoodsClient goodsClient;


    @GetMapping("/getOrder")
    public Mono<String> getOrder() {
        log.info("请求：getOrder");
        return goodsClient.getGoods()
                .flatMap(goods -> {
                    // 处理获取到的商品信息
                    // 这里假设将商品信息组装成订单信息并返回
                    String order = "Your order: " + goods;
                    return Mono.just(order);
                });
        //return goods.map(g -> g.concat(",order"));
      /*  goods.subscribe(
                value -> {
                    // 处理获取到的真实值
                    System.out.println("Got goods: " + value);
                },
                error -> {
                    // 处理错误情况
                    System.err.println("Error occurred: " + error);
                },
                () -> {
                    // 处理完成信号
                    System.out.println("Completed!");
                }
        );*/

    }


}

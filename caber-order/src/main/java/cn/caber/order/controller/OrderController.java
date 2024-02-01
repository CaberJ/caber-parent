package cn.caber.order.controller;

import cn.caber.feign.goods.client.GoodsClient;
import cn.caber.feign.goods.entity.Goods;
import cn.caber.feign.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private GoodsClient goodsClient;

    @PostMapping("/getOrder")
    public String getOrder(@RequestBody Order order) {
        log.info("请求：getOrder");
        goodsClient.testThread(1111L);
        return goodsClient.getGoods(111L, new Goods());

    }


}

package cn.caber.feign.order.client;

import cn.caber.feign.order.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("order")

public interface OrderClient {

    @PostMapping("/getOrder")
    String getOrder(@RequestBody Order order);
}

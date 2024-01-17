package cn.caber.cabergoods.controller;

import cn.caber.cabergoods.client.OrderClient;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodController {

    @Autowired
    private OrderClient client;

    @RequestMapping("/getGoods")
    public String getGoods() {
        log.info("请求：getGoods");
        String traceId = MDC.get("traceId");
        log.info("traceId：{}", traceId);
        String goods = client.getOrder();
        return goods;
    }

}

package cn.caber.caberorder.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    @GetMapping("/getOrder")
    public String getOrder() {
        log.info("请求：getOrder");
        return "order";
    }


}

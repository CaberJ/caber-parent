package cn.caber.cabergoods.controller;

import cn.caber.commons.thread.CaberTransmittableRunnable;
import cn.caber.feign.goods.entity.Goods;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadPoolExecutor;

@RestController
@Slf4j
public class GoodController {
    @Autowired
    private GoodsCategoryController goodsCategoryController;

    @Autowired
    @Qualifier("caberThreadPoolExecutor")
    private ThreadPoolExecutor threadPoolExecutor;

    @PostMapping("/getGoods")
    public String getGoods(@RequestParam("id") Long id, @RequestBody Goods goods) {
        String traceId = MDC.get("traceId");
        String spanId = MDC.get("spanId");
        log.info("请求：getGoods: id:{}, goods:{}, traceId,{}, spanId:{}", id, goods, traceId, spanId);
        goodsCategoryController.getGoodsCategoryFirst();
        log.info("请求：getGoods, 请求:getGoodsCategoryFirst1, id:{}, goods:{}, traceId,{}, spanId:{}", id, goods, traceId, spanId);
        goodsCategoryController.getGoodsCategoryFirst();
        log.info("请求：getGoods, 请求:getGoodsCategoryFirst2, id:{}, goods:{}, traceId,{}, spanId:{}", id, goods, traceId, spanId);
        return "goods";
    }


    @GetMapping("/testThread")
    public String testThread(@RequestParam("id") Long id) {
        log.info("请求：testThread: id:{}", id);
        goodsCategoryController.getGoodsCategoryFirst();
        log.info("请求：getGoodsCategoryFirst: id:{}", id);
        CaberTransmittableRunnable runnable = new CaberTransmittableRunnable() {
            @Override
            public void doRun() {
                String traceId = MDC.get("traceId");
                String spanId = MDC.get("spanId");
                log.info("子线程内请求：testThread: id:{},  traceId,{}, spanId:{}", id, traceId, spanId);
                goodsCategoryController.getGoodsCategoryFirst();
                log.info("子线程内请求：testThread, 请求:getGoodsCategoryFirst1, id:{}, traceId,{}, spanId:{}", id, traceId, spanId);
            }
        };
        for (int i = 0; i < 1; i++) {
            threadPoolExecutor.submit(runnable);
        }
        log.info("子线程请求结束");
        return "goods";
    }

}

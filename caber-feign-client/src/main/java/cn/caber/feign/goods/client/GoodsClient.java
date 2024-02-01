package cn.caber.feign.goods.client;

import cn.caber.feign.goods.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("goods")
public interface GoodsClient {

    @RequestMapping("/goods/getGoods")
    String getGoods(@RequestParam("id") Long id, @RequestBody Goods goods);

    @RequestMapping("/goods/testThread")
    String testThread(@RequestParam("id") Long id);
}

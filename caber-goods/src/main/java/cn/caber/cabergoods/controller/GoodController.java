package cn.caber.cabergoods.controller;

import cn.caber.cabergoods.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/goods")
public class GoodController {

    @Autowired
    private OrderClient client;

    @RequestMapping("/getGoods")
    public String getGoods() {
        String goods = client.getOrder();
        return goods;
    }


}

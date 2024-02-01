package cn.caber.cabergoods.controller;

import cn.caber.feign.goods.entity.GoodsCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class GoodsCategoryController {

    //@NewSpan
    @GetMapping("/getGoodsCategory")
    public List<GoodsCategory> getGoodsCategory(){
        log.info("getGoodsCategory");
        GoodsCategory food = new GoodsCategory();
        food.setCategoryName("食品");
        food.setId(111L);
        GoodsCategory appliance = new GoodsCategory();
        appliance.setCategoryName("电器");
        appliance.setId(222L);
        ArrayList<GoodsCategory> goodsCategories = new ArrayList<>();
        goodsCategories.add(food);
        goodsCategories.add(appliance);
        goodsCategories.add(getGoodsCategoryFirst());
        return goodsCategories;
    }


    //@NewSpan
    @GetMapping("/getGoodsCategory/first")
    public GoodsCategory getGoodsCategoryFirst(){
        log.info("getGoodsCategory/first");
        GoodsCategory food = new GoodsCategory();
        food.setCategoryName("食品");
        food.setId(1111L);
        return food;
    }
}

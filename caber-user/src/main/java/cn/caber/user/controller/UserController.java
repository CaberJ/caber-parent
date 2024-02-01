package cn.caber.user.controller;

import cn.caber.commons.mq.rocketmq.RocketMqClient;
import cn.caber.feign.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private RocketMqClient rocketMqClient;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/getUser")
    public User getUser(@RequestParam("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setAge(12);
        user.setName("libai");
        return user;
    }

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam("msg") String msg) {
        rocketMQTemplate.asyncSend("TestTopic",msg,null);
        return "发送成功";
    }


}

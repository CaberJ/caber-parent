package cn.caber.user.controller;

import cn.caber.feign.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @GetMapping("/getUser")
    public User getUser(@RequestParam("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setAge(12);
        user.setName("libai");
        return user;
    }
}

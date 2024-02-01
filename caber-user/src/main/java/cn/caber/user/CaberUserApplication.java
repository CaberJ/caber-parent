package cn.caber.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.caber.feign.goods","cn.caber.feign.order"})
public class CaberUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaberUserApplication.class, args);
    }
}

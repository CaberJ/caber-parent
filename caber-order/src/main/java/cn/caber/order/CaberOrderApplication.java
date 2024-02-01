package cn.caber.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"cn.caber.feign.goods"})
public class CaberOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaberOrderApplication.class, args);
	}

}

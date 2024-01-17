package cn.caber.cabergoods;

import cn.caber.cabergoods.log.TestLogFilter;
import cn.caber.commons.buried.filter.DefaultLogFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CaberGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaberGoodsApplication.class, args);
	}


}

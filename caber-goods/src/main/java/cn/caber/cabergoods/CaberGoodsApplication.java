package cn.caber.cabergoods;

import cn.caber.commons.buried.log.BuriedPointAop;
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
		ConfigurableApplicationContext run = SpringApplication.run(CaberGoodsApplication.class, args);
		BuriedPointAop bean = run.getBean(BuriedPointAop.class);
		bean.show();
	}


}

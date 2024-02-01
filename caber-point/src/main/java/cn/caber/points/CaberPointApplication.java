package cn.caber.points;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CaberPointApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaberPointApplication.class, args);
    }
}

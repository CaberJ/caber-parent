package cn.caber.gateway.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfiguration {

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public LogWebFilter logWebFilter() {
        return new LogWebFilter();
    }

}

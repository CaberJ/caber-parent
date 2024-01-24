package cn.caber.commons.config;

import cn.caber.commons.interceptor.CaberRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new CaberRequestInterceptor();
    }


}

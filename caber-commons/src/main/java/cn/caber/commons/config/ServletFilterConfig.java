package cn.caber.commons.config;

import cn.caber.commons.filter.CaberRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletFilterConfig {

    @Bean
    public CaberRequestFilter caberRequestFilter() {
        return new CaberRequestFilter();
    }
}

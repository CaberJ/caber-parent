package cn.caber.commons.buried.config;

import cn.caber.commons.buried.filter.DefaultLogFilter;
import cn.caber.commons.buried.filter.LogFilter;
import cn.caber.commons.buried.utils.SpringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuriedConfiguration {

    @Bean
    public SpringUtils springUtils() {
        return new SpringUtils();
    }
    @Bean
    public LogFilter logFilter() {
        return new DefaultLogFilter();
    }

    @Bean
    public DoLogConfig doLogConfig() {
        return new DoLogConfig();
    }


}

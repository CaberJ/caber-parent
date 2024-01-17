package cn.caber.commons.buried.config;

import cn.caber.commons.buried.filter.DefaultLogFilter;
import cn.caber.commons.buried.filter.LogFilter;
import cn.caber.commons.buried.log.DefaultPrinter;
import cn.caber.commons.buried.log.Printer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuriedConfiguration {

    @Bean
    public LogFilter logFilter() {
        return new DefaultLogFilter();
    }

    @Bean
    public Printer printer() {
        return new DefaultPrinter();
    }

    @Bean
    public DoLogConfig doLogConfig() {
        return new DoLogConfig();
    }
}

package cn.caber.commons.buried.config;

import cn.caber.commons.buried.filter.DefaultParamLogFilter;
import cn.caber.commons.buried.filter.DefaultResultLogFilter;
import cn.caber.commons.buried.filter.ParamLogFilter;
import cn.caber.commons.buried.filter.ResultLogFilter;
import cn.caber.commons.buried.log.BuriedPointAop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BuriedConfiguration {

    @Autowired(required = false)
    private List<ParamLogFilter> paramLogFilterList;

    @Autowired(required = false)
    private List<ResultLogFilter> resultLogFilterList;

    @Bean
    public ParamLogFilter defaultParamLogFilter() {
        return new DefaultParamLogFilter();
    }

    @Bean
    public ResultLogFilter defaultResultLogFilter() {
        return new DefaultResultLogFilter();
    }


    @Bean
    public BuriedPointAop doLogConfig(List<ParamLogFilter> paramLogFilterList, List<ResultLogFilter> resultLogFilterList) {
        return new BuriedPointAop(paramLogFilterList, resultLogFilterList);
    }


}

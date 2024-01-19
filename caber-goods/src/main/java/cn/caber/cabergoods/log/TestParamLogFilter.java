package cn.caber.cabergoods.log;

import cn.caber.commons.buried.filter.ParamLogFilter;
import org.springframework.stereotype.Component;

@Component
public class TestParamLogFilter implements ParamLogFilter {

    @Override
    public Object filter(Object args) {
        if (canPrint(args)) {
            return args;
        }
        return null;
    }


    public Boolean canPrint(Object arg) {
        if ((arg instanceof String) && "aaa".equals(arg)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

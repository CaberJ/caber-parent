package cn.caber.cabergoods.log;

import cn.caber.commons.buried.filter.LogFilter;
import org.springframework.stereotype.Component;

@Component
public class TestLogFilter implements LogFilter {
    @Override
    public Boolean needPrint(Object arg) {
        if ((arg instanceof String) && "aaa".equals(arg)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

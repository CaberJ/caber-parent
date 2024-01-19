package cn.caber.cabergoods.log;

import cn.caber.commons.buried.filter.ResultLogFilter;
import org.springframework.stereotype.Component;

@Component
public class TestResultLogFilter implements ResultLogFilter {
    @Override
    public Object filter(Object arg) {
        return null;
    }


    public Boolean canPrint(Object arg) {
        if ((arg instanceof String) && "aaa".equals(arg)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

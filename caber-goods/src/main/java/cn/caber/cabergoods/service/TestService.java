package cn.caber.cabergoods.service;

import cn.caber.cabergoods.log.TestParamLogFilter;
import cn.caber.cabergoods.po.Param;
import cn.caber.commons.buried.log.BuriedPoint;
import org.springframework.stereotype.Service;

@Service
public class TestService  {

    @BuriedPoint(paramLogFilter = TestParamLogFilter.class)
    public Param doService(String name, Param param) {
        param.setName(name);
        return param;
    }
}

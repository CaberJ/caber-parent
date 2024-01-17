package cn.caber.cabergoods.service;

import cn.caber.cabergoods.log.TestLogFilter;
import cn.caber.cabergoods.po.Param;
import cn.caber.commons.buried.log.DoLog;
import org.springframework.stereotype.Service;

@Service
public class TestService  {

    @DoLog(paramLogFilter = TestLogFilter.class)
    public Param doService(String name, Param param) {
        param.setName(name);
        return param;
    }
}

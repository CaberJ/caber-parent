package cn.caber.commons.buried.filter;

import java.util.ArrayList;
import java.util.List;

public interface ParamLogFilter {


    default List<Object> filterAll(Object[] args) {
        ArrayList<Object> argList = new ArrayList<>();
        if (args.length == 0) {
            return argList;
        }
        for (Object obj : args) {
            argList.add(filter(obj));
        }
        return argList;
    }

    Object filter(Object arg);

}

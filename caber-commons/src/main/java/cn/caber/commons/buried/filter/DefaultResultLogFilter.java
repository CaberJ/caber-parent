package cn.caber.commons.buried.filter;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class DefaultResultLogFilter implements ResultLogFilter {

    private static final List<Class> EXCEPT_CLASS = Arrays.asList(MultipartFile.class);

    @Override
    public Object filter(Object arg) {
        if (canPrint(arg)) {
            return arg;
        }
        return null;
    }

    private Boolean canPrint(Object arg) {
        for (Class exceptClass : EXCEPT_CLASS) {
            if (exceptClass.isInstance(arg)) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}

package cn.caber.commons.buried.filter;


import org.springframework.web.multipart.MultipartFile;

public class DefaultLogFilter implements LogFilter {
    @Override
    public Boolean needPrint(Object arg) {
        if(arg instanceof MultipartFile){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

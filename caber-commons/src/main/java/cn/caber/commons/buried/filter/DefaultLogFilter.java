package cn.caber.commons.buried.filter;


public class DefaultLogFilter implements LogFilter {
    @Override
    public Boolean needPrint() {
        return Boolean.TRUE;
    }
}

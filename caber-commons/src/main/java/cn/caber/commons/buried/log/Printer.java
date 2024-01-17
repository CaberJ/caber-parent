package cn.caber.commons.buried.log;

public interface Printer {

    String print(Class<?> type, Object value);

    String print(Object value);


}

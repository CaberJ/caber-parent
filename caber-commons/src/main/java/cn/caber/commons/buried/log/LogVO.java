package cn.caber.commons.buried.log;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class LogVO implements Serializable {

    private static final long serialVersionUID = -2733821646796366197L;

    private String stack;
    private Long duration;
    private List<Object> args;
    private Object result;
    private Throwable ex;



}

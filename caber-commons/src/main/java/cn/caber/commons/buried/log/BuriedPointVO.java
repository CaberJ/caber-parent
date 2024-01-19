package cn.caber.commons.buried.log;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class BuriedPointVO implements Serializable {

    private static final long serialVersionUID = -2733821646796366197L;

    private Long startTime;
    private Long endTime;
    private String method;
    private Long timeConsumed;
    private List<Object> request;
    private Object response;
    private String ex;


}

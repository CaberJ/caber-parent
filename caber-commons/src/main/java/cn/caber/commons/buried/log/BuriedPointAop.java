package cn.caber.commons.buried.log;

import cn.caber.commons.buried.filter.DefaultParamLogFilter;
import cn.caber.commons.buried.filter.DefaultResultLogFilter;
import cn.caber.commons.buried.filter.ParamLogFilter;
import cn.caber.commons.buried.filter.ResultLogFilter;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Aspect
@Slf4j
public class BuriedPointAop {

    private final Map<Class<? extends ParamLogFilter>, ParamLogFilter> LOG_PARAM_FILTER_CACHE;
    private final Map<Class<? extends ResultLogFilter>, ResultLogFilter> LOG_RESULT_FILTER_CACHE;

    public BuriedPointAop(List<ParamLogFilter> paramLogFilters, List<ResultLogFilter> resultLogFilter) {
        HashMap<Class<? extends ParamLogFilter>, ParamLogFilter> currentParamMap = new HashMap<>();
        for (ParamLogFilter filter : paramLogFilters) {
            currentParamMap.put(filter.getClass(), filter);
        }
        LOG_PARAM_FILTER_CACHE = currentParamMap;

        HashMap<Class<? extends ResultLogFilter>, ResultLogFilter> currentResultMap = new HashMap<>();
        for (ResultLogFilter filter : resultLogFilter) {
            currentResultMap.put(filter.getClass(), filter);
        }
        LOG_RESULT_FILTER_CACHE = currentResultMap;
    }

    @Pointcut("@annotation(cn.caber.commons.buried.log.BuriedPoint)")
    public void annCut() {
    }

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controllerCut() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerCut() {
    }

    @Around(value = " annCut() && @annotation(buriedPoint) ")
    public Object annoDoLog(ProceedingJoinPoint joinPoint, BuriedPoint buriedPoint) throws Throwable{
        Class<? extends ParamLogFilter> paramFilterClass = buriedPoint.paramLogFilter();
        Class<? extends ResultLogFilter> resultFilterClass = buriedPoint.resultLogFilter();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> clazz = signature.getDeclaringType();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable ex = null;
        BuriedPointVO buriedPointVO;
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            buriedPointVO = BuriedPointVO.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .method(clazz.getName() + "#" + methodName)
                    .timeConsumed(endTime - startTime)
                    .request(filterParam(args, paramFilterClass))
                    .response(filterResult(result, resultFilterClass))
                    .build();
            if(Objects.nonNull(ex)){
                buriedPointVO.setEx(ExceptionUtils.getStackTrace(ex));
            }
            log.info("切点是注解");
            log.info(JSON.toJSONString(buriedPointVO));
        }

    }

    @Around(value = "controllerCut() || restControllerCut()")
    public Object controllerDoLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class<?> clazz = signature.getDeclaringType();
        String methodName = signature.getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        BuriedPoint annotation = method.getAnnotation(BuriedPoint.class);
        if (Objects.nonNull(annotation)) {
            return joinPoint.proceed();
        }
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable ex = null;
        BuriedPointVO buriedPointVO;
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            buriedPointVO = BuriedPointVO.builder()
                    .startTime(startTime)
                    .endTime(endTime)
                    .method(clazz.getName() + "#" + methodName)
                    .timeConsumed(endTime - startTime)
                    .request(filterParam(args, DefaultParamLogFilter.class))
                    .response(filterResult(result, DefaultResultLogFilter.class))
                    .build();
            if(Objects.nonNull(ex)){
                buriedPointVO.setEx(ExceptionUtils.getStackTrace(ex));
            }
            log.info("切点是controller");
            log.info(JSON.toJSONString(buriedPointVO));

        }
    }


    private Object filterResult(Object result, Class<? extends ResultLogFilter> filterClass) {
        ResultLogFilter currFilter = LOG_RESULT_FILTER_CACHE.get(filterClass);
        if (Objects.isNull(result)) {
            return result;
        }
        return currFilter.filter(result);
    }

    private List<Object> filterParam(Object[] oriArgs, Class<? extends ParamLogFilter> filterClass) {
        ParamLogFilter currFilter = LOG_PARAM_FILTER_CACHE.get(filterClass);
        if (oriArgs.length == 0) {
            return new ArrayList<>(0);
        }
        List<Object> printArgList = currFilter.filterAll(oriArgs);
        return printArgList;
    }

    public void show(){
        log.info(LOG_PARAM_FILTER_CACHE.toString());
        log.info(LOG_RESULT_FILTER_CACHE.toString());
    }


}

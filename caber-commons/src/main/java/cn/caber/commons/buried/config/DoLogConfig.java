package cn.caber.commons.buried.config;

import cn.caber.commons.buried.filter.DefaultLogFilter;
import cn.caber.commons.buried.filter.LogFilter;
import cn.caber.commons.buried.log.DoLog;
import cn.caber.commons.buried.log.LogVO;
import cn.caber.commons.buried.utils.SpringUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Aspect
@Slf4j
public class DoLogConfig {


    private LogFilter logFilter = (LogFilter) SpringUtils.getBeanByClass(DefaultLogFilter.class);

    @Pointcut("@annotation(cn.caber.commons.buried.log.DoLog)")
    public void annCut() {
    }

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void controllerCut() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void RestControllerCut() {
    }

    @Around(value = " annCut() && @annotation(doLog) ")
    public Object annoDoLog(ProceedingJoinPoint joinPoint, DoLog doLog) {
        Class<? extends LogFilter> paramFilterClass = doLog.paramLogFilter();
        Class<? extends LogFilter> resultFilterClass = doLog.resultLogFilter();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable ex = null;
        LogVO logVO;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logVO = LogVO.builder()
                    .stack(clazz.getName() + "#" + methodName)
                    .duration(endTime - startTime)
                    .args(filterArgs(args, paramFilterClass))
                    .result(filterResult(result, resultFilterClass))
                    .ex(ex)
                    .build();
            log.info("切点是注解");
            log.info(JSON.toJSONString(logVO));
            return result;
        }

    }

    @Around(value = "controllerCut() || RestControllerCut()")
    public Object controllerDoLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Class clazz = signature.getDeclaringType();
        String methodName = signature.getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        DoLog annotation = method.getAnnotation(DoLog.class);
        if (Objects.nonNull(annotation)) {
            return joinPoint.proceed();
        }
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object result = null;
        Throwable ex = null;
        LogVO logVO;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            ex = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logVO = LogVO.builder()
                    .stack(clazz.getName() + "#" + methodName)
                    .duration(endTime - startTime)
                    .args(filterArgs(args, null))
                    .result(filterResult(result, null))
                    .ex(ex)
                    .build();
            log.info("切点是controller");
            log.info(JSON.toJSONString(logVO));
            return result;
        }
    }


    private Object filterResult(Object result, Class<? extends LogFilter> filterClass) {
        ArrayList<LogFilter> logFilters = new ArrayList<>();
        logFilters.add(logFilter);
        if (Objects.nonNull(filterClass)) {
            LogFilter logFilter = (LogFilter) SpringUtils.getBeanByClass(filterClass);
            logFilters.add(logFilter);
        }
        if (Objects.isNull(result)) {
            return result;
        }
        if (canPrint(result, logFilters)) {
            return result;
        }
        return null;

    }

    private List<Object> filterArgs(Object[] oriArgs, Class<? extends LogFilter> filterClass) {
        ArrayList<LogFilter> logFilters = new ArrayList<>();
        logFilters.add(logFilter);
        if (Objects.nonNull(filterClass)) {
            LogFilter logFilter = (LogFilter) SpringUtils.getBeanByClass(filterClass);
            logFilters.add(logFilter);
        }
        List<Object> args = new ArrayList<>();
        if (oriArgs.length == 0) {
            return args;
        }
        for (Object oriArg : oriArgs) {
            if (canPrint(oriArg, logFilters)) {
                args.add(oriArg);
            }
        }
        return args;
    }

    private boolean canPrint(Object arg, List<LogFilter> logFilters) {
        for (LogFilter logFilter : logFilters) {
            if (!logFilter.needPrint(arg)) {
                return false;
            }
        }
        return true;
    }

}

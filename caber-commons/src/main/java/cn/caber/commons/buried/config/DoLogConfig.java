package cn.caber.commons.buried.config;

import cn.caber.commons.buried.filter.LogFilter;
import cn.caber.commons.buried.log.DoLog;
import cn.caber.commons.buried.log.Printer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class DoLogConfig {

    @Autowired
    private Printer printer;

    @Pointcut("@annotation(cn.caber.commons.buried.log.DoLog)")
    public void doLogCut() {
    }

    @Around(value = "doLogCut() && @annotation(doLog)")
    public void doLog(ProceedingJoinPoint joinPoint, DoLog doLog) throws Throwable {


        // 从dolog中判断是否需要打印，


        // 获取方法参数
        Object[] args = joinPoint.getArgs();



        // 打印方法参数
        printer.print()
        for (Object arg : args) {
            System.out.println(arg);
        }

        // 执行目标方法
        Object result = joinPoint.proceed();

        // 打印方法返回值
        printer.print(result);

    }

    private <T> T getAnnotation(JoinPoint joinPoint, Class<T> annotationType) {
        // 获取目标方法
        Object target = joinPoint.getTarget();
        try {
            // 获取目标方法的Method对象
            java.lang.reflect.Method method = target.getClass().getMethod(
                    joinPoint.getSignature().getName(),
                    joinPoint.getArgs().getClass()
            );

            // 获取方法上的注解
            T annotation = (T) method.getAnnotation(annotationType);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}

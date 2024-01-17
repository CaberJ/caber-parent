package cn.caber.commons.buried.log;

import cn.caber.commons.buried.filter.DefaultLogFilter;
import cn.caber.commons.buried.filter.LogFilter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoLog {

    Class<? extends LogFilter> paramLogFilter() default DefaultLogFilter.class;

    Class<? extends LogFilter> resultLogFilter() default DefaultLogFilter.class;
}

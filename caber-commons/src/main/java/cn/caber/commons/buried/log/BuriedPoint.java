package cn.caber.commons.buried.log;

import cn.caber.commons.buried.filter.DefaultParamLogFilter;
import cn.caber.commons.buried.filter.DefaultResultLogFilter;
import cn.caber.commons.buried.filter.ParamLogFilter;
import cn.caber.commons.buried.filter.ResultLogFilter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BuriedPoint {

    Class<? extends ParamLogFilter> paramLogFilter() default DefaultParamLogFilter.class;

    Class<? extends ResultLogFilter> resultLogFilter() default DefaultResultLogFilter.class;
}

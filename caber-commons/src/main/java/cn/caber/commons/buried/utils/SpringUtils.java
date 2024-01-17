package cn.caber.commons.buried.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext==null){
            SpringUtils.applicationContext=applicationContext;
        }
    }


    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }


    public static Environment getEnvironment(){
        return getApplicationContext().getEnvironment();
    }

    public static Object getBeanByClass(Class<?> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static Object getBeanByName(String name) {
        return getApplicationContext().getBean(name);
    }



}

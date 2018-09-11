package com.newbig.im.dal.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class DynamicDataSourceAspect {

    @Before("@annotation(DS)")
    public void beforeSwitchDS(JoinPoint point){
        Class<?> className = point.getTarget().getClass();
        String methodName = point.getSignature().getName();

        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DataSourceNames.IMCONFIG_DS;
        try {
            Method method = className.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(DS.class)) {
                DS annotation = method.getAnnotation(DS.class);
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        DataSourceContextHolder.setDB(dataSource);
    }

    @After("@annotation(DS)")
    public void afterSwitchDS(JoinPoint point){
        DataSourceContextHolder.clearDB();
    }
}

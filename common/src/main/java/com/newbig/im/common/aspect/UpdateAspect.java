package com.newbig.im.common.aspect;

import com.newbig.im.common.constant.AppConstant;
import com.newbig.im.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Aspect
@Component
@Slf4j
public class UpdateAspect {
    @Before("execution(* com..*.mapper.*.save*(..))" + " || " +
            "execution(* com..*.mapper.*.update*(..))" + " || " +
            "execution(* com..*.mapper.*.delete*(..))")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        String userId = AuthAndLogAspect.userIdThread.get();
        for (Object arg : args) {
            if (arg != null) {
                if (arg instanceof List) {
                    List argList = (List) arg;
                    if (!CollectionUtils.isEmpty(argList)) {
                        for (Object object : argList) {
                            Class<?> clas = object.getClass();
                            domainHandle(clas, object, joinPoint, userId);
                        }
                    }
                } else {
                    domainHandle(arg.getClass(), arg, joinPoint, userId);
                }
            }
        }
    }

    private void domainHandle(Class<?> cla, Object arg, JoinPoint joinPoint, String userName){

        try {
            if(cla.getTypeName().startsWith(AppConstant.PACKAGE_NAME)) {
                MethodUtils.invokeMethod(arg, "setGmtModify", new Date());
                MethodUtils.invokeMethod(arg, "setModifier", StringUtil.isBlank(userName) ? "unkown" : userName);
            }
        } catch (Exception e) {
            log.error("common fields process error: {}", ExceptionUtils.getStackTrace(e));
        }
    }
}

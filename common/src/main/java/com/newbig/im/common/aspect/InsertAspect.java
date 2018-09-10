package com.newbig.im.common.aspect;

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

import static com.newbig.im.common.constant.AppConstant.PACKAGE_NAME;

/**
 * User: haibo
 * Date: 2017/10/7 上午11:06
 * Desc:
 */
@Aspect
@Component
@Slf4j
public class InsertAspect {

    @Before("execution(* com..*.mapper.*.insert*(..))" + " || " +
            "execution(* com..*.mapper.*.add*(..))")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String userId = AuthAndLogAspect.userIdThread.get();
        for (Object arg : args) {
            if (arg != null) {
                if (arg instanceof List) {
                    List argList = (List) arg;
                    if (!CollectionUtils.isEmpty(argList)) {
                        for (Object object : argList) {
                            domainHandle(object, userId);
                        }
                    }
                } else {
                    domainHandle(arg, userId);
                }
            }
        }
    }

    private void domainHandle(Object object, String userId){

//        try {
//            if(object.getClass().getTypeName().startsWith(PACKAGE_NAME)) {
//                MethodUtils.invokeMethod(object, "setCreator", userId);
//                MethodUtils.invokeMethod(object, "setModifier", userId);
//                MethodUtils.invokeMethod(object, "setGmtCreate", new Date());
//                MethodUtils.invokeMethod(object, "setGmtModify", new Date());
//                try {
//                    MethodUtils.invokeMethod(object, "setIsDeleted", Boolean.FALSE);
//                } catch (Exception e) {
//                    MethodUtils.invokeMethod(object, "setIsDeleted", 0);
//                }
//            }
//        } catch (Exception e) {
//            log.error("common fields process error: {}", ExceptionUtils.getStackTrace(e));
//        }
    }
}

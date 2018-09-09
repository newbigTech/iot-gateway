package com.newbig.im.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: haibo
 * Date: 2017/10/2 下午9:54
 * Desc:
 */
@Slf4j
public class BeanCopyUtil {

    private static Map<String, BeanCopier> beanCopierMap = Maps.newHashMap();

    /**
     * bean 对象copy
     *
     * @param source
     * @param targetClass
     */
    public static Object copyProperties(Object source, Class<?> targetClass) throws IllegalAccessException, InstantiationException {

        String beanKey = generateKey(source.getClass(), targetClass);
        BeanCopier copier;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), targetClass, false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }

        Object targetObject = targetClass.newInstance();
        copier.copy(source, targetObject, null);

        return targetObject;

    }


    /**
     * bean 对象copy
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {

        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);

    }

    /**
     * @param source
     * @param target
     * @param targetClass
     * @throws Exception
     */
    public static void copyList(List<?> source, List target, Class<?> targetClass) {

        if (CollectionUtils.isEmpty(source) || target == null) {
            return;
        }

        for (Object obj : source) {
            try {
                Object targetObject = targetClass.newInstance();
                copyProperties(obj, targetObject);

                target.add(targetObject);
            }catch (Exception e){
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    /**
     * @param source
     * @param targetClass
     * @throws Exception
     */
    public static List copyList(List<?> source, Class<?> targetClass) throws IllegalAccessException, InstantiationException {

        if (CollectionUtils.isEmpty(source)) {
            return Lists.newArrayList();
        }

        List target = new ArrayList();
        for (Object obj : source) {
            Object targetObject = targetClass.newInstance();
            copyProperties(obj, targetObject);
            target.add(targetObject);
        }

        return target;
    }
    /**
     * 获取驼峰属性
     *
     * @param fieldName
     * @return
     */
    private static String getHumpFieldName(String fieldName) {
        String[] columnNameArr = StringUtil.split(fieldName, "_");
        int length = columnNameArr.length;
        StringBuilder buffer = new StringBuilder(columnNameArr[0]);
        if (length > 1) {
            for (int i = 1; i < columnNameArr.length; i++) {
                buffer.append(StringUtil.capitalize(columnNameArr[i]));
            }
        }
        return buffer.toString();
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

}

package com.newbig.im.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * User: haibo
 * Date: 2018/1/17 下午3:12
 * Desc:
 */
public class LogUtil {
    static Logger logger = LoggerFactory.getLogger("bizLogger");

    public static void info(String filename, String object) {
        MDC.put("logFileName", filename);
        logger.info(object);
    }
}

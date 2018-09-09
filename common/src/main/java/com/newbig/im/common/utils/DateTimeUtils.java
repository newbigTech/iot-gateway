package com.newbig.im.common.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

public class DateTimeUtils {
    public static final DateTimeZone timeZone = DateTimeZone.forID("Asia/Shanghai");

    public static String getNow() {
        return DateTime.now(timeZone).toString("yyyy-MM-dd HH:mm:ss");
    }
    public static String getDate() {
        return DateTime.now(timeZone).toString("yyyy-MM-dd");
    }

    public static Date getDateToMidNight() {
        //凌晨两点
        DateTime now = DateTime.now(timeZone);
        DateTime tomorrowStart = now.plusDays(1)
                                    .withTimeAtStartOfDay()
                                    .plusHours(2);
        return tomorrowStart.toDate();
    }
}

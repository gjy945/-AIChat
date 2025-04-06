package com.example.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateJava8Util {

    /**
     * 获取指定日期的开始时间
     *
     * @param date 输入的日期
     * @return 当天的开始时间
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime startOfLocalDateTime = localDateTime.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return Date.from(startOfLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param date 输入的日期
     * @return 当天的结束时间
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime endOfLocalDateTime = localDateTime.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
        return Date.from(endOfLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定LocalDate的开始时间对应的Date
     *
     * @param localDate 输入的LocalDate
     * @return 当天的开始时间对应的Date
     */
    public static Date getStartOfDay(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定LocalDate的结束时间对应的Date
     *
     * @param localDate 输入的LocalDate
     * @return 当天的结束时间对应的Date
     */
    public static Date getEndOfDay(LocalDate localDate) {
        LocalDateTime endOfLocalDateTime = localDate.atTime(23, 59, 59, 999999999);
        return Date.from(endOfLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
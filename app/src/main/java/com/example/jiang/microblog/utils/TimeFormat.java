package com.example.jiang.microblog.utils;

import java.util.Date;

/**
 * Created by jiang on 2018/4/15.
 */

public class TimeFormat {

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
    public static final String YESTERDAY = "昨天 ";

    public static final String FLAG = "flag";

    public static String format(Date date) {
        long delta = new Date().getTime() - date.getTime();
        //xx秒钟前
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        //xx分钟前
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        //xx小时前
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        //昨天
        if (delta < 48L * ONE_HOUR) {
            return YESTERDAY;
        }
        //一天或者xx天前
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            if (days <= 0) {
                return 1 + ONE_DAY_AGO;
            } else {
                return FLAG;
            }
        }
        //一月或者xx月前
        if (delta < 12L * 4L * ONE_WEEK) {
            return FLAG;
        } else {
            //一年或者xx年前
            return FLAG;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

}

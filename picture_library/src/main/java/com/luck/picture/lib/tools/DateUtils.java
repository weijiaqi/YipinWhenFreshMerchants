package com.luck.picture.lib.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.tool
 * email：893855882@qq.com
 * data：2017/5/25
 */

public class DateUtils {
    private static SimpleDateFormat msFormat = new SimpleDateFormat("mm:ss");
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

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParse(long duration) {
        String time = "";
        if (duration > 1000) {
            time = timeParseMinute(duration);
        } else {
            long minute = duration / 60000;
            long seconds = duration % 60000;
            long second = Math.round((float) seconds / 1000);
            if (minute < 10) {
                time += "0";
            }
            time += minute + ":";
            if (second < 10) {
                time += "0";
            }
            time += second;
        }
        return time;
    }

    /**
     * MS turn every minute
     *
     * @param duration Millisecond
     * @return Every minute
     */
    public static String timeParseMinute(long duration) {
        try {
            return msFormat.format(duration);
        } catch (Exception e) {
            e.printStackTrace();
            return "0:00";
        }
    }

    /**
     * 判断两个时间戳相差多少秒
     *
     * @param d
     * @return
     */
    public static int dateDiffer(long d) {
        try {
            long l1 = Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10));
            long interval = l1 - d;
            return (int) Math.abs(interval);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 计算两个时间间隔
     *
     * @param sTime
     * @param eTime
     * @return
     */
    public static String cdTime(long sTime, long eTime) {
        long diff = eTime - sTime;
        return diff > 1000 ? diff / 1000 + "秒" : diff + "毫秒";
    }


    public static String format( Date delta) {
        long mius = new Date().getTime() - delta.getTime();
        if (mius < 1L * ONE_MINUTE) {
            long seconds = toSeconds(mius);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (mius < 45L * ONE_MINUTE) {
            long minutes = toMinutes(mius);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (mius < 24L * ONE_HOUR) {
            long hours = toHours(mius);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (mius < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (mius < 30L * ONE_DAY) {
            long days = toDays(mius);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (mius < 12L * 4L * ONE_WEEK) {
            long months = toMonths(mius);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(mius);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
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

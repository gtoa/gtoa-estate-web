package com.renren.gota.webserver.util;

import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimeUtils {

    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_CHINESE = "yyyy年MM月";
    public static final String MM_dd_HH_mm_CHINESE = "MM月dd日 HH:mm";
    public static final String MM_dd = "MM-dd";
    public static final String yyyyMMdd_HHmmss = "yyyyMMdd HHmmss";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_HH_mm_ssS = "yyyy-MM-dd HH:mm:ss.S";
    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd/HH:mm";
    public static final String MMdd = "MM/dd";

    /**
     * 使用指定的时间创建一个Date对象 <br>
     * 2015年6月19日:下午5:44:09<br>
     * <br>
     * 
     * @param year
     * @param month
     *            从0开始
     * @param date
     * @return
     * <pre>
     * </pre>
     */
    public static Date build(
                    int year,
                    int month,
                    int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return calendar.getTime();
    }

    public static Date build(
                    int year,
                    int month,
                    int date,
                    int hourOfDay,
                    int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date, hourOfDay, minute);
        return calendar.getTime();
    }

    /**
     * 获取今天 <br>
     * 2015年6月23日:下午5:05:30<br>
     * <br>
     * 
     * @return
     */
    public static Date buildToday() {
        try {
            return TimeUtils.translate(new Date(), TimeUtils.yyyy_MM_dd);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 根据格式返回当前时间的字符串 <br>
     * 2015年7月24日:下午4:03:49<br>
     * <br>
     * 
     * @param format
     * @return
     */
    public static String buildByFormat(
                    String format) {
        return TimeUtils.format(new Date(), format);
    }

    /**
     * 获取两天之间的日期list <br>
     * 2015年6月23日:下午3:54:40<br>
     * <br>
     * 
     * @param startDate
     * @param containsStartDay
     * @param endDate
     * @param containsEndDay
     * @return 早的日期排在前
     */
    public static List<Date> getDateListBetweenDays(
                    Date startDate,
                    boolean containsStartDay,
                    Date endDate,
                    boolean containsEndDay) {
        List<Date> result = Lists.newArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        if (containsStartDay) {
            Date time = calendar.getTime();
            result.add(time);
        }
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date today = calendar.getTime();
        while (today.compareTo(endDate) < 0) {
            result.add(today);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            today = calendar.getTime();
        }
        if (containsEndDay) {
            result.add(calendar.getTime());
        }
        return result;
    }

    public static void main(
                    String[] args) throws ParseException {
        Date startDate = TimeUtils.parse("2015-06-01", TimeUtils.yyyy_MM_dd);
        Date endDate = TimeUtils.parse("2015-06-10", TimeUtils.yyyy_MM_dd);
        List<Date> dateListBetweenDays = TimeUtils.getDateListBetweenDays(startDate, true, endDate, true);
        System.out.println(TimeUtils.batchFormat(dateListBetweenDays, TimeUtils.yyyy_MM_dd));
    }

    /**
     * 格式化date <br>
     * 2015年4月17日:上午11:17:29<br>
     * <br>
     * 
     * @param date
     * @param format
     * @return
     */
    public static String format(
                    Date date,
                    String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String string = sdf.format(date);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 批量格式化 <br>
     * 2015年6月5日:下午1:35:29<br>
     * <br>
     * 
     * @param dateList
     * @param format
     * @return
     */
    public static List<String> batchFormat(
                    List<Date> dateList,
                    String format) {
        List<String> result = Lists.newArrayList();
        for (Date date : dateList) {
            result.add(TimeUtils.format(date, format));
        }
        return result;
    }

    /**
     * 把一个list的数据转成long类型 <br>
     * 2015年7月31日:下午5:39:01<br>
     * <br>
     * 
     * @param dates
     * @return
     */
    public static List<Long> batchToLong(
                    List<Date> dates) {
        List<Long> result = Lists.newArrayListWithExpectedSize(dates.size());
        for (Date date : dates) {
            result.add(date.getTime());
        }
        return result;
    }

    /**
     * 按照日期格式转化成date <br>
     * 2015年5月14日:下午3:18:42<br>
     * <br>
     * 
     * @param dateString
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parse(
                    String dateString,
                    String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(dateString);
        return date;
    }

    /**
     * 转化日期格式 <br>
     * 2015年5月14日:下午3:52:17<br>
     * <br>
     * 
     * @param src
     * @param srcFormate
     * @param targetFormate
     * @return
     * @throws ParseException
     */
    public static String translate(
                    String src,
                    String srcFormate,
                    String targetFormate) throws ParseException {
        Date parse = TimeUtils.parse(src, srcFormate);
        String format = TimeUtils.format(parse, targetFormate);
        return format;
    }

    /**
     * 转化日期格式 <br>
     * 2015年5月14日:下午3:52:17<br>
     * <br>
     * 
     * @param src
     * @param srcFormate
     * @param targetFormate
     * @return
     * @throws ParseException
     */
    public static Date translate(
                    Date src,
                    String targetFormate) throws ParseException {
        return TimeUtils.parse(TimeUtils.format(src, targetFormate), targetFormate);
    }

    /**
     * 将时间戳转换为时间 <br>
     * 2015年4月22日 下午4:52:19 <br>
     * <br>
     * 
     * @param timeStamp
     * @return
     */
    public static String converTimeStampToDate(
                    String timeStamp) {
        try {
            long timeMillis = Long.parseLong(timeStamp) * 1000;
            Date date = new Date(timeMillis);
            return format(date, "yyyy-MM-dd HH:mm:ss");
        } catch (NumberFormatException e) {
            return timeStamp;
        }
    }

    /**
     * 获取上月同一时期<br>
     * 2015年5月15日 上午11:10:39 <br>
     * <br>
     * 
     * @param currentDate
     * @return
     */
    public static Date getLastMonthDate(
                    Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 取得某一月的同一天date <br>
     * 2015年6月5日:上午11:40:42<br>
     * <br>
     * 
     * @param currentDate
     * @param month
     *            负数向前,正数向后
     * @return
     */
    public static Date getLastMonthDate(
                    Date currentDate,
                    int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取下月同一时期<br>
     * 2015年5月15日 上午11:10:39 <br>
     * <br>
     * 
     * @param currentDate
     * @return
     */
    public static Date getNextMonthDate(
                    Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 根据当前日期获取昨日日期<br>
     * 2015年5月18日 下午3:47:47 <br>
     * <br>
     * 
     * @param currentDate
     * @return
     */
    public static Date getYesterday(
                    Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 根据当前日期获取昨日日期<br>
     * 2015年5月18日 下午3:47:47 <br>
     * <br>
     * 
     * @param currentDate
     * @return
     */
    public static Date getTomorrow(
                    Date currentDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * FIXME 到账日期时间：3日后<br>
     * 2015年5月23日 下午3:04:16 <br>
     * <br>
     * 
     * @param date
     * @return
     */
    public static Date getArriveTime(
                    Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 3);
        return c.getTime();
    }

    /**
     * 计算相差天数<br>
     * 2015年5月23日 下午4:02:34 <br>
     * <br>
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getBetweenDays(
                    Date startDate,
                    Date endDate) {
        long beginTime = startDate.getTime();
        long endTime = endDate.getTime();
        int betweenDays = (int) ((endTime - beginTime) / (1000 * 60 * 60 * 24) + 0.5);

        return betweenDays;
    }

    /**
     * 判断是否是同一天<br>
     * 2015年5月25日 下午6:13:55 <br>
     * <br>
     * 
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean areSameDay(
                    Date dateA,
                    Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                        && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                        && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取days天之后的date对象 <br>
     * 2015年6月3日:下午8:53:05<br>
     * <br>
     * 
     * @param startDate
     * @param days
     *            正数向后,负数向前
     * @return
     */
    public static Date getBeforeDate(
                    Date startDate,
                    int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static String getMMdd(
                    String dateString) {
        try {
            Date date = parse(dateString, yyyy_MM_dd);
            return format(date, MMdd);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    /**
     * 计算当天剩余的秒数
     * 
     * @param date
     * @return
     */
    public static long getDayLastSeconds(
                    Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        long lastSeconds = (calendar.getTime().getTime() - date.getTime()) / 1000;
        return lastSeconds;
    }
}

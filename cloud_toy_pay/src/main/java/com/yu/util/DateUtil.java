package com.yu.util;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhusy on 2016/9/20.
 */
public class DateUtil {

    /**
     * 毫秒
     */
    public final static long MS = 1;
    /**
     * 每秒钟的毫秒数
     */
    public final static long SECOND_MS = MS * 1000;
    /**
     * 每分钟的毫秒数
     */
    public final static long MINUTE_MS = SECOND_MS * 60;
    /**
     * 每小时的毫秒数
     */
    public final static long HOUR_MS = MINUTE_MS * 60;
    /**
     * 每天的毫秒数
     */
    public final static long DAY_MS = HOUR_MS * 24;

    /**
     * 标准日期格式
     */
    public final static String NORM_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 标准时间格式
     */
    public final static String NORM_TIME_PATTERN = "HH:mm:ss";
    /**
     * 不含秒时间格式
     */
    public final static String NOMILS_TIME_PATTERN = "HH:mm";
    /**
     * 标准日期时间格式
     */
    public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 标准日期时间格式
     */
    public final static String NORM_DATETIME2_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * HTTP头中日期时间格式
     */
    public final static String HTTP_DATETIME_PATTERN = "dd/MMM/yyyy:HH:mm:ss";

    /**
     * yyyyMMddHHmmss
     */
    public final static String LONG_DATATIME_PATTERN = "yyyyMMddHHmmss";

    public final static String SHORT_NUMBER_PATTERN="yyyyMMdd";

    /**
     * 20150925014512
     */
    public static String FORMAT_LONG_Number = "yyyyMMddHHmmss";

    /**
     * 20150925
     */
    public static String FORMAT_SHORT_NUMBER = "yyyyMMdd";

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return formatDateTime(new Date());
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return formatDate(new Date());
    }

    // ------------------------------------ Format start ----------------------------------------------

    /**
     * 根据毫秒数转化日期格式化
     * yyyy-MM-dd H:m:s
     *
     * @param m 毫秒
     * @return
     */
    public static String formatDateTime(long m) {
        return new SimpleDateFormat(NORM_DATETIME_PATTERN).format(new Date(m));
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        return new SimpleDateFormat(NORM_DATETIME_PATTERN).format(date);
    }

    /**
     * 格式HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatTime(Date date) {
        return new SimpleDateFormat(NORM_TIME_PATTERN).format(date);
    }

    /**
     * 格式HH:mm
     *
     * @param m 毫秒数
     * @return 时分秒
     */
    public static String formatSpentTime(long m) {
        int hour = (int) (m / HOUR_MS);
        int min = (int) (m % HOUR_MS / MINUTE_MS);
        int ms = (int) (((m % HOUR_MS) % MINUTE_MS) / SECOND_MS);
        StringBuilder sb = new StringBuilder();
        if (hour != 0) {
            sb.append(hour + "小时");
        }
        if (min != 0) {
            sb.append(min + "分钟");
        }
        if (ms != 0) {
            sb.append(ms + "秒");
        }
        return sb.toString();
    }

    /**
     * 格式化为Http的标准日期格式
     *
     * @param date 被格式化的日期
     * @return HTTP标准形式日期字符串
     */
    public static String formatHttpDate(Date date) {
        return new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.ENGLISH).format(date);
    }

    public static Date formatHttpDateString(String dateStr) {
        try {
            return new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.ENGLISH).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        return new SimpleDateFormat(NORM_DATE_PATTERN).format(date);
    }

    /**
     * 格式化为中文”昨天 09:30“
     *
     * @param m 毫秒数
     * @return 格式化后的字符串
     */
    public static String formatDateTimeString(long m) {
        String date = formatDate(new Date(m));
        String time = formatTime(new Date(m));
        String today = today();
        String yesterday = formatDate(yesterday());
        StringBuilder sb = new StringBuilder();
        if (today.equals(date)) {
            sb.append("今天 ");
            sb.append(time);
        } else if (yesterday.equals(date)) {
            sb.append("昨天");
        } else {
            sb.append(date);
        }
        return sb.toString();
    }
    // ------------------------------------ Format end ----------------------------------------------

    // ------------------------------------ Parse start ----------------------------------------------

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateString 特定格式的日期
     * @param format     格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(String dateString) {
//		return parse(s, "yyyy-MM-dd HH:mm:ss");
        try {
            return new SimpleDateFormat(NORM_DATETIME_PATTERN).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd HH:mm
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime2(String dateString) {
        try {
            return new SimpleDateFormat(NORM_DATETIME2_PATTERN).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式yyyy-MM-dd
     *
     * @param dateString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat(NORM_DATE_PATTERN).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式HH:mm:ss
     *
     * @param timeString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseTime(String timeString) {
        try {
            return new SimpleDateFormat(NORM_TIME_PATTERN).parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseLongTime(String timeString) {
        try {
            return new SimpleDateFormat(LONG_DATATIME_PATTERN).parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 格式：<br>
     * 1、yyyy-MM-dd HH:mm:ss<br>
     * 2、yyyy-MM-dd<br>
     * 3、HH:mm:ss>
     * 4、yyyyMMddHHmmss
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr) {
        int length = dateStr.length();
        try {
            if (length == DateUtil.NORM_DATETIME_PATTERN.length()) {
                return parseDateTime(dateStr);
            } else if (length == DateUtil.NORM_DATE_PATTERN.length()) {
                return parseDate(dateStr);
            } else if (length == DateUtil.NORM_TIME_PATTERN.length()) {
                return parseTime(dateStr);
            } else if (length == DateUtil.LONG_DATATIME_PATTERN.length()) {
                return parseLongTime(dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // ------------------------------------ Parse end ----------------------------------------------

    // ------------------------------------ Offset start ----------------------------------------------

    /**
     * 昨天
     *
     * @return 昨天
     */
    public static Date yesterday() {
        return offsiteDate(new Date(), Calendar.DAY_OF_YEAR, -1);
    }

    /**
     * 明天
     *
     * @return 明天 格式 yyyy-MM-dd
     */
    public static String tomorrow() {
        return formatDate(offsiteDate(new Date(), Calendar.DAY_OF_YEAR, 1));
    }

    /**
     * 上周
     *
     * @return 上周
     */
    public static Date lastWeek() {
        return offsiteDate(new Date(), Calendar.WEEK_OF_YEAR, -1);
    }

    /**
     * 上个月
     *
     * @return 上个月
     */
    public static Date lastMouth() {
        return offsiteDate(new Date(), Calendar.MONTH, -1);
    }

    /**
     * n 前几个月(0代表当前月  1 代表上月 以此类推)
     *
     * @return 获取前 n 个月的第一天
     */
    public static String mouthFirstDay(int n) {
        SimpleDateFormat format = new SimpleDateFormat(NORM_DATE_PATTERN);
        Calendar c_start = Calendar.getInstance();
        c_start.add(Calendar.MONTH, n);
        //设置为1号,当前日期既为本月第一天
        c_start.set(Calendar.DAY_OF_MONTH,1);
        String oneStartMonthDate = format.format(c_start.getTime());
        return oneStartMonthDate;
    }

    public static int mouth(int n) {
        SimpleDateFormat format = new SimpleDateFormat("MM");
        Calendar c_start = Calendar.getInstance();
        c_start.add(Calendar.MONTH, n);
        //设置为1号,当前日期既为本月第一天
        c_start.set(Calendar.DAY_OF_MONTH,1);
        int oneStartMonthDate = Integer.parseInt(format.format(c_start.getTime()));
        return oneStartMonthDate;
    }

    /**
     * n 前几个月(0代表当前月  -1 代表上月 以此类推)
     *
     * @return 获取前 n 个月的最后一天
     */
    public static String mouthLastDay(int n) {
        SimpleDateFormat format = new SimpleDateFormat(NORM_DATE_PATTERN);
        //获取当前月最后一天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, n);
        calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date          基准日期
     * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
     * @param offsite       偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static Date offsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }
    // ------------------------------------ Offset end ----------------------------------------------

    /**
     * 判断两个日期相差的时长<br/>
     * 返回 minuend - subtrahend 的差
     *
     * @param subtrahend 减数日期
     * @param minuend    被减数日期
     * @param diffField  相差的选项：相差的天、小时
     * @return 日期差
     */
    public static long diff(Date subtrahend, Date minuend, long diffField) {
        long diff = minuend.getTime() - subtrahend.getTime();
        return diff / diffField;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：纳秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，纳秒
     */
    public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：毫秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，毫秒
     */
    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }

    /**
     * 计算时间差
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 结束时间-开始时间 （毫秒）
     */
    public static long spendMs(String startDate, String endDate) {
        Date start = parseDateTime(matchDateString(startDate));
        Date end = parseDateTime(matchDateString(endDate));
        if (start == null || end == null) {
            System.out.println(startDate + "\t" + endDate);
            return 0;
        }
        return end.getTime() - start.getTime();
    }

    /**
     * 当前时间加指定小时后的时间,格式yyyy-MM-dd HH:mm:ss
     * @param hour
     * @return 当前时间加指定小时后的时间 标准形式字符串
     */
    public static String nowAdd(int hour){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, hour);
        Date newDate = cal.getTime();
        return formatDateTime(newDate);
    }

    /**
     * 获取时间Id
     * @return
     */
    public static String getDateId(){
        return getSixRandom(DateUtil.FORMAT_LONG_Number);
    }


    /**
     * 获取日期+6位随机数
     * @return
     */
    public static String getDateSixRandomId(){
        return getSixRandom(DateUtil.FORMAT_SHORT_NUMBER);
    }

    /**
     * 获取时间 + 获取6位随机数字
     */
    public static String getSixRandom(String format){
        String returnValue = DateUtil.getNow(format);
        int random = (int)((Math.random()*9+1)*100000);
        return returnValue+random;
    }

    /**
     * 根据用户格式返回当前日期
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 清洗时间日期数据
     *
     * @param dateStr
     * @return
     */
    public static String matchDateString(String dateStr) {
        try {
            List matches = null;
            Pattern p = Pattern.compile("(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
            Matcher matcher = p.matcher(dateStr);
            if (matcher.find() && matcher.groupCount() >= 1) {
                matches = new ArrayList();
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String temp = matcher.group(i);
                    matches.add(temp);
                }
            } else {
                matches = Collections.EMPTY_LIST;
            }
            if (matches.size() > 0) {
                return ((String) matches.get(0)).trim();
            } else {
            }
        } catch (Exception e) {
            return "";
        }

        return "";
    }


    //
    public static String formatLocalDateTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN));
    }

    public static String formatLocalDateTime(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(NORM_DATE_PATTERN));
    }

    public static LocalDateTime parseLocalDateTime(String time) {
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern(NORM_DATETIME_PATTERN));
    }

    public static LocalDate parseLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(NORM_DATE_PATTERN));
    }

    /**
     * 获取一天分钟数据
     *
     * @return
     */
    public static List<String> getTime() {
        int hour = 24;
        int minute = 60;
        //一天所有时间段值
        String aTime = "";
        //总的时间坐标轴显示X轴的坐标
        List<String> dateTime = new ArrayList<String>();
        for (int i = 0; i < hour; i++) {
            for (int j = 0; j < minute; j++) {
                if (j % 5 == 0) {
                    if (i < 10 && j < 10) {
                        aTime = "0" + i + ":" + "0" + j;
                        dateTime.add(aTime);
                    }
                    if (i < 10 && j >= 10) {
                        aTime = "0" + i + ":" + j;
                        dateTime.add(aTime);
                    }
                    if (i >= 10 && j < 10) {
                        aTime = i + ":" + "0" + j;
                        dateTime.add(aTime);
                    }
                    if (i >= 10 && j >= 10) {
                        aTime = i + ":" + j;
                        dateTime.add(aTime);
                    }
                }
            }
        }
        return dateTime;
    }

    /**
     * 查看指定日期是不是当天
     * @param dateStart  格式 ：yyyy-mm-dd
     * @return
     */
    public static boolean compareDate(String dateStart){
        Date dateTwo = null;
        try {
            dateTwo = new SimpleDateFormat(DateUtil.NORM_DATE_PATTERN).parse(dateStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar ca = Calendar.getInstance();
        ca.setTime(dateTwo);

        //年
        int year = ca.get(Calendar.YEAR);

        //月
        int month = ca.get(Calendar.MONTH) + 1;

        //日
        int day = ca.get(Calendar.DATE);

        //实名日期
        String dateThree = year+"-"+month+"-"+day;

        // 获取当前年份、月份、日期
        Calendar cale = null;
        cale = Calendar.getInstance();
        //年
        int yearTwo = cale.get(Calendar.YEAR);

        //月
        int monthTwo = cale.get(Calendar.MONTH) + 1;

        //日
        int dayTwo = cale.get(Calendar.DATE);
        //今天
        String dateFour = yearTwo+"-"+monthTwo+"-"+dayTwo;

        return dateFour.equals(dateThree) ? true : false;

    }




    /**
     * 获取指定位数的随机数
     * @param number  位数 10000 代表四位
     * @return
     */
    public static String getRandom(Integer number){
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //生成4位随机数
        String str= (random.nextInt(number) + "");
        return str;
    }
}

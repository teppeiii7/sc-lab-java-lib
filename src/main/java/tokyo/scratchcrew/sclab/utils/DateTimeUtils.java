package tokyo.scratchcrew.sclab.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;

/**
 * joda-timeを扱うUtilsです。
 * 
 * 
 * @author teppeiii7
 *
 */
public class DateTimeUtils {

    public static final String TIME_ZONE_JP = "Asia/Tokyo";
    public static final String MS_FORMAT_NOT_TIMEZONE = "yyyyMMddHHmmssSSS";
    public static final String MS_FORMAT_ISO_8601_NOT_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String MS_FORMAT_CUSTOM = "yyyy-MM-dd HH:mm:ss.SSS";
    
    /**
     * 
     * @return
     */
    public static DateTime getNowJst() {
        return DateTime.now(DateTimeZone.forID(TIME_ZONE_JP));
    }
    
    /**
     * 
     * @return
     */
    public static String getNowJstByMsFormat() {
        return toString(getNowJst(), MS_FORMAT_NOT_TIMEZONE);
    }
    
    /**
     * 
     * @return
     */
    public static String getNowJstByMsFormatIso8601() {
        return toString(getNowJst(), MS_FORMAT_ISO_8601_NOT_TIMEZONE);
    }
    
    /**
     * 
     * @param dateTime
     * @param format
     * @return
     */
    public static String toString(DateTime dateTime, String format) {
        return DateTimeFormat.forPattern(format).print(dateTime);
    }
    
    /**
     * 
     * @param time
     * @return
     */
    public static DateTime toDateTime(long time) {
        return new DateTime(time, DateTimeZone.forID(TIME_ZONE_JP));
    }
    
    /**
     * 
     * @param datetime
     * @param format
     * @return
     */
    public static DateTime toDateTime(String datetime, String format) {
        return DateTimeFormat.forPattern(format).parseDateTime(datetime);
    }

    /**
     * 文字列のstartとendの差分を日(Day)単位で取得します。
     * 
     * @param start
     * @param end
     * @param format
     * @return
     */
    public static long durationDays(String start, String end, String format) {
        return durationDays(toDateTime(start, format), toDateTime(end, format));
    }

    /**
     * startとendの差分を日(Day)単位で取得します。<br>
     * 
     * @param start
     * @param end
     * @return
     */
    public static long durationDays(DateTime start, DateTime end) {
        Duration d = new Duration(start, end);
        return d.getStandardDays();
    }

    /**
     * startとendの差分をミリ秒単位で取得します。<br>
     * 文字列の書式は {@link jp.co.cacco.omt.util.OmtLibConst#AT_FORMAT} を使用しています。
     * 
     * @param start
     * @param end
     * @param format
     * @return
     */
    public static long durationMillis(String start, String end, String format) {
        return durationMillis(toDateTime(start, format), toDateTime(end, format));
    }

    /**
     * startとendの差分をミリ秒単位で取得します。<br>
     * 
     * @param start
     * @param end
     * @return
     */
    public static long durationMillis(DateTime start, DateTime end) {
        return end.minus(start.getMillis()).getMillis();
    }

}

package space.levan.suishouji.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by WangZhiYao on 2017/5/6.
 */

public class DateUtils
{
    public static String getDate(int i)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, i);
        return new SimpleDateFormat("yyyy-MM", Locale.CHINA)
                .format(new Date(c.getTimeInMillis()));
    }

    public static String setDate(int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                .format(new Date(c.getTimeInMillis()));
    }
}

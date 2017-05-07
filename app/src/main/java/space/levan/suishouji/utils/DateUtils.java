package space.levan.suishouji.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 格式化时间工具类
 *
 * Created by WangZhiYao on 2017/5/6.
 */

public class DateUtils
{
    /**
     * 用于CountFragment与SearchFragment的标题栏
     * 设置时间
     * @param i 标记
     * @return
     */
    public static String setDate(int i)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, i);
        return new SimpleDateFormat("yyyy-MM", Locale.getDefault())
                .format(new Date(c.getTimeInMillis()));
    }

    /**
     * 用于将数据存进Realm时将时间格式化
     * @param year 年
     * @param month 月
     * @param day 日
     * @return
     */
    public static String setDate(int year, int month, int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date(c.getTimeInMillis()));
    }
}

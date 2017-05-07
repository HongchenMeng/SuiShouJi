package space.levan.suishouji.utils;

import android.content.Context;
import android.content.Intent;

import space.levan.suishouji.App;
import space.levan.suishouji.view.base.BaseActivity;

/**
 * 界面工具类
 *
 * Created by WangZhiYao on 2017/5/6.
 */

public class UIUtils
{
    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext()
    {
        return App.getApplication();
    }

    /**
     * 页面跳转
     * 如果不在activity里去打开activity  需要指定任务栈  需要设置标签
     *
     * @param intent
     */
    public static void startActivity(Intent intent)
    {
        if (BaseActivity.activity == null)
        {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
        else
        {
            BaseActivity.activity.startActivity(intent);
        }
    }
}

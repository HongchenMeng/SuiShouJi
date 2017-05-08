package space.levan.suishouji;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import space.levan.suishouji.view.base.BaseActivity;

/**
 * 全局类
 *
 * Created by WangZhiYao on 2017/5/5.
 */

public class App extends Application
{
    private static App mApp;
    private static List<BaseActivity> activities;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mApp = this;
        activities = new LinkedList<>();
        initRealm();
        AVOSCloud.initialize(this,
                "hMrIgm5mc7gBH9RfXMHLK2Ts-gzGzoHsz",
                "iGqKMDXYmbHwFG6PQGIrD57F");
    }

    private void initRealm()
    {
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("SuiShouJi.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication()
    {
        return mApp;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(BaseActivity activity)
    {
        activities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity)
    {
        activities.remove(activity);
    }

    /**
     * 结束当前所有Activity
     */
    public static void clearActivities()
    {
        ListIterator<BaseActivity> iterator = activities.listIterator();
        AppCompatActivity activity;
        while (iterator.hasNext())
        {
            activity = iterator.next();
            if (activity != null)
            {
                activity.finish();
            }
        }
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication()
    {
        clearActivities();
        System.exit(0);
    }
}

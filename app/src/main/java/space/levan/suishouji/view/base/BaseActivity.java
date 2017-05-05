package space.levan.suishouji.view.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import space.levan.suishouji.App;
import space.levan.suishouji.utils.UIUtils;

/**
 * Created by WangZhiYao on 2017/5/6.
 */

public class BaseActivity extends AppCompatActivity
{
    public static BaseActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activity = this;
        ((App) UIUtils.getContext()).addActivity(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        activity = this;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        activity = null;
    }

    @Override
    public void finish()
    {
        super.finish();
        ((App) UIUtils.getContext()).removeActivity(this);
    }

    /**
     * activity退出时将activity移出栈
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ((App) UIUtils.getContext()).removeActivity(this);
    }
}

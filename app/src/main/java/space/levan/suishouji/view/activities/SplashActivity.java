package space.levan.suishouji.view.activities;

import android.content.Intent;
import android.os.Bundle;

import com.avos.avoscloud.AVUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.wangyuwei.particleview.ParticleView;
import space.levan.suishouji.R;
import space.levan.suishouji.view.base.BaseActivity;

/**
 * Splash
 *
 * Created by WangZhiYao on 2017/5/8.
 */

public class SplashActivity extends BaseActivity
{
    @BindView(R.id.pv_logo)
    ParticleView mPvLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initEvents();
    }

    protected void initEvents()
    {
        mPvLogo.setOnParticleAnimListener(() ->
        {
            AVUser currentUser = AVUser.getCurrentUser();
            if (currentUser != null)
            {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                this.finish();
            }
            else
            {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                this.finish();
            }
        });
        mPvLogo.startAnim();
    }

    @Override
    public void onBackPressed()
    {
        //空实现拦截在Splash时用户按下返回键导致MainActivity内存泄漏
    }
}

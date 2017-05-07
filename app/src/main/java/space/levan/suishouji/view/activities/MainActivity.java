package space.levan.suishouji.view.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import space.levan.suishouji.App;
import space.levan.suishouji.R;
import space.levan.suishouji.view.adapter.MainFragmentAdapter;
import space.levan.suishouji.view.base.BaseActivity;
import space.levan.suishouji.view.fragment.CountFragment;
import space.levan.suishouji.view.fragment.RecordFragment;
import space.levan.suishouji.view.fragment.SearchFragment;
import space.levan.suishouji.view.fragment.UserFragment;

/**
 * 主页
 *
 * Created by WangZhiYao on 2017/5/5.
 */

public class MainActivity extends BaseActivity
{
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    private UserFragment        mUserFragment;
    private CountFragment       mCountFragment;
    private RecordFragment      mRecordFragment;
    private SearchFragment      mSearchFragment;
    private MainFragmentAdapter mMainFragmentAdapter;

    private long exitTime = 0;

    /**
     * 底部菜单点击响应事件
     */
    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener
            = item ->
    {
        switch (item.getItemId())
        {
            case R.id.navigation_record:
                mViewpager.setCurrentItem(0);
                return true;
            case R.id.navigation_search:
                mViewpager.setCurrentItem(1);
                return true;
            case R.id.navigation_count:
                mViewpager.setCurrentItem(2);
                return true;
            case R.id.navigation_user:
                mViewpager.setCurrentItem(3);
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*
          翻页设置底部栏菜单选中状态
         */
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                mNavigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        setupViewPager(mViewpager);
    }

    /**
     * 将Fragment添加进ViewPager
     *
     * @param mViewpager
     */
    private void setupViewPager(ViewPager mViewpager)
    {
        mRecordFragment = new RecordFragment();
        mSearchFragment = new SearchFragment();
        mCountFragment = new CountFragment();
        mUserFragment = new UserFragment();
        mMainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mMainFragmentAdapter.addFragment(mRecordFragment);
        mMainFragmentAdapter.addFragment(mSearchFragment);
        mMainFragmentAdapter.addFragment(mCountFragment);
        mMainFragmentAdapter.addFragment(mUserFragment);
        mViewpager.setAdapter(mMainFragmentAdapter);
    }

    /**
     * 拦截物理返回按钮点击
     * 实现双击退出
     */
    @Override
    public void onBackPressed()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Snackbar.make(mNavigation, getString(R.string.app_exit), Snackbar.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }
        else
        {
            this.finish();
            App.quiteApplication();
        }
    }
}

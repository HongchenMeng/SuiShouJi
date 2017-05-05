package space.levan.suishouji.view.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import space.levan.suishouji.R;
import space.levan.suishouji.view.adapter.MainFragmentAdapter;
import space.levan.suishouji.view.fragment.CountFragment;
import space.levan.suishouji.view.fragment.RecordFragment;
import space.levan.suishouji.view.fragment.SearchFragment;
import space.levan.suishouji.view.fragment.UserFragment;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;
    private MainFragmentAdapter mMainFragmentAdapter;
    private RecordFragment mRecordFragment;
    private SearchFragment mSearchFragment;
    private CountFragment mCountFragment;
    private UserFragment mUserFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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

        /*
        Bill bill = new Bill();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        bill.time = dateFormat.format(date);
        bill.mode = "支出";
        bill.category = "美团";
        bill.method = "支付宝";
        bill.amount = "95.00";
        bill.remark = "";
        RealmUtils.addToBill(bill);

        Bill newBill = RealmUtils.getAllBill().get(0);
        Log.w("WZY", newBill.toString());
        */
    }

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
}

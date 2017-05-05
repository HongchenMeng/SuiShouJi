package space.levan.suishouji.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments = new ArrayList<>();

    public MainFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    public void addFragment(Fragment mFragment)
    {
        mFragments.add(mFragment);
    }
}

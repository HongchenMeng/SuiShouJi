package space.levan.suishouji.view.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener
{
    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onClick(View view) {}
}

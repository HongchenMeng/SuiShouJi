package space.levan.suishouji.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import space.levan.suishouji.R;
import space.levan.suishouji.view.base.BaseFragment;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class UserFragment extends Fragment
{
    private String TAG = "UserFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.w(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }
}

package space.levan.suishouji.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import space.levan.suishouji.R;
import space.levan.suishouji.utils.DateUtils;
import space.levan.suishouji.utils.RealmUtils;
import space.levan.suishouji.view.adapter.SearchAdapter;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class SearchFragment extends Fragment
{
    @BindView(R.id.tv_search_date)
    TextView mTvDate;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private Realm                      realm;
    private RealmChangeListener        realmListener;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchAdapter              mSearchAdapter;

    private int MARK = 0;

    private String TAG = "SearchFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        initData();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            Log.w(TAG, isVisibleToUser + "");

            realm = Realm.getDefaultInstance();
            realmListener = (RealmChangeListener<Realm>) realm1 -> initData();
            realm.addChangeListener(realmListener);
        }
        else
        {
            Log.w(TAG, isVisibleToUser + "");
            if (realmListener != null)
            {
                Log.w(TAG, "0");
                realm.removeChangeListener(realmListener);
            }
            else
            {
                Log.w(TAG, "1");
                realm = Realm.getDefaultInstance();
                realmListener = (RealmChangeListener<Realm>) realm1 -> initData();
                realm.addChangeListener(realmListener);
            }
        }
    }

    private void initData()
    {
        mTvDate.setText(DateUtils.getDate(MARK));

        mSearchAdapter = new SearchAdapter(getContext(),
                RealmUtils.getBill(DateUtils.getDate(MARK)));
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    @OnClick({R.id.iv_search_before, R.id.iv_search_after})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_search_before:
                MARK -= 1;
                initData();
                break;
            case R.id.iv_search_after:
                MARK += 1;
                initData();
                break;
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

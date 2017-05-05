package space.levan.suishouji.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import space.levan.suishouji.R;
import space.levan.suishouji.utils.RealmUtils;
import space.levan.suishouji.view.adapter.SearchAdapter;
import space.levan.suishouji.view.base.BaseFragment;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class SearchFragment extends BaseFragment
{
    private Realm realm;
    private RealmChangeListener realmListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchAdapter mSearchAdapter;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        initData();
        realm = Realm.getDefaultInstance();
        realmListener = (RealmChangeListener<Realm>) realm1 -> initData();
        realm.addChangeListener(realmListener);
    }

    private void initData()
    {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSearchAdapter = new SearchAdapter(getContext(),RealmUtils.getAllBill());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_search;
    }
}

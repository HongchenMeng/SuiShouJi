package space.levan.suishouji.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import space.levan.suishouji.R;
import space.levan.suishouji.utils.DateUtils;
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
    private ImageView mIvBefore;
    private ImageView mIvAfter;
    private TextView mTvDate;

    private int MARK = 0;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mIvBefore = (ImageView) view.findViewById(R.id.iv_search_before);
        mIvAfter = (ImageView) view.findViewById(R.id.iv_search_after);
        mTvDate = (TextView) view.findViewById(R.id.tv_search_date);
        mIvBefore.setOnClickListener(this);
        mIvAfter.setOnClickListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mTvDate.setText(DateUtils.getDate(MARK));
        mSearchAdapter = new SearchAdapter(getContext(),
                RealmUtils.getBill(DateUtils.getDate(MARK)));
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        initData();

        realm = Realm.getDefaultInstance();
        realmListener = (RealmChangeListener<Realm>) realm1 -> initData();
        realm.addChangeListener(realmListener);
    }

    private void initData()
    {
        mRecyclerView.setAdapter(mSearchAdapter);
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_search;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_search_before:
                MARK -= 1;
                mTvDate.setText(DateUtils.getDate(MARK));
                mSearchAdapter = new SearchAdapter(getContext(),
                        RealmUtils.getBill(DateUtils.getDate(MARK)));
                mRecyclerView.setAdapter(mSearchAdapter);
                break;
            case R.id.iv_search_after:
                MARK += 1;
                mTvDate.setText(DateUtils.getDate(MARK));
                mSearchAdapter = new SearchAdapter(getContext(),
                        RealmUtils.getBill(DateUtils.getDate(MARK)));
                mRecyclerView.setAdapter(mSearchAdapter);
                break;
            default:
                break;
        }
    }
}

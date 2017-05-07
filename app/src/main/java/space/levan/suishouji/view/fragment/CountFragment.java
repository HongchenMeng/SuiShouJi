package space.levan.suishouji.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import space.levan.suishouji.R;
import space.levan.suishouji.utils.DateUtils;
import space.levan.suishouji.utils.RealmUtils;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class CountFragment extends Fragment
{
    @BindView(R.id.tv_count_date)
    TextView mTvDate;
    @BindView(R.id.tv_count_income_wages)
    TextView mTvIncomeWages;
    @BindView(R.id.tv_count_income_bonus)
    TextView mTvIncomeBonus;
    @BindView(R.id.tv_count_income_other)
    TextView mTvIncomeOther;
    @BindView(R.id.tv_count_consumption_food)
    TextView mTvConsumptionFood;
    @BindView(R.id.tv_count_consumption_traffic)
    TextView mTvConsumptionTraffic;
    @BindView(R.id.tv_count_consumption_home)
    TextView mTvConsumptionHome;
    @BindView(R.id.tv_count_consumption_dress_up)
    TextView mTvConsumptionDressUp;
    @BindView(R.id.tv_count_consumption_entertainment)
    TextView mTvConsumptionEntertainment;
    @BindView(R.id.tv_count_consumption_digital)
    TextView mTvConsumptionDigital;
    @BindView(R.id.tv_count_consumption_travel)
    TextView mTvConsumptionTravel;
    @BindView(R.id.tv_count_consumption_other)
    TextView mTvConsumptionOther;
    @BindView(R.id.tv_count_totals)
    TextView mTvTotals;
    Unbinder unbinder;

    private int MARK = 0;
    private Realm               realm;
    private RealmChangeListener realmListener;
    private String TAG = "CountFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_count, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        //realm = Realm.getDefaultInstance();
        //realmListener = (RealmChangeListener<Realm>) realm1 -> initView();
        //realm.addChangeListener(realmListener);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            /*
            Log.w(TAG, isVisibleToUser + "");

            realm = Realm.getDefaultInstance();
            realmListener = (RealmChangeListener<Realm>) realm1 -> initView();
            realm.addChangeListener(realmListener);*/
        }
        else
        {
            /*
            Log.w(TAG, isVisibleToUser + "");
            if (realmListener != null)
            {
                //realm.removeChangeListener(realmListener);
            }
            else
            {

                //realm = Realm.getDefaultInstance();
                //realmListener = (RealmChangeListener<Realm>) realm1 -> initView();
                //realm.addChangeListener(realmListener);
                //initView();
            }*/
        }
    }

    private void initView()
    {
        mTvDate.setText(DateUtils.getDate(MARK));

        String[] consumption = this.getResources()
                .getStringArray(R.array.consumption_category);

        String[] income = this.getResources()
                .getStringArray(R.array.income_category);

        String[] mode = this.getResources()
                .getStringArray(R.array.mode);

        String[][] type = {consumption, income};

        TextView consumptionTextView[] = {mTvConsumptionFood,
                mTvConsumptionTraffic,
                mTvConsumptionHome,
                mTvConsumptionDressUp,
                mTvConsumptionEntertainment,
                mTvConsumptionDigital,
                mTvConsumptionTravel,
                mTvConsumptionOther};

        TextView incomeTextView[] = {mTvIncomeWages,
                mTvIncomeBonus,
                mTvIncomeOther};

        TextView modeTextView[][] = {consumptionTextView, incomeTextView};

        for (int i = 0; i < modeTextView.length; i++)
        {
            for (int x = 0; x < modeTextView[i].length; x++)
            {
                String strAmount = String.format(Locale.getDefault(), "%.2f",
                        RealmUtils.getTotal(DateUtils.getDate(MARK), mode[i], type[i][x]));

                modeTextView[i][x].setText(type[i][x] + "：" + strAmount);
            }
        }
        //Log.w("WZY", modeTextView.length + "");
    }


    @OnClick({R.id.iv_count_before, R.id.iv_count_after})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_count_before:
                MARK -= 1;
                initView();
                break;
            case R.id.iv_count_after:
                MARK += 1;
                initView();
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

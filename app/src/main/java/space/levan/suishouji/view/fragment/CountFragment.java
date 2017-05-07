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
import space.levan.suishouji.R;
import space.levan.suishouji.utils.DateUtils;
import space.levan.suishouji.utils.RealmUtils;

/**
 * 算一算
 *
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_count, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;
    }

    /**
     * 判断Fragment对用户是否可见，实现懒加载
     *
     * @param isVisibleToUser 是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            initView();
        }
    }

    /**
     * 加载数据，设置视图
     */
    private void initView()
    {
        mTvDate.setText(DateUtils.setDate(MARK));

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
                        RealmUtils.getDetail(DateUtils.setDate(MARK), mode[i], type[i][x]));

                modeTextView[i][x].setText(type[i][x] + "：" + strAmount);
            }
        }

        String strTotalConsumption = String.format(Locale.getDefault(), "%.2f",
                RealmUtils.getTotalConsumption(DateUtils.setDate(MARK)));
        String strTotalIncome = String.format(Locale.getDefault(), "%.2f",
                RealmUtils.getTotalIncome(DateUtils.setDate(MARK)));
        String strTotal = String.format(Locale.getDefault(), "%.2f",
                (RealmUtils.getTotalIncome(DateUtils.setDate(MARK))
                        - RealmUtils.getTotalConsumption(DateUtils.setDate(MARK))));

        mTvTotals.setText(mode[0] + "："
                + strTotalConsumption + "，"
                + mode[1] + "："
                + strTotalIncome + "，"
                + "总计：" + strTotal);
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

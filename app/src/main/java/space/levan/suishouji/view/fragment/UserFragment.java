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
import butterknife.Unbinder;
import space.levan.suishouji.R;
import space.levan.suishouji.utils.RealmUtils;

/**
 * 我的
 *
 * Created by WangZhiYao on 2017/5/5.
 */

public class UserFragment extends Fragment
{
    @BindView(R.id.tv_user_total)
    TextView mTvTotal;
    @BindView(R.id.tv_user_cash)
    TextView mTvCash;
    @BindView(R.id.tv_user_alipay)
    TextView mTvAlipay;
    @BindView(R.id.tv_user_savings_card)
    TextView mTvSavingsCard;
    @BindView(R.id.tv_user_traffic_card)
    TextView mTvTrafficCard;
    @BindView(R.id.tv_user_debit_card)
    TextView mTvDebitCard;
    @BindView(R.id.tv_user_credit_card)
    TextView mTvCreditCard;
    @BindView(R.id.tv_user_other)
    TextView mTvOther;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 加载数据
     */
    private void initView()
    {
        String[] method = this.getResources()
                .getStringArray(R.array.assets);

        TextView totalTextView[] = {mTvCash,
                mTvAlipay,
                mTvSavingsCard,
                mTvTrafficCard,
                mTvDebitCard,
                mTvCreditCard,
                mTvOther};

        double mTotal = 0;

        for (int i  = 0; i < method.length; i++)
        {
            String total = String.format(Locale.getDefault(), "%.2f",
                    RealmUtils.getTotal(method[i]));

            mTotal += RealmUtils.getTotal(method[i]);

            totalTextView[i].setText(method[i] + "：" + total);
        }

        mTvTotal.setText("总计：" + String.format(Locale.getDefault(), "%.2f", mTotal));
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package space.levan.suishouji.view.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmResults;
import space.levan.suishouji.R;
import space.levan.suishouji.bean.Bill;
import space.levan.suishouji.utils.RealmUtils;

/**
 * Created by WangZhiYao on 2017/5/6.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_DEFAULT = 1;
    private RealmResults<Bill> mBills;
    private Context mContext;

    public SearchAdapter(Context mContext, RealmResults<Bill> mBills)
    {
        this.mContext = mContext;
        this.mBills = mBills;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        if (viewType == TYPE_DEFAULT)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_list, parent, false);
            return new SearchHolder(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false);
            return new EmptyHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (mBills.size() == 0 || mBills.isEmpty())
        {
            return TYPE_EMPTY;
        }
        else
        {
            return TYPE_DEFAULT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (holder instanceof SearchHolder)
        {
            Bill mBill = mBills.get(position);
            if (TextUtils.equals(mBill.mode, "收入"))
            {
                ((SearchHolder) holder).mIvMode.setImageResource(R.mipmap.ic_income);
            }
            else
            {
                ((SearchHolder) holder).mIvMode.setImageResource(R.mipmap.ic_expend);
            }
            ((SearchHolder) holder).mTvAmount.setText(mBill.amount);
            ((SearchHolder) holder).mTvCategory.setText(mBill.category);
            ((SearchHolder) holder).mTvMethod.setText(mBill.method);
            ((SearchHolder) holder).mTvDate.setText(mBill.date);
            ((SearchHolder) holder).itemView.setOnLongClickListener(view ->
            {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("确定要删除这条账单吗？")
                        .setPositiveButton("确定", (dialogInterface, i) ->
                                RealmUtils.deleteFromBill(mBills, position))
                        .setNegativeButton("取消", null)
                        .show();

                return true;
            });
        }
    }

    @Override
    public int getItemCount()
    {
        return mBills.isEmpty() ? 1 : mBills.size();
    }

    class SearchHolder extends RecyclerView.ViewHolder
    {
        private final ImageView mIvMode;
        private final TextView mTvAmount;
        private final TextView mTvCategory;
        private final TextView mTvMethod;
        private final TextView mTvDate;

        public SearchHolder(View itemView)
        {
            super(itemView);
            mIvMode = (ImageView) itemView.findViewById(R.id.iv_item_mode);
            mTvAmount = (TextView) itemView.findViewById(R.id.tv_item_amount);
            mTvCategory = (TextView) itemView.findViewById(R.id.tv_item_category);
            mTvMethod = (TextView) itemView.findViewById(R.id.tv_item_method);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_item_date);
        }
    }

    class EmptyHolder extends RecyclerView.ViewHolder
    {
        public EmptyHolder(View itemView)
        {
            super(itemView);
        }
    }
}

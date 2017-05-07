package space.levan.suishouji.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import space.levan.suishouji.R;
import space.levan.suishouji.bean.Bill;
import space.levan.suishouji.utils.DateUtils;
import space.levan.suishouji.utils.RealmUtils;

/**
 * 记一记
 *
 * Created by WangZhiYao on 2017/5/5.
 */

public class RecordFragment extends Fragment
{
    @BindView(R.id.et_record_date)
    EditText mEtDate;
    @BindView(R.id.sp_mode)
    Spinner mSpMode;
    @BindView(R.id.tv_category)
    TextView mTvCategory;
    @BindView(R.id.sp_category)
    Spinner mSpCategory;
    @BindView(R.id.tv_method)
    TextView mTvMethod;
    @BindView(R.id.sp_method)
    Spinner mSpMethod;
    @BindView(R.id.et_record_amount)
    EditText mEtAmount;
    @BindView(R.id.et_record_remark)
    EditText mEtRemark;
    Unbinder unbinder;
    @BindView(R.id.btn_save)
    Button mBtnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        unbinder  = ButterKnife.bind(this, view);
        mSpMode.setOnItemSelectedListener(listener);
        return view;
    }

    /**
     * 对Spinner设置选项选中监听
     * 实现Spinner联动
     */
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            switchMode(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView)
        {

        }
    };

    /**
     * 设置收入支出之后的Spinner要显示的内容
     *
     * @param i 被选中的选项 0 = 支出， 1 = 收入
     */
    private void switchMode(int i)
    {
        String[] strCategory;
        ArrayList<String> mListData = new ArrayList<>();
        ArrayAdapter<String> mArrayAdapter;
        if (i == 0)
        {
            strCategory =  getResources().getStringArray(R.array.consumption_category);
            mTvCategory.setText(getString(R.string.fg_record_consumption_category));
            mTvMethod.setText(getString(R.string.fg_record_consumption_method));
        }
        else
        {
            strCategory = getResources().getStringArray(R.array.income_category);
            mTvCategory.setText(getString(R.string.fg_record_income_category));
            mTvMethod.setText(getString(R.string.fg_record_income_method));
        }
        for(int x = 0; x < strCategory.length; x++)
        {
            mListData.add(strCategory[x]);
        }
        mArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, mListData);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpCategory.setAdapter(mArrayAdapter);
    }

    @OnClick({R.id.iv_time_picker, R.id.btn_save})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_time_picker:
                showDatePick();
                break;
            case R.id.btn_save:
                if (!(TextUtils.equals(mEtDate.getText().toString().trim(), "") ||
                        TextUtils.equals(mEtAmount.getText().toString().trim(), "")))
                {
                    saveData();
                }
                else
                {
                    Toast.makeText(getActivity(), "请填写时间或金额", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 弹出时间选择对话框
     */
    private void showDatePick()
    {
        Calendar c = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateListener = (datePicker, i, i1, i2) ->
        {
            if (i > c.get(Calendar.YEAR) || i1 > c.get(Calendar.MONTH))
            {
                Toast.makeText(getContext(), "不允许设置比当前时间晚的日期",
                        Toast.LENGTH_SHORT).show();
            }
            else if ((i == c.get(Calendar.YEAR) && i1 == c.get(Calendar.MONTH))
                    && i2 > c.get(Calendar.DAY_OF_MONTH))
            {
                Toast.makeText(getContext(), "不允许设置比当前时间晚的日期",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                mEtDate.setText(DateUtils.setDate(i, i1, i2));
                mEtDate.setSelection(DateUtils.setDate(i, i1, i2).length());
            }
        };

        new DatePickerDialog(getActivity(),
                dateListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 将数据结构化为Bill实体
     * 再将数据存储进Realm
     */
    private void saveData()
    {
        Bill mBill     = new Bill();
        mBill.date     = mEtDate.getText().toString().trim();
        mBill.mode     = mSpMode.getSelectedItem().toString().trim();
        mBill.category = mSpCategory.getSelectedItem().toString().trim();
        mBill.method   = mSpMethod.getSelectedItem().toString().trim();
        mBill.amount   = Double.parseDouble(mEtAmount.getText().toString().trim());
        mBill.remark   = mEtRemark.getText().toString().trim();
        RealmUtils.addToBill(mBill);
        mBtnSave.setClickable(false);
        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
        Log.w("WZY", mBill.toString());
        mEtDate.setText("");
        mSpMode.setSelection(0);
        mSpCategory.setSelection(0);
        mSpMethod.setSelection(0);
        mEtAmount.setText("");
        mEtRemark.setText("");
        mBtnSave.setClickable(true);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

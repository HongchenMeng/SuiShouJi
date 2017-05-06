package space.levan.suishouji.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import space.levan.suishouji.R;
import space.levan.suishouji.bean.Bill;
import space.levan.suishouji.utils.DateUtils;
import space.levan.suishouji.utils.RealmUtils;
import space.levan.suishouji.view.base.BaseFragment;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class RecordFragment extends BaseFragment
{
    private EditText mEtDate;
    private ImageView mIvTimePicker;
    private TextView mTvCategory;
    private TextView mTvMethod;
    private Spinner mModeSpinner;
    private Spinner mCategorySpinner;
    private Spinner mMethodSpinner;
    private EditText mEtAmount;
    private EditText mEtRemark;
    private Button mBtSave;

    @Override
    protected void initView(View view, Bundle savedInstanceState)
    {
        mEtDate = (EditText) view.findViewById(R.id.et_record_date);
        mEtAmount = (EditText) view.findViewById(R.id.et_record_amount);
        mEtRemark = (EditText) view.findViewById(R.id.et_record_remark);
        mIvTimePicker = (ImageView) view.findViewById(R.id.iv_time_picker);
        mIvTimePicker.setOnClickListener(this);
        mTvCategory = (TextView) view.findViewById(R.id.tv_category);
        mTvMethod = (TextView) view.findViewById(R.id.tv_method);
        mBtSave = (Button) view.findViewById(R.id.btn_save);
        mBtSave.setOnClickListener(this);
        mModeSpinner = (Spinner) view.findViewById(R.id.sp_mode);
        mCategorySpinner = (Spinner) view.findViewById(R.id.sp_category);
        mMethodSpinner = (Spinner) view.findViewById(R.id.sp_method);
        mModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
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
        });
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_record;
    }

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
        mArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mListData);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCategorySpinner.setAdapter(mArrayAdapter);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_time_picker:
                showDatePick();
                break;
            case R.id.btn_save:
                if (!TextUtils.equals(mEtDate.getText().toString().trim(), "") ||
                        !TextUtils.equals(mEtAmount.getText().toString().trim(), ""))
                {
                    saveData();
                }
                else
                {
                    Toast.makeText(getActivity(), "请填写时间或金额", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void showDatePick()
    {
        Calendar c = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateListener = (datePicker, i, i1, i2) ->
        {
            if (i > c.get(Calendar.YEAR) || i1 > c.get(Calendar.MONTH))
            {
                Toast.makeText(getContext(), "不允许设置比当前时间晚的日期", Toast.LENGTH_SHORT).show();
            }
            else if ((i == c.get(Calendar.YEAR) && i1 == c.get(Calendar.MONTH)) && i2 > c.get(Calendar.DAY_OF_MONTH))
            {
                Toast.makeText(getContext(), "不允许设置比当前时间晚的日期", Toast.LENGTH_SHORT).show();
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

    private void saveData()
    {
        Bill mBill = new Bill();
        mBill.date = mEtDate.getText().toString().trim();
        mBill.mode = mModeSpinner.getSelectedItem().toString().trim();
        mBill.category = mCategorySpinner.getSelectedItem().toString().trim();
        mBill.method = mMethodSpinner.getSelectedItem().toString().trim();
        mBill.amount = mEtAmount.getText().toString().trim();
        mBill.remark = mEtRemark.getText().toString().trim();
        RealmUtils.addToBill(mBill);
        mBtSave.setClickable(false);
        Log.w("RecordFragment", mBill.toString());
        Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_SHORT).show();
        mEtDate.setText("");
        mModeSpinner.setSelection(0);
        mCategorySpinner.setSelection(0);
        mMethodSpinner.setSelection(0);
        mEtAmount.setText("");
        mEtRemark.setText("");
        mBtSave.setClickable(true);
    }
}

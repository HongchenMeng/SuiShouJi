package space.levan.suishouji.bean;

import io.realm.RealmObject;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class Bill extends RealmObject
{
    public String date;
    public String mode;
    public String category;
    public String method;
    public String amount;
    public String remark;

    @Override
    public String toString()
    {
        return "date=" + date
                + "mode=" + mode
                + "category=" + category
                + "method=" + method
                + "amount=" + amount
                + "remark=" + remark;
    }
}

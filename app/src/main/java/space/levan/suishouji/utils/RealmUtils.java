package space.levan.suishouji.utils;

import io.realm.Realm;
import io.realm.RealmResults;
import space.levan.suishouji.App;
import space.levan.suishouji.R;
import space.levan.suishouji.bean.Bill;

/**
 * Realm数据库操作工具类
 *
 * Created by WangZhiYao on 2017/5/5.
 */

public class RealmUtils
{
    /**
     * 增加一个账单
     *
     * @param bill Bill实体类
     */
    public static void addToBill(Bill bill)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(bill);
        realm.commitTransaction();
    }

    /**
     * 获取某个月的账单
     *
     * @param date 某个月的时间，格式为yyyy-MM
     * @return
     */
    public static RealmResults<Bill> getBill(String date)
    {
        return Realm.getDefaultInstance()
                .where(Bill.class)
                .contains("date", date)
                .findAll()
                .sort("date");
    }

    /**
     * 获取账单详情
     *
     * @param date 时间，格式为yyyy-MM
     * @param mode 模式，收入或者支出
     * @param category 类别，收入为：工资等 支出为：美食等
     * @return
     */
    public static double getDetail(String date, String mode, String category)
    {
        return Realm.getDefaultInstance()
                .where(Bill.class)
                .contains("date", date)
                .beginGroup()
                .equalTo("mode", mode)
                .equalTo("category", category)
                .endGroup()
                .sum("amount")
                .doubleValue();
    }

    /**
     * 获取一个月内总的支出
     *
     * @param date 时间，格式为：yyyy-MM
     * @return
     */
    public static double getTotalConsumption(String date)
    {
        return Realm.getDefaultInstance()
                .where(Bill.class)
                .contains("date", date)
                .beginGroup()
                .equalTo("mode", "支出")
                .in("category", App.getApplication()
                        .getResources()
                        .getStringArray(R.array.consumption_category))
                .endGroup()
                .sum("amount")
                .doubleValue();
    }

    /**
     * 获取一个月内总的收入
     *
     * @param date 时间，格式为：yyyy-MM
     * @return
     */
    public static double getTotalIncome(String date)
    {
        return Realm.getDefaultInstance()
                .where(Bill.class)
                .contains("date", date)
                .beginGroup()
                .equalTo("mode", "收入")
                .in("category", App.getApplication()
                        .getResources()
                        .getStringArray(R.array.income_category))
                .endGroup()
                .sum("amount")
                .doubleValue();
    }

    /**
     * 获取所有的收入与支出的差
     *
     * @param method 收入与支出的方式，例如：现金
     * @return
     */
    public static double getTotal(String method)
    {
        return (Realm.getDefaultInstance()
                       .where(Bill.class)
                       .equalTo("mode", "收入")
                       .equalTo("method", method)
                       .sum("amount")
                       .doubleValue())
                -
                (Realm.getDefaultInstance()
                        .where(Bill.class)
                        .equalTo("mode", "支出")
                        .equalTo("method", method)
                        .sum("amount")
                        .doubleValue());
    }

    /**
     * 从查询结果中删除某条账单
     *
     * @param results 查询结果
     * @param position 账单的位置
     */
    public static void deleteFromBill(RealmResults<Bill> results, int position)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 ->
        {
            Bill bill = results.get(position);
            bill.deleteFromRealm();
        });
    }
}

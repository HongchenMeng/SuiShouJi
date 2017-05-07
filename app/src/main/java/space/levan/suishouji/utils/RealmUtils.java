package space.levan.suishouji.utils;

import io.realm.Realm;
import io.realm.RealmResults;
import space.levan.suishouji.App;
import space.levan.suishouji.R;
import space.levan.suishouji.bean.Bill;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class RealmUtils
{
    public static void addToBill(Bill bill)
    {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(bill);
        realm.commitTransaction();
    }

    public static RealmResults<Bill> getBill(String date)
    {
        return Realm.getDefaultInstance()
                .where(Bill.class)
                .contains("date", date)
                .findAll()
                .sort("date");
    }

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

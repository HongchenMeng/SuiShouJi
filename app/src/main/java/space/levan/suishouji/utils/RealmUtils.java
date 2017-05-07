package space.levan.suishouji.utils;

import io.realm.Realm;
import io.realm.RealmResults;
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

    public static double getTotal(String date, String mode, String category)
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

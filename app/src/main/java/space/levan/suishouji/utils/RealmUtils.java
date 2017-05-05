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

    public static RealmResults<Bill> getAllBill()
    {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Bill> results = realm.where(Bill.class).findAll();
        return results;
    }

    /*
    public static void deleteBill()
    {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Bill> results = realm.where(Bill.class).findAll();
    }*/
}

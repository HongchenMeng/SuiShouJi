package space.levan.suishouji;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by WangZhiYao on 2017/5/5.
 */

public class App extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initRealm();
    }

    private void initRealm()
    {
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("Bill.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }
}

package com.sampledemo;

import android.app.Application;
import android.preference.PreferenceManager;

import com.sampledemo.models.Item;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myRealm.realm").build();
        Realm.setDefaultConfiguration(config);
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isRealmInitialed", false)) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isRealmInitialed", true).apply();
            Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmList<Item> itemList = new RealmList<>();
                        for(int i=1;i<31;i++) {
                            Item item = new Item();
                            if(i%2 == 0) {
                                item.setCategory("Item Category A");
                                item.setFavorite(true);
                            } else {
                                item.setCategory("Item Category B");
                                item.setFavorite(false);
                            }
                            item.setDescription("Item Description");
                            item.setName("Item Name " + i);
                            item.setId(i);
                            itemList.add(item);
                        }
                        realm.insert(itemList);
                    }
                });

        }
    }
}

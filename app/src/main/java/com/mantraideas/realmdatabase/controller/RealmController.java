package com.mantraideas.realmdatabase.controller;

/**
 * Created by mantraideas on 1/10/17.
 */
import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.mantraideas.realmdatabase.Contacts;
import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {
        realm.beginTransaction();
        realm.clear(Contacts.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<Contacts> getAllContacts() {
        return realm.where(Contacts.class).findAll();
    }

    //query a single item with the given id
    public Contacts findContactById(String id) {
        return realm.where(Contacts.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasContacts() {
        return !realm.allObjects(Contacts.class).isEmpty();
    }

    //query example
//    public RealmResults<Contacts> querye() {
//        return realm.where(Contacts.class)
//                .contains("author", "Author 0")
//                .or()
//                .contains("title", "Realm")
//                .findAll();
//
//    }
}

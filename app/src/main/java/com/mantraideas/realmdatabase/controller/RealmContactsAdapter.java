package com.mantraideas.realmdatabase.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mantraideas.realmdatabase.Contacts;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by mantraideas on 1/10/17.
 */
public class RealmContactsAdapter extends RealmBaseAdapter<Contacts> {
    public RealmContactsAdapter(Context context, RealmResults<Contacts> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

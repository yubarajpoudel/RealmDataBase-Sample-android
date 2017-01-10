package com.mantraideas.realmdatabase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mantraideas.realmdatabase.controller.RealmContactsAdapter;
import com.mantraideas.realmdatabase.controller.RealmController;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ContactsAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recycler);

        //get realm instance
        this.realm = RealmController.with(this).getRealm();

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }

        // refresh the realm instance
        RealmController.with(this).refresh();
        // get all persisted objects
        // create the helper adapter and notify data set changes
        // changes will be reflected automatically
        setRealmAdapter(RealmController.with(this).getAllContacts());

        Toast.makeText(this, "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();

        //add new item

    }

    public void setRealmAdapter(RealmResults<Contacts> contacts) {

        RealmContactsAdapter realmAdapter = new RealmContactsAdapter(this.getApplicationContext(),contacts, true);
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new ContactsAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<Contacts> contactsArrayList = new ArrayList<>();

        Contacts contact = new Contacts();
        contact.setId(1 + System.currentTimeMillis());
        contact.setName("Roshan Shrestha");
        contact.setEmail("hello@theroshan.com");
        contact.setDesignation("Managing Director");
        contact.setImageUrl("http://www.mantraideas.com/image.php?src=http://www.mantraideas.com/explorer/data/files/staffs/5867be077f41f.jpg&width=280&h=200");
        contactsArrayList.add(contact);

        Contacts contact1 = new Contacts();
        contact1.setId(2 + System.currentTimeMillis());
        contact1.setName("Ashok Basnet");
        contact1.setDesignation("Chief Executive Officer");
        contact1.setEmail("mail@ashokbasnet.com.np");
        contact1.setImageUrl("http://www.mantraideas.com/image.php?src=http://www.mantraideas.com/explorer/data/files/staffs/5867bdd08a102.jpg&width=280&h=200");
        contactsArrayList.add(contact1);

        Contacts contact2 = new Contacts();
        contact2.setId(2 + System.currentTimeMillis());
        contact2.setName("Hem Shrestha");
        contact2.setDesignation("Chief Technical Officer");
        contact2.setEmail("hereshem@gmail.com");
        contact2.setImageUrl("http://www.mantraideas.com/image.php?src=http://www.mantraideas.com/explorer/data/files/staffs/56659474b7da8.jpg&width=280&h=200");
        contactsArrayList.add(contact2);

        Contacts contact3 = new Contacts();
        contact3.setId(2 + System.currentTimeMillis());
        contact3.setName("Yubaraj Poudel");
        contact3.setDesignation("Chief Operating Officer");
        contact3.setEmail("yubarajpoudel708@gmail.com");
        contact3.setImageUrl("http://www.mantraideas.com/image.php?src=http://www.mantraideas.com/explorer/data/files/staffs/5867c32d6589b.jpg&width=280&h=200");
        contactsArrayList.add(contact3);



        for (Contacts c : contactsArrayList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(c);
            realm.commitTransaction();
        }

        Prefs.with(this).setPreLoad(true);

    }
}
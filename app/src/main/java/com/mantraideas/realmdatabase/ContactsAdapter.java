package com.mantraideas.realmdatabase;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mantraideas.realmdatabase.controller.RealmController;
import com.mantraideas.realmdatabase.controller.RealmRecyclerViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mantraideas on 1/10/17.
 */
public class ContactsAdapter extends RealmRecyclerViewAdapter<Contacts> {
    final Context context;
    private Realm realm;
    private LayoutInflater inflater;

    public ContactsAdapter(Context context) {

        this.context = context;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        realm = RealmController.getInstance().getRealm();
        // get the article
        final Contacts contact = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.textTitle.setText(contact.getName());
        holder.textDesignation.setText(contact.getDesignation());
        holder.textemail.setText(contact.getEmail());
        // load the background image
        if (contact.getImageUrl() != null) {
            Log.d("ContactsAdapter", "imageUrl = " + contact.getImageUrl());
            Picasso.with(context)
                    .load(contact.getImageUrl())
                    .into(holder.imageBackground, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("ContactsAdapter", "success");
                        }

                        @Override
                        public void onError() {
                            Log.d("ContactsAdapter", "failed");
                        }
                    });

        }

        //remove single match from realm
        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                RealmResults<Contacts> results = realm.where(Contacts.class).findAll();
                // Get the book title to show it in toast message
                Contacts b = results.get(position);
                String title = b.getName();

                // All changes to data must happen in a transaction
                realm.beginTransaction();

                // remove single match
                results.remove(position);
                realm.commitTransaction();
//
                if (results.size() == 0) {
                    Prefs.with(context).setPreLoad(false);
                }

                notifyDataSetChanged();

                Toast.makeText(context, title + " is removed from Realm", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView textTitle;
        public TextView textemail;
        public TextView textDesignation;
        public ImageView imageBackground;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_books);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textemail = (TextView) itemView.findViewById(R.id.text_email);
            textDesignation = (TextView) itemView.findViewById(R.id.text_designation);
            imageBackground = (ImageView) itemView.findViewById(R.id.image_background);
        }
    }
}
package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.ContactInfo;
import com.olek.testify.recycleview.viewholder.ContactViewHolder;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private List<ContactInfo> contactList;

    public ContactAdapter(List<ContactInfo> contactList, Context context) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ContactInfo ci = contactList.get(i);

        contactViewHolder.vName.setText(ci.getName());
        contactViewHolder.vSurname.setText(ci.getSurname());
        contactViewHolder.vEmail.setText(ci.getEmail());
        contactViewHolder.vTitle.setText(ci.getName() + " " + ci.getSurname());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.example_card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }


}
package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olek.testify.R;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView vName;
    public TextView vSurname;
    public TextView vEmail;
    public TextView vTitle;


    public ContactViewHolder(View v) {
        super(v);

        vName = (TextView) v.findViewById(R.id.txtName);
        vSurname = (TextView) v.findViewById(R.id.txtSurname);
        vEmail = (TextView) v.findViewById(R.id.txtEmail);
        vTitle = (TextView) v.findViewById(R.id.title);
    }
}
package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olek.testify.R;

public class SubjectViewHolder extends RecyclerView.ViewHolder {

    public TextView mName;
    public TextView mDescription;


    public SubjectViewHolder(View v) {
        super(v);
        mName = (TextView) v.findViewById(R.id.subject_title);
        mDescription = (TextView) v.findViewById(R.id.subject_desc);
    }
}

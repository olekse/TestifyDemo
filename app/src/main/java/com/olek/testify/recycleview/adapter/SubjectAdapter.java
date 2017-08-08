package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.Subject;
import com.olek.testify.recycleview.viewholder.SubjectViewHolder;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectViewHolder> {

    private List<Subject> subjectList;

    public SubjectAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.subject_card_layout, viewGroup, false);

        return new SubjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject ci = subjectList.get(position);

        holder.mName.setText(ci.getName());
        holder.mDescription.setText(ci.getDescription());
    }
}

package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.Course;
import com.olek.testify.model.Group;
import com.olek.testify.model.Language;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.recycleview.viewholder.CourseViewHolder;
import com.olek.testify.recycleview.viewholder.GroupViewHolder;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

    private List<Group> groupList;
    private Context mContext;

    public GroupAdapter(List<Group> groupList, Context context) {
        this.groupList = groupList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.group_card_layout, viewGroup, false);

        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = groupList.get(position);

        Language language = (Language) TableAdapter.getBy("ID", String.valueOf(group.getId_lang()), mContext, Language.class);

        holder.codeNameView.setText(group.getCode_name());
        holder.descriptionView.setText(group.getText());
        holder.language.setText(language == null ? "" : language.getName());

    }

}

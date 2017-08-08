package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.Course;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.recycleview.viewholder.CourseViewHolder;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    private List<Course> courseList;
    private Context mContext;

    public CourseAdapter(List<Course> courseList, Context context) {
        this.courseList = courseList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.course_card_layout, viewGroup, false);

        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = courseList.get(position);

        holder.mSemester.setText(String.valueOf(course.getSemester()));

        Subject subject = (Subject) TableAdapter.getBy("ID", String.valueOf(course.getId_subject()), mContext, Subject.class);

        holder.subjectName.setText(subject.getName());
    }

}

package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.Course;
import com.olek.testify.model.Student;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.model.Test;
import com.olek.testify.recycleview.viewholder.TestViewHolder;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

    private List<Test> testList;
    private Context mContext;

    public TestAdapter(List<Test> courseList, Context context) {
        this.testList = courseList;
        this.mContext = context;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.student_card_layout, parent, false);

        return new TestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        Test test = testList.get(position);

        holder.testName.setText(test.getName());
        holder.testDescription.setText(test.getDescription());

        Course course = (Course) TableAdapter.getBy("ID", String.valueOf(test.getId_course()), mContext, Course.class);

        Subject subject = (Subject) TableAdapter.getBy("ID", String.valueOf(course.getId_subject()), mContext, Subject.class);

        holder.courseName.setText(subject.getName() + "/SEM: " + course.getSemester());
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    /*

        public TextView testName;
        public TextView testDescription;
        public TextView courseName;



        public TestViewHolder(View v) {
            super(v);


            testName =  (TextView) v.findViewById(R.id.card_test_name);
            testDescription = (TextView)  v.findViewById(R.id.card_test_description);
            courseName = (TextView)  v.findViewById(R.id.card_test_course);
        }
    */

}

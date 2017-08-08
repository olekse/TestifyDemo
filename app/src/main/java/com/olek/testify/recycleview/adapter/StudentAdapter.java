package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.Course;
import com.olek.testify.model.Group;
import com.olek.testify.model.Student;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.recycleview.viewholder.StudentViewHolder;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private List<Student> studentList;
    private Context mContext;

    public StudentAdapter(List<Student> courseList, Context context) {
        this.studentList = courseList;
        this.mContext = context;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.student_card_layout, parent, false);

        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = studentList.get(position);

        holder.mName.setText(student.getName());
        holder.mSurname.setText(student.getSurname());
        holder.mEmail.setText(student.getEmail());

        Group studentsGroup = (Group) TableAdapter.getBy("ID", String.valueOf(student.getId_studentGroup()), mContext, Group.class);

        holder.mStudentGroupCodeName.setText(studentsGroup.getCode_name());
        holder.mName.setText(student.getName());
        holder.mStudentPhoto.setImageBitmap(BitmapFactory.decodeByteArray(student.getImage(), 0, student.getImage().length));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    /*

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = studentList.get(position);

        holder.mSemester.setText(String.valueOf(course.getSemester()));

        Subject subject = (Subject) TableAdapter.getBy("ID", String.valueOf(course.getId_subject()), mContext, Subject.class);

        holder.subjectName.setText(subject.getName());
    }
    */

}

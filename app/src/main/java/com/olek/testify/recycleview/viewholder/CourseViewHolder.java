package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olek.testify.R;


/*

   ID   INT              NOT NULL,
   SEMESTER INT		 NOT NULL,
   ID_SUBJECT INT      NOT NULL,
   PRIMARY KEY (ID),
   FOREIGN KEY (ID_SUBJECT) REFERENCES Subject(ID)
 */

public class CourseViewHolder extends RecyclerView.ViewHolder {


    public TextView mSemester;
    public TextView subjectName;

    public CourseViewHolder(View v) {
        super(v);


        mSemester = (TextView) v.findViewById(R.id.course_semester);
        subjectName = (TextView) v.findViewById(R.id.course_subject_name);
    }
}

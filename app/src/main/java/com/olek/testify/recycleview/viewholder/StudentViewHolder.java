package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.olek.testify.R;

import org.w3c.dom.Text;


public class StudentViewHolder extends RecyclerView.ViewHolder {


    public TextView mName;
    public TextView mSurname;
    public TextView mEmail;
    public TextView mStudentGroupCodeName;
    public ImageView mStudentPhoto;

    public StudentViewHolder(View v) {
        super(v);

        mName = (TextView) v.findViewById(R.id.student_name_textField);
        mSurname = (TextView) v.findViewById(R.id.student_surnameTextField);
        mEmail = (TextView) v.findViewById(R.id.student_email_textView);
        mStudentGroupCodeName = (TextView) v.findViewById(R.id.student_group_code_name);
        mStudentPhoto = (ImageView) v.findViewById(R.id.student_image);

    }
}

package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olek.testify.R;


/*


 */

public class TestViewHolder extends RecyclerView.ViewHolder {

    /*
        ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
        NAME VARCHAR (36)     NOT NULL,
        DESCRIPTION TEXT NOT NULL,
        ID_COURSE INT NOT NULL,
        FOREIGN KEY (ID_COURSE) REFERENCES Course(ID)
     */


    public TextView testName;
    public TextView testDescription;
    public TextView courseName;


    public TestViewHolder(View v) {
        super(v);


        testName = (TextView) v.findViewById(R.id.card_test_name);
        testDescription = (TextView) v.findViewById(R.id.card_test_description);
        courseName = (TextView) v.findViewById(R.id.card_test_course);
    }
}

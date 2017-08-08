package com.olek.testify.recycleview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.olek.testify.R;


/*

    CREATE TABLE Answer (
	ID INT NOT NULL,
    IS_CORRECT BOOLEAN NOT NULL,
    ID_TASK INT NOT NULL,
    KEY_CODE VARCHAR (36),
    ANSWER_TEXT TEXT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (ID_TASK) REFERENCES Task(ID)
);

CREATE TABLE Task (
	ID INT NOT NULL,
    TEXT TEXT     NOT NULL,
    NUMBER INT ,
    ID_TEST INT NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (ID_TEST) REFERENCES Test(ID)
);
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    public TextView question;
    public TextView correctAnswer;
    public TextView numberOfAnswers;


    public TaskViewHolder(View v) {
        super(v);

        question = (TextView) v.findViewById(R.id.task_card_question_W);
        correctAnswer = (TextView) v.findViewById(R.id.task_card_correct_answer_W);
        numberOfAnswers = (TextView) v.findViewById(R.id.task_card_number_of_answers_W);
    }
}

package com.olek.testify.recycleview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olek.testify.R;
import com.olek.testify.model.Answer;
import com.olek.testify.model.Task;
import com.olek.testify.recycleview.viewholder.TaskViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    Map<Task, List<Answer>> taskListMap;
    private List<Task> taskList;
    private Context mContext;

    public TaskAdapter(Map<Task, List<Answer>> taskListMap, Context context) {
        this.taskList = new ArrayList<>(taskListMap.keySet());
        this.taskListMap = taskListMap;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.group_card_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        Answer correct = null;
        for (Answer answer : taskListMap.get(task)) {
            if (answer.isCorrect()) {
                correct = answer;
            }
        }

        if (correct == null) {
            correct = new Answer();
            correct.setAnswerText("--//--");
        }

        holder.correctAnswer.setText(correct.getAnswerText());
        holder.numberOfAnswers.setText(String.valueOf(taskListMap.get(task).size()));
        holder.question.setText(task.getText());
    }

}

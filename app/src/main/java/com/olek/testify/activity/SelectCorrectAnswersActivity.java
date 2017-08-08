package com.olek.testify.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.olek.testify.R;
import com.olek.testify.activity.NexusFragments.ConfirmTestAddition;
import com.olek.testify.activity.NexusFragments.TestFormFragment;
import com.olek.testify.adapters.CustomExpandableListAdapter;
import com.olek.testify.model.Answer;
import com.olek.testify.model.Task;
import com.olek.testify.model.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCorrectAnswersActivity extends AppCompatActivity {

    public static final String KEY_HASHMAP_RESULTS = "hms";

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;



    // Group -- Element
    Map<Integer, Integer> selectedChildren;

    Test mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_correct_answers);

        selectedChildren = new HashMap<>();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle == null || bundle.isEmpty()) finish();

        Map<Task, List<Answer>> finalMapW = (Map<Task, List<Answer>>) bundle.get(KEY_HASHMAP_RESULTS);

        final Map<Task, List<Answer>> finalMap = finalMapW;

        mTest = (Test) bundle.get(TestFormFragment.EXTRA_TEST_KEY);

        expandableListDetail = (HashMap<String, List<String>>) convertToStringMap(finalMap);
        //expandableListTitle = new ArrayList<>(expandableListDetail.keySet());


        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();

                if (!selectedChildren.containsKey(groupPosition)){
                    selectedChildren.put(groupPosition, childPosition);
                    v.setBackgroundColor(Color.LTGRAY);
                } else {
                    Integer previousSelected = selectedChildren.get(groupPosition);
                    parent.getChildAt(previousSelected).setBackgroundColor(Color.WHITE);
                }

                if (selectedChildren.keySet().size() == expandableListDetail.keySet().size()){

                    // convert maps
                    Map<Task, Answer> correctAnswers = new HashMap<>();

                    List<Task> list = new ArrayList<Task>(finalMap.keySet());




                    for(Integer group : selectedChildren.keySet()){
                        correctAnswers.put(list.get(group), finalMap.get(list.get(group)).get(selectedChildren.get(group)));
                    }



                    //
                    Intent finishAddTestIntent = new Intent(SelectCorrectAnswersActivity.this, ConfirmTestAddition.class);
                    final Map<Task, List<Answer>> finalHashMap = finalMap;
                    finishAddTestIntent.putExtra(ConfirmTestAddition.BUNDLE_TASKS_MAP, (Serializable) finalHashMap);
                    finishAddTestIntent.putExtra(ConfirmTestAddition.BUNDLE_SELECTED_TASKS_MAP, (Serializable)selectedChildren );
                    finishAddTestIntent.putExtra(TestFormFragment.EXTRA_TEST_KEY, mTest);
                    finishAddTestIntent.putExtra(ConfirmTestAddition.BUNDLE_ANSWER_MAP, (Serializable)correctAnswers);
                    startActivity(finishAddTestIntent);
                }


                return false;
            }
        });
    }

    private Map<String, List<String>> convertToStringMap(Map<Task, List<Answer>> input){
        Map<String, List<String>> resultMap = new HashMap<>();

        for(Task currentTask : input.keySet()){
            List<String> tmp = new ArrayList<>();

            for(Answer ans : input.get(currentTask)){
                tmp.add(ans.getAnswerText());
            }

            resultMap.put(currentTask.getText(), tmp);
        }


        return resultMap;
    }

}

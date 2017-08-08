package com.olek.testify.activity.NexusFragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.olek.testify.R;
import com.olek.testify.model.Answer;
import com.olek.testify.model.Student;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.model.Task;
import com.olek.testify.model.Test;
import com.olek.testify.recycleview.adapter.StudentAdapter;
import com.olek.testify.recycleview.adapter.TaskAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ConfirmTestAddition extends AppCompatActivity {

    public static final String BUNDLE_TASKS_MAP = "bundleTask";
    public static final String BUNDLE_SELECTED_TASKS_MAP = "bundleSelectedTasks";
    public static final String BUNDLE_ANSWER_MAP = "bundleAnswerMap";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_test_addition);

        Bundle bundle = getIntent().getExtras();

        /*
            finishAddTestIntent.putExtra(ConfirmTestAddition.BUNDLE_TASKS_MAP, (Serializable) finalHashMap);
            finishAddTestIntent.putExtra(ConfirmTestAddition.BUNDLE_SELECTED_TASKS_MAP, (Serializable)selectedChildren );
            finishAddTestIntent.putExtra(TestFormFragment.EXTRA_TEST_KEY, mTest);
        */



        Map<Task, List<Answer>> finalHashMap = (Map<Task, List<Answer>>) bundle.get(BUNDLE_TASKS_MAP);
        Map<Integer, Integer> selectedChildren = (Map<Integer, Integer>) bundle.get(BUNDLE_SELECTED_TASKS_MAP);
        com.olek.testify.model.Test test = (com.olek.testify.model.Test) bundle.get(TestFormFragment.EXTRA_TEST_KEY);





        // checkAnswers

        RecyclerView recList = (RecyclerView) findViewById(R.id.answer_recycler_view_W);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        /*
        Map<Course, Integer> map = TableAdapter.getAllFromTableAsMap(this, Course.class);
        CourseAdapter adapter = new CourseAdapter(new ArrayList<>(map.keySet()), this.getApplication());
        recList.setAdapter(adapter);
        */

        TaskAdapter taskA = new TaskAdapter(finalHashMap, this);
        recList.setAdapter(taskA);
    }


}

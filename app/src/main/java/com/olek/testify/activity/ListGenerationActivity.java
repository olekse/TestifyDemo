package com.olek.testify.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.olek.testify.R;
import com.olek.testify.activity.NexusFragments.CourseFormFragment;
import com.olek.testify.activity.NexusFragments.FormGenerationActivity;
import com.olek.testify.activity.NexusFragments.GroupFormFragment;
import com.olek.testify.activity.NexusFragments.StudentFormFragment;
import com.olek.testify.activity.NexusFragments.SubjectFormFragment;
import com.olek.testify.activity.NexusFragments.TestFormFragment;
import com.olek.testify.model.Course;
import com.olek.testify.model.Group;
import com.olek.testify.model.Student;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.model.Test;
import com.olek.testify.recycleview.adapter.CourseAdapter;
import com.olek.testify.recycleview.adapter.GroupAdapter;
import com.olek.testify.recycleview.adapter.StudentAdapter;
import com.olek.testify.recycleview.adapter.SubjectAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.olek.testify.activity.NexusFragments.FormGenerationActivity.FormType;

public class ListGenerationActivity extends FragmentActivity {


    private static Map<FormType, Class> formMap = new HashMap<>();
    private static Map<FormType, String> titles = new HashMap<>();
    private static Map<FormType, Class> adapters = new HashMap<>();
    private static Map<FormType, Class> types = new HashMap<>();

    public static final int GENERAL_RETURN = 1;

    static{
        formMap.put(FormType.GROUP_FRAGMENT_KEY, GroupFormFragment.class);
        formMap.put(FormType.STUDENT_FRAGMENT_KEY, StudentFormFragment.class);
        formMap.put(FormType.SUBJECT_FRAGMENT_KEY, SubjectFormFragment.class);
        formMap.put(FormType.COURSE_FRAGMENT_KEY, CourseFormFragment.class);
        formMap.put(FormType.TEST_FRAGMENT_KEY, TestFormFragment.class);

        titles.put(FormType.GROUP_FRAGMENT_KEY, "Groups");
        titles.put(FormType.STUDENT_FRAGMENT_KEY, "Students");
        titles.put(FormType.SUBJECT_FRAGMENT_KEY, "Subjects");
        titles.put(FormType.COURSE_FRAGMENT_KEY, "Courses");
        titles.put(FormType.TEST_FRAGMENT_KEY, "Tests");

        types.put(FormType.COURSE_FRAGMENT_KEY, Course.class);
        types.put(FormType.STUDENT_FRAGMENT_KEY, Student.class);
        types.put(FormType.SUBJECT_FRAGMENT_KEY, Subject.class);
        types.put(FormType.GROUP_FRAGMENT_KEY, Group.class);
        types.put(FormType.TEST_FRAGMENT_KEY, Test.class);

        adapters.put(FormType.COURSE_FRAGMENT_KEY, CourseAdapter.class);
        adapters.put(FormType.STUDENT_FRAGMENT_KEY, StudentAdapter.class);
        adapters.put(FormType.SUBJECT_FRAGMENT_KEY, SubjectAdapter.class);
        adapters.put(FormType.GROUP_FRAGMENT_KEY, GroupAdapter.class);
        adapters.put(FormType.TEST_FRAGMENT_KEY, Test.class);
    }

    private void setUpRecyclerView(FormType type) {
        RecyclerView recList = (RecyclerView) findViewById(R.id.template_recycler_view);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        TextView listTemplateLabel = (TextView) findViewById(R.id.list_template_label);
        listTemplateLabel.setText(titles.get(type));

        setUpDynamic(type, recList);
    }


    private <T extends TableAdapter> void setUpDynamic(FormType type, RecyclerView recyclerView){

        Class tClass = types.get(type);


        Map<T, Integer> objects = TableAdapter.getAllFromTableAsMap(getApplication(), tClass);
        RecyclerView.Adapter adapter = null;
        Class adapterClass = adapters.get(type);
        Constructor constructor = null;
        try {

            constructor = adapterClass.getConstructor(List.class, Context.class);
            adapter = (RecyclerView.Adapter) constructor.newInstance(new ArrayList<>(objects.keySet()), getApplication());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GENERAL_RETURN){
            this.recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_creation_layout);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle == null) throw new IllegalStateException("You should pass a type of Form you want to create!");
        final FormType type = (FormType) bundle.get(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(ListGenerationActivity.this, FormGenerationActivity.class);
                createIntent.putExtra(FormGenerationActivity.FORM_TYPE_ARGUMENT_KEY, type);
                startActivityForResult(createIntent, GENERAL_RETURN);
            }
        });


        setUpRecyclerView(type);
    }

    @NonNull
      private <T extends Fragment> T getFragment(FormType type) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        FragmentManager fm = getSupportFragmentManager();

        Fragment listFragment = null;


        Fragment retainedFragment = fm.findFragmentByTag(type.type);
        if (retainedFragment != null && retainedFragment instanceof TemplateListFragment) {
            listFragment = (T) retainedFragment;
        } else {

                listFragment = new TemplateListFragment();

            fm.beginTransaction()
                    .add(R.id.template_recycler_view, listFragment, type.type)
                    .commit();
        }
        return (T) listFragment;
    }


}
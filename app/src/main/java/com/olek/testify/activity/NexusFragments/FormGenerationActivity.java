package com.olek.testify.activity.NexusFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.FormFragment;
import com.olek.testify.R;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FormGenerationActivity extends FragmentActivity {

    public static final String FORM_TYPE_ARGUMENT_KEY = "formTypeArgument";


    private static Map<FormType, Class> formMap = new HashMap<>();

    static{
        formMap.put(FormType.GROUP_FRAGMENT_KEY, GroupFormFragment.class);
        formMap.put(FormType.STUDENT_FRAGMENT_KEY, StudentFormFragment.class);
        formMap.put(FormType.SUBJECT_FRAGMENT_KEY, SubjectFormFragment.class);
        formMap.put(FormType.COURSE_FRAGMENT_KEY, CourseFormFragment.class);
        formMap.put(FormType.TEST_FRAGMENT_KEY, TestFormFragment.class);
    }

    public enum FormType {
         GROUP_FRAGMENT_KEY("group_form_fragment_key"),
         STUDENT_FRAGMENT_KEY("student_from_fragment_key"),
         SUBJECT_FRAGMENT_KEY("subject_from_fragment_key"),
         COURSE_FRAGMENT_KEY("course_from_fragment_key"),
         TEST_FRAGMENT_KEY("test_from_fragment_key"),
         RESULTS_FRAGMENT_KEY("result_from_fragment_key");


        public final String type;

        private FormType(final String text) {
            this.type = text;
        }

        @Override
        public String toString() {
            return type;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_creation_layout);


        //AndroidBug5497Workaround.assistActivity(this);

        Bundle bundle = this.getIntent().getExtras();

        if(bundle == null) throw new IllegalStateException("You should pass a type of Form you want to create!");

        FormType type = (FormGenerationActivity.FormType) bundle.get(FORM_TYPE_ARGUMENT_KEY);


        try {
            setSubmitAction((TemplateFormFragment) getFragment(type));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void setSubmitAction(final TemplateFormFragment formFragment) {
        Button submitButton = (Button) findViewById(R.id.save_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formFragment.validate();
            }
        });
    }


    @NonNull
      private <T extends TemplateFormFragment> T getFragment(FormType type) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        FragmentManager fm = getSupportFragmentManager();

        Class<? extends FormFragment> fragmentType = formMap.get(type);

        FormFragment formFragment = null;




        Fragment retainedFragment = fm.findFragmentByTag(type.type);
        if (retainedFragment != null && retainedFragment instanceof TemplateFormFragment) {
            formFragment = (T) retainedFragment;
        } else {
            try {
                formFragment = fragmentType.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }


            fm.beginTransaction()
                    .add(R.id.form_base_layout, formFragment, type.type)
                    .commit();
        }
        return (T) formFragment;
    }


}
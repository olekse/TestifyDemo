package com.olek.testify.activity.NexusFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.olek.testify.activity.TestCameraActivity;
import com.olek.testify.model.Course;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;
import com.olek.testify.model.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestFormFragment extends TemplateFormFragment implements AdapterView.OnItemSelectedListener {


    Map<String, Course> courseMap = new HashMap<>();
   // Map<String, Subject> subjectMap = new HashMap<>();
    /*
    	ID INT NOT NULL,
        NAME VARCHAR (36)     NOT NULL,
        DESCRIPTION TEXT NOT NULL,
        ID_COURSE INT NOT NULL,
        PRIMARY KEY (ID),
        FOREIGN KEY (ID_COURSE) REFERENCES Course(ID)
     */

    public static final String TEST_NAME = "testName";
    public static final String TEST_DESC = "testDesc";
    public static final String TEST_COURSE = "testCourse";
    public static final String TEST_SUBJECT_SELECTOR = "testSubject";

    public static final String EXTRA_TEST_KEY = "extraTestKey";

    private FormController mController = null;
    private Spinner mSubjectSpinner;

    /*
    public static final String FIRST_NAME = "firstName";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String GROUP = "group";
    */

    public boolean validate() {
        getFormController().resetValidationErrors();
        if (getFormController().isValidInput()) {

            Object testName = getModel().getValue(TEST_NAME);
            Object testDesc = getModel().getValue(TEST_DESC);
            Object testCourse = getModel().getValue(TEST_COURSE);
            Object testSubject = getModel().getValue(TEST_SUBJECT_SELECTOR);

            /*
            Object firstName = getModel().getValue(FIRST_NAME);
            Object surname = getModel().getValue(SURNAME);
            Object email = getModel().getValue(EMAIL);
            Object group = getModel().getValue(GROUP);
            */
            //Object imageBlob = getModel().getValue(CUSTOM_ELEM);

            Test test = new Test();

            test.setName((String) testName);
            test.setDescription((String) testDesc);
            test.setId_course(courseMap.get((String)testCourse).getId());

            if (test.insert(getContext(), "ID", true)){
                Intent intent = new Intent(getContext(), TestCameraActivity.class);
                intent.putExtra(EXTRA_TEST_KEY, test);
                startActivity(intent);
            }



        } else {
            getFormController().showValidationErrors();
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    Context mContext;

    private Map<Subject, Integer> subjects;
    private Set<Subject> subjectSet;
    private Map<Course, Integer> courseIntegerMap;
    private Map<Integer, Subject> mIntegerSubjectMap;
    private FormSectionController section;
    private SelectionController subjectSelectionController;
    private AdapterView.OnItemSelectedListener baseListener;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mSubjectSpinner = subjectSelectionController.getSpinner();
        try{
            baseListener = mSubjectSpinner.getOnItemSelectedListener();
            mSubjectSpinner.setOnItemSelectedListener(TestFormFragment.this);
        } catch (Exception ex){
            ex.printStackTrace();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return super.onCreateView(inflater, container, savedInstanceState);


    }

    private HashSet<InputValidator> validators;

    @Override
    public void initForm(FormController controller) {
        mController = controller;
        Context ctxt = getContext();
        mContext = ctxt;

        InputValidator iv = new RequiredFieldValidator();
        validators = new HashSet<>();
        validators.add(iv);

        section = new FormSectionController(ctxt, "Create New Test");

        //

        /*
           ID   INT              NOT NULL,
           NAME VARCHAR (36)     NOT NULL,
           SURNAME VARCHAR (36)  NOT NULL,
           EMAIL VARCHAR (46)    NOT NULL,
           IMAGE BLOB NOT NULL,
           ID_StudentGroup INT,
         */


        subjects = TableAdapter.getAllFromTableAsMap(ctxt, Subject.class);
        courseIntegerMap = TableAdapter.getAllFromTableAsMap(ctxt, Course.class);

        subjectSet = subjects.keySet();

        Iterator<Subject> subjectIterator = subjectSet.iterator();

        while (subjectIterator.hasNext()) {
            Subject tmpSubject = subjectIterator.next();


            boolean delete = true;

            for (Course course : courseIntegerMap.keySet()) {
                if (tmpSubject.getId() == course.getId_subject()) {
                    delete = false;
                    break;
                }
            }

            if (delete) {
                subjectIterator.remove();
            }
        }

        // now I deleted every subject that doesn't have a course associated with it

        List<String> subjectNames = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();

        mIntegerSubjectMap = new HashMap<>();

        int subjectCounter = 0;
        for (Subject subject : subjectSet) {
            subjectNames.add(subject.getName());
            mIntegerSubjectMap.put(subjectCounter++, subject);
        }




        for(Course course : courseIntegerMap.keySet()){
            courseMap.put(String.valueOf(course.getSemester()), course);

            //courseNames.add(String.valueOf(course.getSemester()));
        }

        EditTextController testName = new EditTextController(ctxt, TEST_NAME, "Test Title");
        EditTextController testDesc = new EditTextController(ctxt, TEST_DESC, "Test Description");
        //EditTextController emailTextController = new EditTextController(ctxt, TEST_SUBJECT, "Subject");
        subjectSelectionController = new SelectionController(ctxt, TEST_SUBJECT_SELECTOR, "Subject", true, "Select", subjectNames, true);
        // SelectionController groupSelectionController = new SelectionController(ctxt, GROUP, "Group", true, "Select", groupNames, true);

        section.addElement(testName);
        section.addElement(testDesc);
        section.addElement(subjectSelectionController);

        //section.addElement(surnameTextController);
        //section.addElement(firstNameTextController);
        //section.addElement(emailTextController);
        //section.addElement(groupSelectionController);

        testName.setValidators(validators);
        testDesc.setValidators(validators);
        //subjectSelectionController.setValidators(validators);

        controller.addSection(section);
    }



    private SelectionController groupSelectionController;

    private void addOrUpdateCourseSelector(int i){

        Subject selectedSubject = mIntegerSubjectMap.get(i - 1);

        // now find all courses for that subject

        List<Course> courses = (List<Course>)(List<?>)TableAdapter.getListBy("ID_SUBJECT", String.valueOf(selectedSubject.getId()), mContext, Course.class);

        List<String> courseNames = new ArrayList<>();

        for(Course course : courses){
            courseNames.add(String.valueOf(course.getSemester()));
        }

        boolean doesExists = (groupSelectionController != null);

        groupSelectionController = new SelectionController(mContext, TEST_COURSE , "Course", true, "Select", courseNames, true);

        if (!doesExists){
            section.addElement(groupSelectionController);
            //groupSelectionController.setValidators(validators);
        }

        recreateViews();

        try {
            //section.refresh();
            //mController.refreshElements();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        //if (view == mSubjectSpinner){
            if (i > 0){
                addOrUpdateCourseSelector(i);
            }


        baseListener.onItemSelected(adapterView, view, i, l);

      //  }
    }

    /*

    @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object value;
                // if no values are specified, set the index on the model
                if (values == null) {
                    value = pos;
                } else {
                    // pos of 0 indicates nothing is selected
                    if (pos == 0) {
                        value = null;
                    } else {    // if something is selected, set the value on the model
                        value = values.get(pos-1);
                    }
                }

                getModel().setValue(getName(), value);
            }
     */

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
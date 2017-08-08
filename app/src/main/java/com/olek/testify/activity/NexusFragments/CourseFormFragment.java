package com.olek.testify.activity.NexusFragments;


import android.content.Context;
import android.text.InputType;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.olek.testify.model.Course;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CourseFormFragment extends TemplateFormFragment {


    /*
       ID   INT              NOT NULL,
       SEMESTER INT		 NOT NULL,
       ID_SUBJECT INT      NOT NULL,
       PRIMARY KEY (ID),
       FOREIGN KEY (ID_SUBJECT) REFERENCES Subject(ID)
     */


    //Map<String, Language> selector = new HashMap<>();
    Map<String, Subject> subjectMap = new HashMap<>();

    public static final String SEMESTER = "semester";
    public static final String COURSE_SUBJECT = "surname";

    public boolean validate() {
        getFormController().resetValidationErrors();
        if (getFormController().isValidInput()) {

            Object semester = getModel().getValue(SEMESTER);
            Object course_subject = getModel().getValue(COURSE_SUBJECT);

            Course course = new Course();
            course.setSemester(Integer.valueOf((String) semester));
            course.setId_subject(subjectMap.get((String)course_subject).getId());

            course.insert(getContext(), "ID", true);
            getActivity().finish();

        } else {
            getFormController().showValidationErrors();
        }
        return true;
    }



    @Override
    public void initForm(FormController controller) {
        Context ctxt = getContext();

        InputValidator iv = new RequiredFieldValidator();
        HashSet<InputValidator> validators = new HashSet<>();
        validators.add(iv);

        FormSectionController section = new FormSectionController(ctxt, "Create New Course");

        //

        /*
           ID   INT              NOT NULL,
           NAME VARCHAR (36)     NOT NULL,
           SURNAME VARCHAR (36)  NOT NULL,
           EMAIL VARCHAR (46)    NOT NULL,
           IMAGE BLOB NOT NULL,
           ID_StudentGroup INT,
         */


        Map<Subject, Integer> subjectIntegerMap = TableAdapter.getAllFromTableAsMap(ctxt, Subject.class);

        List<String> subjectNames = new ArrayList<>();

        for(Subject subject : subjectIntegerMap.keySet()){
            subjectNames.add(subject.getName());
            subjectMap.put(subject.getName(), subject);
        }

        EditTextController semesterTextController = new EditTextController(ctxt, SEMESTER, null, "Semester", validators, InputType.TYPE_CLASS_NUMBER);
        SelectionController subjectSelectionController = new SelectionController(ctxt, COURSE_SUBJECT, "Group", true, "Select", subjectNames, true);

        section.addElement(semesterTextController);
        section.addElement(subjectSelectionController);

        semesterTextController.setValidators(validators);
        subjectSelectionController.setValidators(validators);

        controller.addSection(section);
    }
}
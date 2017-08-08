package com.olek.testify.activity.NexusFragments;


import android.content.Context;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.olek.testify.model.Group;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SubjectFormFragment extends TemplateFormFragment {

    //Map<String, Language> selector = new HashMap<>();

    public static final String SUBJECT_NAME = "subjectName";
    public static final String SUBJECT_DESCRIPTION = "subjectDescription";


    /*
       ID  INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
       NAME VARCHAR (36)     NOT NULL,
       DESCRIPTION TEXT      NOT NULL
     */

    public boolean validate() {
        getFormController().resetValidationErrors();
        if (getFormController().isValidInput()) {

            Object firstName = getModel().getValue(SUBJECT_NAME);
            Object description = getModel().getValue(SUBJECT_DESCRIPTION);


            Subject subject = new Subject();

            subject.setName((String) firstName);
            subject.setDescription((String) description);

            subject.insert(getContext(), "ID", true);


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

        FormSectionController section = new FormSectionController(ctxt, "Create New Subject");

        //

        /*
           ID   INT              NOT NULL,
           NAME VARCHAR (36)     NOT NULL,
           SURNAME VARCHAR (36)  NOT NULL,
           EMAIL VARCHAR (46)    NOT NULL,
           IMAGE BLOB NOT NULL,
           ID_StudentGroup INT,
         */


        Map<Group, Integer> groupIntegerMap = TableAdapter.getAllFromTableAsMap(ctxt, Group.class);

        List<String> groupNames = new ArrayList<>();

        /*

            public static final String SUBJECT_NAME = "subjectName";
            public static final String SUBJECT_DESCRIPTION = "subjectDescription";
         */

        EditTextController subjectTextController = new EditTextController(ctxt, SUBJECT_NAME, "Subject Name");
        EditTextController descTextController = new EditTextController(ctxt, SUBJECT_DESCRIPTION, "Subject Description");



        section.addElement(subjectTextController);
        section.addElement(descTextController);

        subjectTextController.setValidators(validators);
        descTextController.setValidators(validators);

        controller.addSection(section);
    }
}
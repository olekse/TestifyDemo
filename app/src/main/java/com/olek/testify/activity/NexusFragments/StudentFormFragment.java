package com.olek.testify.activity.NexusFragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.olek.testify.activity.ImageTakerElement;
import com.olek.testify.model.Group;
import com.olek.testify.model.Student;
import com.olek.testify.model.TableAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class StudentFormFragment extends TemplateFormFragment {

    private static final String CUSTOM_ELEM = "imageTaker";
    //Map<String, Language> selector = new HashMap<>();
    Map<String, Group> groupMap = new HashMap<>();
    Bitmap studentImage = null;


    public static final String FIRST_NAME = "firstName";
    public static final String SURNAME = "surname";
    public static final String EMAIL = "email";
    public static final String GROUP = "group";

    public boolean validate() {
        getFormController().resetValidationErrors();
        if (getFormController().isValidInput()) {

            Object firstName = getModel().getValue(FIRST_NAME);
            Object surname = getModel().getValue(SURNAME);
            Object email = getModel().getValue(EMAIL);
            Object group = getModel().getValue(GROUP);
            //Object imageBlob = getModel().getValue(CUSTOM_ELEM);

            Student student = new Student();
            student.setName((String) firstName);
            student.setSurname((String) surname);
            student.setEmail((String) email);
            student.setId_studentGroup(groupMap.get((String)group).getId());
            if (studentImage != null) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                studentImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                student.setImage(byteArray);
            }

            student.insert(getContext(), "ID", true);
            getActivity().finish();



        } else {
            getFormController().showValidationErrors();
        }
        return true;
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            studentImage = (Bitmap) extras.get("data");
            //mImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void initForm(FormController controller) {
        Context ctxt = getContext();

        InputValidator iv = new RequiredFieldValidator();
        HashSet<InputValidator> validators = new HashSet<>();
        validators.add(iv);

        FormSectionController section = new FormSectionController(ctxt, "Create New Student");

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

        for(Group group : groupIntegerMap.keySet()){
            groupNames.add(group.getCode_name());
            groupMap.put(group.getCode_name(), group);
        }

        EditTextController firstNameTextController = new EditTextController(ctxt, FIRST_NAME, "First Name");
        EditTextController surnameTextController = new EditTextController(ctxt, SURNAME, "Surname");
        EditTextController emailTextController = new EditTextController(ctxt, EMAIL, "Email");
        SelectionController groupSelectionController = new SelectionController(ctxt, GROUP, "Group", true, "Select", groupNames, true);

        ImageTakerElement customElem = new ImageTakerElement(getContext(), CUSTOM_ELEM, "Custom Element");

        customElem.getAddButton().setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


            }
        });



        section.addElement(surnameTextController);
        section.addElement(firstNameTextController);
        section.addElement(emailTextController);
        section.addElement(groupSelectionController);

        section.addElement(customElem);

        emailTextController.setValidators(validators);
        firstNameTextController.setValidators(validators);
        surnameTextController.setValidators(validators);

        controller.addSection(section);
    }
}
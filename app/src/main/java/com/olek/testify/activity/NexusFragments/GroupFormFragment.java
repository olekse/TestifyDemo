package com.olek.testify.activity.NexusFragments;


import android.content.Context;

import com.github.dkharrat.nexusdialog.FormController;
import com.github.dkharrat.nexusdialog.controllers.EditTextController;
import com.github.dkharrat.nexusdialog.controllers.FormSectionController;
import com.github.dkharrat.nexusdialog.controllers.SelectionController;
import com.github.dkharrat.nexusdialog.validations.InputValidator;
import com.github.dkharrat.nexusdialog.validations.RequiredFieldValidator;
import com.olek.testify.model.Group;
import com.olek.testify.model.Language;
import com.olek.testify.model.TableAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class GroupFormFragment extends TemplateFormFragment {

    Map<String, Language> selector = new HashMap<>();

    public static final String CODE_NAME = "codeName";
    public static final String DESCRIPTION = "description";
    public static final String LANGUAGE = "language";

    public boolean validate() {
        getFormController().resetValidationErrors();
        if (getFormController().isValidInput()) {

            Object code_name = getModel().getValue(CODE_NAME);
            Object description = getModel().getValue(DESCRIPTION);
            Object language = getModel().getValue(LANGUAGE);

            Group group = new Group();
            group.setCode_name((String) code_name);
            group.setText((String) description);
            group.setId_lang(selector.get(language).getId());

            group.insert(getContext(), "ID", true);
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

        FormSectionController section = new FormSectionController(ctxt, "Create New Group");

        EditTextController etc = new EditTextController(ctxt, CODE_NAME, "Group Name");
        etc.setValidators(validators);
        section.addElement(etc);



        EditTextController descTextController = new EditTextController(ctxt, DESCRIPTION, "Descrpition");
        descTextController.setMultiLine(true);
        descTextController.setValidators(validators);
        section.addElement(descTextController);

        List<Language> languages = (List<Language>)(List<?>) TableAdapter.getAllFromTable(getContext(), Language.class);

        Map<Language, Integer> map = TableAdapter.getAllFromTableAsMap(getContext(), Language.class);
        List<String> languageTitles = new ArrayList<>();

        for(Language lang : map.keySet()){
            languageTitles.add(lang.getName());
            selector.put(lang.getName(), lang);
        }

        // querry for languages

        section.addElement(new SelectionController(ctxt, LANGUAGE, "Language", true, "Select", languageTitles, true));

        controller.addSection(section);
    }
}
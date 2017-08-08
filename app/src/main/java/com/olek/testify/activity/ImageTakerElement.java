package com.olek.testify.activity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.github.dkharrat.nexusdialog.controllers.LabeledFieldController;
import com.olek.testify.R;

public class ImageTakerElement extends LabeledFieldController {

    public ImageTakerElement(Context ctx, String name, String label) {
        super(ctx, name, label, false);
    }

    @Override
    protected View createFieldView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        return inflater.inflate(R.layout.custom_element, null);
    }

    public Button getAddButton() {
        return (Button)getView().findViewById(R.id.add_btn);
    }


    public void refresh() {
        // nothing to refresh
    }
}

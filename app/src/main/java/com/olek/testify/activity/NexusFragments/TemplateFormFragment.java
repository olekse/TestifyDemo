package com.olek.testify.activity.NexusFragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.github.dkharrat.nexusdialog.FormFragment;
import com.olek.testify.R;

import butterknife.BindView;


public abstract class TemplateFormFragment extends FormFragment {
    public abstract boolean validate();




}

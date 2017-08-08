package com.olek.testify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.olek.testify.Utils.MultiSpinner;
import com.olek.testify.Utils.RandomString;
import com.olek.testify.adapters.NothingSelectedSpinnerAdapter;
import com.olek.testify.model.Language;
import com.olek.testify.model.Subject;
import com.olek.testify.model.TableAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateGroup extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener {

    private static final String TAG = CreateGroup.class.getSimpleName();

    @BindView(R.id.create_group_button)
    Button createGroupButton;

    @BindView(R.id.group_name_text)
    TextView groupNameText;

    @BindView(R.id.group_desc_text)
    TextView groupDescText;

    @BindView(R.id.lang_spinner)
    Spinner spinner;

    @BindView(R.id.multi_spinner)
    MultiSpinner multiSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_creation);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle("New Group");

        try {
            setUpLanguageSpinner();
            setCreateGroupButton();
            setCourseMultiSpinner();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setCourseMultiSpinner() {

        // just for demo
        // TODO

        RandomString rs = new RandomString(8);

        List<String> choiceList = rs.makeList(10);

        List<CharSequence> charSequanceList = new ArrayList<>();

        for(String s : choiceList){
            charSequanceList.add(s);
        }


        //

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.contact_spinner_row_nothing_selected, charSequanceList);

        multiSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        this));

        multiSpinner.setItems(choiceList, "Select Courses", this);

    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }

    private void setCreateGroupButton() {

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();

                sb.append(groupNameText.getText() + "\n");
                sb.append(groupDescText.getText() + "\n");
                sb.append(spinner.getSelectedItem() + "\n");

                Log.d(TAG, sb.toString());

            }
        });

    }

    private void setUpLanguageSpinner() throws Exception {

        List<CharSequence> spinnerDataSelection = new ArrayList<>();

        List<Language> languages = (List<Language>)(List<?>) TableAdapter.getAllFromTable(this, Language.class);

        Language lang_id1 = (Language) TableAdapter.getBy("ID", "1", this, Language.class);

        for(Language l : languages){

            spinnerDataSelection.add(l.getName());
        }

        // delet this

        Subject sub = new Subject();
        sub.setName("MAS");
        sub.setDescription("Pain in the butt!");

        sub.insert(this, "ID", true);

        List<Subject> subjects = (List<Subject>)(List<?>) TableAdapter.getAllFromTable(this, Subject.class);


        //


        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, R.layout.contact_spinner_row_nothing_selected, spinnerDataSelection);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Language");

        spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.contact_spinner_row_nothing_selected,
                        this));
    }


}

package com.olek.testify.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.olek.testify.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.register_email_text)
    EditText emailText;

    @BindView(R.id.register_password_text)
    EditText passwordText;

    @BindView(R.id.register_button)
    Button registerButton;

    @BindView(R.id.have_account_text)
    TextView goToLoginText;

    @BindView(R.id.register_name_text)
    EditText registerNameText;

    @BindView(R.id.empty_email_message)
    TextView emptyEmailMessage;

    @BindView(R.id.empty_name_message)
    TextView emptyNameMessage;

    @BindView(R.id.emptyPassword_message)
    TextView emptyPasswordMessage;

    private ProgressDialog progressDiagog;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    private static final String FIREBASE_DB_TABLE_USERS = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_layout);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDiagog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child(FIREBASE_DB_TABLE_USERS);
        registerButton.setOnClickListener(this);
        goToLoginText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == registerButton){
            registerUser();
        }

        if (view == goToLoginText){
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    private void registerUser() {
        final String email = emailText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();
        final String name = registerNameText.getText().toString().trim();

        Boolean isFailed = false;

        if(TextUtils.isEmpty(email)){
            emptyEmailMessage.setVisibility(View.VISIBLE);
            isFailed = true;
        } else {
            emptyEmailMessage.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(password)){
            emptyPasswordMessage.setVisibility(View.VISIBLE);
            isFailed = true;
        } else {
            emptyPasswordMessage.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(name)){
            emptyNameMessage.setVisibility(View.VISIBLE);
            isFailed = true;
        } else {
            emptyNameMessage.setVisibility(View.INVISIBLE);
        }

        if (isFailed)  return;

        progressDiagog.setMessage("Registering in progress!");
        progressDiagog.setCanceledOnTouchOutside(false);
        progressDiagog.show();

        try {

            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDiagog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Sucsess!", Toast.LENGTH_LONG);


                        //sendEmailVerification();
                        onAuth(task.getResult().getUser());


                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(LoginActivity.EXTRA_EMAIL_DATA, email);
                        resultIntent.putExtra(LoginActivity.EXTRA_PASSWORD_DATA, password);
                        setResult(RESULT_OK, resultIntent);
                        finish();

                    } else {
                        Toast.makeText(RegisterActivity.this, "Fail!", Toast.LENGTH_LONG);
                    }
                }


            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
        
    }

    /*
    private void sendEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,
                                "Check your email for verefication", Toast.LENGTH_LONG);

                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }
    }
    */

    private void onAuth(FirebaseUser user) {
        createUser(user.getUid());

    }

    private void createUser(String uid) {
        User user = new User();

        user.setEmail(emailText.getText().toString());
        user.setDisplayName(registerNameText.getText().toString());
        user.setCreatedAt(new Date().getTime());

        mDatabase.child(uid).setValue(user);
    }


}

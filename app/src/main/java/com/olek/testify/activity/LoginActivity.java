package com.olek.testify.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.olek.testify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.login_email_text)
    EditText emailText;

    @BindView(R.id.login_password_text)
    EditText passwordText;

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.register_not_registered_text)
    TextView goToRegister;

    @BindView(R.id.empty_email_message)
    TextView emptyEmailMessage;

    @BindView(R.id.emptyPassword_message)
    TextView emptyPasswordMessage;

    // @BindView(R.id.google_sign_in_button)
    // Button googleSignInButton;

    private ProgressDialog progressDiagog;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private boolean redirect = true;


    public static final int REGISTER_USER_ACTIVITY = 15;
    public static final String EXTRA_EMAIL_DATA = "emailData";
    public static final String EXTRA_PASSWORD_DATA = "passwordData";


    public static final int GO_TO_HOME_PAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_layout);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    goToHomeActivity();
                } else {
                    // do nutting
                }
            }
        };


        progressDiagog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
        goToRegister.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton) {

            String email = emailText.getText().toString().trim();
            String password = passwordText.getText().toString().trim();

            loginUser(email, password);
        }

        if (view == goToRegister) {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivityForResult(registerIntent, REGISTER_USER_ACTIVITY);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REGISTER_USER_ACTIVITY == requestCode) {
            if (resultCode == RESULT_OK) {
                redirect = true;
                goToHomeActivity();

                //
            } else if (resultCode == RESULT_CANCELED) {
                return;
            }
        }

        if (GO_TO_HOME_PAGE == requestCode) {
            if (resultCode == RESULT_CANCELED) {
                redirect = true;
                //goToHomeActivity();
            }
        }

    }

    private void loginUser(String email, String password) {


        Boolean isFailed = false;

        if (TextUtils.isEmpty(email)) {
            emptyEmailMessage.setVisibility(View.VISIBLE);
            isFailed = true;
        } else {
            emptyEmailMessage.setVisibility(View.INVISIBLE);
        }

        if (TextUtils.isEmpty(password)) {
            emptyPasswordMessage.setVisibility(View.VISIBLE);
            isFailed = true;
        } else {
            emptyPasswordMessage.setVisibility(View.INVISIBLE);
        }

        if (isFailed) return;

        progressDiagog.setMessage("Login is in progress!");
        progressDiagog.setCanceledOnTouchOutside(false);
        progressDiagog.show();

        try {

            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDiagog.dismiss();
                    if (task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, "Sucsess!", Toast.LENGTH_LONG);

                        //redirect = true;
                        goToHomeActivity();

                    } else {
                        Toast.makeText(LoginActivity.this, "Either email or password is wrong!", Toast.LENGTH_LONG);
                    }
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void goToHomeActivity() {
        if (redirect) {
            redirect = false;
            Intent homeScreenIntent = new Intent(LoginActivity.this, HomeScreenActivity.class);
            startActivityForResult(homeScreenIntent, GO_TO_HOME_PAGE);
        }
    }


}

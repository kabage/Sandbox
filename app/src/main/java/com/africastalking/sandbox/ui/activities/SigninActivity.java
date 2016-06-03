package com.africastalking.sandbox.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.africastalking.swipe.R;

/**
 * Created by Lawrence on 11/3/15.
 */
public class SigninActivity extends AppCompatActivity {

    private Button btnSignIn;
    private AppCompatEditText edEmail, edPassword;
    private TextView txtSignUp;
    private ProgressDialog progressDialog;

    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);


        edEmail = (AppCompatEditText) findViewById(R.id.email);
        edPassword = (AppCompatEditText) findViewById(R.id.password);
        btnSignIn = (Button) findViewById(R.id.signUpButton);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showProgressBar();

                }
            }
        });

        txtSignUp = (TextView) findViewById(R.id.link_signup);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

    }


    public void showProgressBar() {
        progressDialog = ProgressDialog.show(this, "", "Signing in...please be patient");


    }

    public void hideProgressBar() {
        progressDialog.cancel();
    }

    public boolean validate() {
        boolean valid = true;

        String email = edEmail.getText().toString().trim();
        String password = edPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("enter a valid email address");
            valid = false;
        } else {
            edEmail.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edPassword.setError(null);
        }


        return valid;
    }
}

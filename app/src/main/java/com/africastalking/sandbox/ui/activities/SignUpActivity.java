package com.africastalking.sandbox.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.africastalking.sandbox.utils.AppPreferencesManager;
import com.africastalking.swipe.R;

import java.util.regex.Pattern;

/**
 * Created by Lawrence on 11/2/15.
 */
public class SignUpActivity extends AppCompatActivity {


    private AppCompatEditText edEmail, edPhone, edPassword;
    private Button btnSignUp;
    private TextView txtSkip, txtSignIn;
    private static final String PHONE_REGEX = "\\+?[0-9]{10,13}";

    private static String DEBUG_TAG = "SignUpActivity";
    private RelativeLayout parentRelativeLayout;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        parentRelativeLayout = (RelativeLayout) findViewById(R.id.parentRelativeLayout);

//        edEmail = (AppCompatEditText) findViewById(R.id.email);
        edPhone = (AppCompatEditText) findViewById(R.id.phone);
        edPassword = (AppCompatEditText) findViewById(R.id.password);


//        txtSkip = (TextView) findViewById(R.id.link_skip);
//        txtSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                AppPreferencesManager.markSignUpFormShown(getApplicationContext());
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            }
//        });

//        txtSignIn = (TextView) findViewById(R.id.link_signin);
//        txtSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), SigninActivity.class));
//                finish();
//            }
//        });

        btnSignUp = (Button) findViewById(R.id.signUpButton);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    showProgressBar();
                    saveDetails(edPhone.getText().toString(), SignUpActivity.this);
                    AppPreferencesManager.markSignUpFormShown(getApplicationContext());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                    hideProgressBar();
                }
            }

        });
    }


    public void showProgressBar() {
        progressDialog = ProgressDialog.show(this, "", "Creating account...please be patient");


    }

    public void hideProgressBar() {
        progressDialog.cancel();
    }


    public boolean validate() {
        boolean valid = true;

//        String email = edEmail.getText().toString().trim();
        String phone = edPhone.getText().toString().trim();
//        String password = edPassword.getText().toString().trim();


//        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            edEmail.setError("enter a valid email address");
//            valid = false;
//        } else {
//            edEmail.setError(null);
//        }

        if (TextUtils.isEmpty(phone) || !Pattern.matches(PHONE_REGEX, phone)) {
            edPhone.setError("enter a valid phone number");
            valid = false;
        } else {
            edPhone.setError(null);
        }

        return valid;
    }

    public static void saveDetails(String phone_number, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("phone_number", phone_number).commit();
    }
}

package com.africastalking.sandbox.ui.fragments.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.africastalking.swipe.R;

/**
 * Created by Lawrence on 10/29/15.
 */
public class VoiceFragment extends Fragment {

    private AppCompatEditText edEmail, edPhone, edPassword;
    private Button btnSignUp;
    private TextView txtSkip, txtSignIn;
    private static final String PHONE_REGEX = "\\+?[0-9]{10,13}";

    private static String DEBUG_TAG = "SignUpActivity";
    private RelativeLayout parentRelativeLayout;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        showProgressBar();

        View rootView = inflater.inflate(R.layout.activity_voice,
                container, false);

        parentRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.parentRelativeLayout);

//        edEmail = (AppCompatEditText) findViewById(R.id.email);
        edPhone = (AppCompatEditText) rootView.findViewById(R.id.phone);
        edPassword = (AppCompatEditText) rootView.findViewById(R.id.password);

/*
        txtSkip = (TextView) findViewById(R.id.link_skip);
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                AppPreferencesManager.markSignUpFormShown(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        txtSignIn = (TextView) findViewById(R.id.link_signin);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SigninActivity.class));
                finish();
            }
        });
        */

        btnSignUp = (Button) rootView.findViewById(R.id.signUpButton);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {

                }
            }

        });

//        showProgressBar();
        return rootView;
    }

    public void showProgressBar() {
        progressDialog = ProgressDialog.show(getActivity(), "", "Running USSD code");


    }

    public void hideProgressBar() {
        progressDialog.cancel();
    }

    public boolean validate() {
        return true;
    }


}

package com.africastalking.sandbox.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

;
import com.africastalking.sandbox.Backend.ConnectionManager;
import com.africastalking.swipe.R;


import java.io.PrintWriter;

/**
 * Created by Lawrence on 11/2/15.
 */
public class DashboardActivity extends AppCompatActivity {


    private AppCompatEditText edEmail, edPhone, edPassword;
    private Button btnSignUp;
    private TextView txtSkip, txtSignIn;
    private static final String PHONE_REGEX = "\\+?[0-9]{10,13}";

    private static String DEBUG_TAG = "SignUpActivity";
    private RelativeLayout parentRelativeLayout;
    private ProgressDialog progressDialog;

    private RelativeLayout sms, ussd, voice, airtime;

    private int FRAGMENT_USSD = 0;
    private int FRAGMENT_SMS = 1;
    private int FRAGMENT_AIRTIME = 2;
    private int FRAGMENT_VOICE = 3;

//    {"messageType":"RegistrationRequest","phoneNumber":"+254718008164"}
//    telnet 134.213.52.79 8082

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dash);


//        parentRelativeLayout = (RelativeLayout) findViewById(R.id.parentRelativeLayout);
        sms = (RelativeLayout) findViewById(R.id.learninggames_card);
        ussd = (RelativeLayout) findViewById(R.id.virtualclassroom_card);
        voice = (RelativeLayout) findViewById(R.id.kytabukyosk_card);
        airtime = (RelativeLayout) findViewById(R.id.testandexams_card);

        setUpActions();
//        Ipconnect();
        ConnectionManager myClient = new ConnectionManager();
        myClient.execute();
    }

    private void setUpActions() {
        airtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle fragment_selected = new Bundle();
//                fragment_selected.putInt("fragment", FRAGMENT_AIRTIME);
//                Intent homeintent = new Intent(DashboardActivity.this, MainActivity.class);
//                homeintent.putExtras(fragment_selected);
//                startActivity(homeintent);
                if (ConnectionManager.socket != null) {
                    try {
                        PrintWriter output = new PrintWriter(ConnectionManager.socket.getOutputStream());
                        output.print(" {\"messageType\":\"RegistrationRequest\",\"phoneNumber\":\"+254718008164\"}");
                        output.flush();
                    } catch (Exception e) {
                        Log.e("Eroor", "posdting data, " + e.toString());
                    }
                }

                if (ConnectionManager.socket != null) {
                    try {
                        PrintWriter output = new PrintWriter(ConnectionManager.socket.getOutputStream());
                        output.print(" {\"messageType\":\"UssdRequest\",\"phoneNumber\":\"+254718008164\",\"payload\":\"{\\\"inputText\\\":\\\"384#*2#\\\"}\"}");
                        output.flush();
                    } catch (Exception e) {
                        Log.e("Eroor", "posdting data, " + e.toString());
                    }
                }


            }
        });
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle fragment_selected = new Bundle();
                fragment_selected.putInt("fragment", FRAGMENT_VOICE);
                Intent homeintent = new Intent(DashboardActivity.this, MainActivity.class);
                homeintent.putExtras(fragment_selected);
                startActivity(homeintent);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle fragment_selected = new Bundle();
                fragment_selected.putInt("fragment", FRAGMENT_SMS);
                Intent homeintent = new Intent(DashboardActivity.this, MainActivity.class);
                homeintent.putExtras(fragment_selected);
                startActivity(homeintent);
            }
        });
        ussd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle fragment_selected = new Bundle();
                fragment_selected.putInt("fragment", FRAGMENT_USSD);
                Intent homeintent = new Intent(DashboardActivity.this, MainActivity.class);
                homeintent.putExtras(fragment_selected);
                startActivity(homeintent);
            }
        });


    }

}

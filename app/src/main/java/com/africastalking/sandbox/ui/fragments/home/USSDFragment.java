package com.africastalking.sandbox.ui.fragments.home;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.africastalking.sandbox.Backend.CreateSocketConnection;
import com.africastalking.sandbox.ui.widgets.SendCommentButton;
import com.africastalking.swipe.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Lawrence on 10/29/15.
 */
public class USSDFragment extends Fragment implements SendCommentButton.OnSendClickListener {


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

        View rootView = inflater.inflate(R.layout.activity_ussd,
                container, false);

        parentRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.parentRelativeLayout);

//        edEmail = (AppCompatEditText) findViewById(R.id.email);
        edPhone = (AppCompatEditText) rootView.findViewById(R.id.phone);
        edPassword = (AppCompatEditText) rootView.findViewById(R.id.password);

        edPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPhone.setText("");
            }
        });
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
                    showProgressBar();
                    try {
//                        CreateSocketConnection connection = new CreateSocketConnection(getActivity(), CreateSocketConnection.makeUSSDRequest("254704415609", edPhone.getText().toString()));
//                        connection.execute();

                        NetworkTask task = new NetworkTask();
                        task.postData(CreateSocketConnection.makeRegistrationRequest("254704415609"));

                    } catch (Exception e) {
                        Log.e("errMakingUssd", e.toString());
                    }
                    new Thread() {
                        public void run() {
                            try {
                                sleep(4000);

                                hideProgressBar();
                            } catch (Exception e) {
                                Log.e("threadmessage", e.getMessage());
                            }
                        }
                    }.start();

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

    @Override
    public void onSendClickListener(View v) {

    }

    public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {
        Socket nsocket; //Network Socket
        InputStream nis; //Network Input Stream
        OutputStream nos; //Network Output Stream

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... params) { //This runs on a different thread
            boolean result = false;
            try {
                Log.i("AsyncTask", "doInBackground: Creating socket");
                SocketAddress sockaddr = new InetSocketAddress("103.3.63.131", 1234);
                nsocket = new Socket();
                nsocket.setKeepAlive(true);
                nsocket.connect(sockaddr, 30000); //10 second connection timeout
                if (nsocket.isConnected()) {
                    nis = nsocket.getInputStream();
                    nos = nsocket.getOutputStream();
                    Log.i("AsyncTask", "doInBackground: Socket created, streams assigned");
                    Log.i("AsyncTask", "doInBackground: Waiting for inital data...");
                    byte[] buffer = new byte[4096];
                    int read = nis.read(buffer, 0, 4096); //This is blocking
                    while (read != -1) {
                        byte[] tempdata = new byte[read];
                        System.arraycopy(buffer, 0, tempdata, 0, read);
                        publishProgress(tempdata);
                        Log.i("AsyncTask", "doInBackground: Got some data");
                        read = nis.read(buffer, 0, 4096); //This is blocking
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: IOException");
                result = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: Exception");
                result = true;
            } finally {
                try {
                    nis.close();
                    nos.close();
                    nsocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("AsyncTask", "doInBackground: Finished");
            }
            return result;
        }

        public void SendDataToNetwork(String cmd) { //You run this from the main thread.
            try {
                if (nsocket.isConnected()) {
                    Log.i("AsyncTask", "SendDataToNetwork: Writing received message to socket");
                    nos.write(cmd.getBytes());
                } else {
                    Log.i("AsyncTask", "SendDataToNetwork: Cannot send message. Socket is closed");
                }
            } catch (Exception e) {
                Log.i("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception");
            }
        }

        @Override
        protected void onProgressUpdate(byte[]... values) {
            if (values.length > 0) {
                Log.i("AsyncTask", "onProgressUpdate: " + values[0].length + " bytes received.");
//                textStatus.setText(new String(values[0]));
            }
        }

        public boolean postData(String data_) {
//        getSocket();
            if (nsocket != null) {
                try {
                    PrintWriter output = new PrintWriter(nsocket.getOutputStream());
                    output.print(data_);
//                    output.flush();
                    nsocket.setSoTimeout(20000);
                    return true;
                } catch (Exception e) {
                    Log.e("ERRPOST", e.toString());
                }
            } else {
                Log.e("SOCKETNUL", "NUL");
            }

            return false;
        }

//        btnStart.setVisibility(View.VISIBLE);
    }
//
//    private static Socket getSocket(Socket nsocket) {
//        if (nsocket == null || nsocket.isClosed()) {
//            try {
//                nsocket = new Socket();
//                nsocket.connect(new java.net.InetSocketAddress(at_server_ddress, at_server_port), 20000);
//            } catch (Exception e) {
//                nsocket = null;
//            }
//        }
//        return nsocket;
//    }
}

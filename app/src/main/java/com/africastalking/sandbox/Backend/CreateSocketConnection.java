package com.africastalking.sandbox.Backend;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.africastalking.sandbox.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by kabage on 4/26/16.
 */

public class CreateSocketConnection extends AsyncTask<String, Void, String> {


//    static String at_server_ddress = "134.213.52.79";  //103.3.63.131
//    static int at_server_port = 8082;

    static String at_server_ddress = "103.3.63.131";  //103.3.63.131
    static int at_server_port = 1234;
    static Socket socket;
    static String responseType;
    static Context context;
    static int type;
    static String json;

    public CreateSocketConnection(Context context, String json) {
        this.context = context;
        this.json = json;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.e("action_started", "started");

        try {
            connect();
        } catch (Exception e) {
            Log.e("errMakinconn", e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        Looper.prepare();
        postData(json);

    }

    public static void connect() {
        Looper.prepare();

        socket = new Socket();
        try {
//            socket.setKeepAlive(true);
            socket.connect(new java.net.InetSocketAddress(at_server_ddress, at_server_port), 20000);
            socket.setKeepAlive(true);
        } catch (IOException e) {
//            Toast.makeText(context.getApplicationContext(), "error creating socket" + e.toString(), Toast.LENGTH_LONG).show();
        }

        messageListener(json);
    }

    public static String makeRegistrationRequest(String phone_number) {
        JSONObject registration_object = new JSONObject();

        try {
            registration_object.put("phoneNumber", phone_number);
            registration_object.put("messageType", "RegistrationRequest");
        } catch (JSONException e) {
            Log.e("registration_error", e.toString());
        }
        return registration_object.toString();
    }

    public static String sendMessageRequest(String phone_number, String text) {
        JSONObject registration_object = new JSONObject();

        try {
            registration_object.put("text", text);
            registration_object.put("from", phone_number);
            registration_object.put("messageType", "OutgoingSms");
        } catch (JSONException e) {
            Log.e("registration_error", e.toString());
        }
        return registration_object.toString();
    }

    public static String makeUSSDRequest(String phone_number, String input_text) {

        //{"messageType":"UssdMenu","sequenceNumber":8,"payload":"{\"transactionId\":\"f8b67483-7a10-45c1-b414-5042c3005a99\",\"text\":\"Application Error\",\"status\":\"END\"}"}
        JSONObject ussd_object = new JSONObject();
        JSONObject payload = new JSONObject();

        try {
            payload.put("inputText", input_text);
            ussd_object.put("phoneNumber", phone_number);
            ussd_object.put("messageType", "UssdRequest");
            ussd_object.put("payload", payload.toString());
        } catch (JSONException e) {
            Log.e("ussd_error", e.toString());
        }
        return ussd_object.toString();

        //{"messageType":"UssdRequest","phoneNumber":"+254718008164","payload":"{\"inputText\":\"*384#\"}"}
    }

    private static void checkInternet(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
//            messageListener(3);
        }
    }

    private static void messageListener(String json) {


//        Toast.makeText(context, "NotifListener", Toast.LENGTH_LONG).show();
        postData(json);
        if (socket != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String responseStr;
                if ((responseStr = reader.readLine()) != null) {
                    JSONObject responseObject = new JSONObject(responseStr);

                    Log.e("REGSTAT", responseStr);
//                    Toast.makeText(context, "TransactionID id: " + responseObject.get("transactionId").toString(), Toast.LENGTH_LONG).show();
                    if (responseObject.has("responseType")) {
                        stopLoad();
                        socket.setSoTimeout(0);
                        responseType = responseObject.getString("responseType");


                        if (responseObject.has("sequenceNumber")) {
                            long seqNo = responseObject.getLong("sequenceNumber");

                            JSONObject transRequest = new JSONObject();

                            transRequest.put("messageType", "OutboundMessageAck");
                            transRequest.put("sequenceNumber", seqNo);
                            postData(transRequest.toString());
                        }
                    }
                    messageListener(json);
                } else {
//                    socket.close();
//                    socket = null;
                    stopLoad();
                }
            } catch (java.net.SocketTimeoutException e) {
                stopLoad();
                messageListener(json);
            } catch (JSONException e) {
                stopLoad();
            } catch (Exception e) {
                stopLoad();
                try {
//                    socket.close();
                } catch (Exception ex) {
                }
                socket = null;
            }
        } else {
            stopLoad();
            //checkInternet();
        }
    }

    private static void stopLoad() {

    }

    public static boolean postData(String data_) {
//        getSocket();
        if (socket != null) {
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                output.print(data_);
                output.flush();
                socket.setSoTimeout(20000);
                return true;
            } catch (Exception e) {
                Log.e("ERRPOST", e.toString());
            }
        } else {
            Log.e("SOCKETNUL", "NUL");
        }

        return false;
    }

    private static Socket getSocket() {
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket();
                socket.connect(new java.net.InetSocketAddress(at_server_ddress, at_server_port), 20000);
            } catch (Exception e) {
                socket = null;
            }
        }
        return socket;
    }

    public void howToPayDialog(final Context ctx) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(
//                ctx);
//
//        LayoutInflater inflater = ctx.getLayoutInflater();
//        builder.setView(inflater.inflate(R.layout.notification_dialog, null))
//                .setCancelable(false)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                });
//
//        AlertDialog alertDialog = builder.create();
//
//        alertDialog.show();

    }
}
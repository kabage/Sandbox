package com.africastalking.sandbox.Backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;


public class NotificationsHandler extends Service {
    static String at_server_address = "134.213.52.79";
    static int at_server_port = 8082;

    private static Socket socket = null;
    private NotificationManager notificationManager;
    android.app.ProgressDialog progress;
    private String responseType;
    private static final int soConnectTimeout = 30000, soReadTimeout = 20000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String json = (String) intent.getExtras().get("json");
        Toast.makeText(this, json, Toast.LENGTH_SHORT).show();

        CreateSocketConnection connection = new CreateSocketConnection(this, json);
        connection.execute();

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeSocket();
    }

    public static void closeSocket() {
        try {
            socket.close();
            socket = null;
        } catch (Exception e) {
        }
    }

    private void checkInternet() {
        NetworkInfo networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationListener();
        }
    }

    private static Socket getSocket() {
        if (socket == null || socket.isClosed()) {
            try {
                socket = new Socket();
                socket.connect(new java.net.InetSocketAddress(at_server_address, at_server_port), soConnectTimeout);
            } catch (Exception e) {
                socket = null;
            }
        }
        return socket;
    }

    public static boolean postData(String data_) {
        getSocket();
        if (socket != null) {
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                output.print(data_);
                output.flush();
                socket.setSoTimeout(soReadTimeout);
                return true;
            } catch (Exception e) {
            }
        }


        return false;
    }

    private void notificationListener() {
        if (socket != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String responseStr;
                if ((responseStr = reader.readLine()) != null) {
                    JSONObject responseObject = new JSONObject(responseStr);

                    Toast.makeText(this, "Connected successfuly", Toast.LENGTH_LONG).show();
                    if (responseObject.has("responseType")) {
                        stopLoad();
                        socket.setSoTimeout(0);
                        responseType = responseObject.getString("responseType");


                        if (responseObject.has("sequenceNumber")) {
                            long seqNo = responseObject.getLong("sequenceNumber");

                            String transRequest = new JSONObject().toString();
//                            postData(transRequest);
                        }
                    }
                    notificationListener();
                } else {
                    socket.close();
                    socket = null;
                    stopLoad();
                }
            } catch (java.net.SocketTimeoutException e) {
                stopLoad();
                notificationListener();
            } catch (JSONException e) {
                stopLoad();
            } catch (Exception e) {
                stopLoad();
                try {
                    socket.close();
                } catch (Exception ex) {
                }
                socket = null;
            }
        } else {
            stopLoad();
            //checkInternet();
        }
    }


    private void generalNotification(JSONObject responseObject_) throws Exception {
        if (responseObject_.has("payload")) {
            JSONObject payload = new JSONObject(responseObject_.getString("payload"));
            String title = payload.getString("title");
            String time = new SimpleDateFormat("dd-MMM-yyyy h:m:s a", java.util.Locale.getDefault()).format(System.currentTimeMillis());
            String msg = payload.getString("message");
//            setNotification(title, msg, time);
        }
    }

    private void stopLoad() {

    }
}

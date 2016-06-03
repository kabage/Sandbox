package com.africastalking.sandbox.Backend;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class Parser {

    public void parseUSSDRequest(String raw_string) throws Exception {
        JSONObject payload = new JSONObject(raw_string);
        String title = payload.getString("title");
        String time = new SimpleDateFormat("dd-MMM-yyyy h:m:s a", java.util.Locale.getDefault()).format(System.currentTimeMillis());
        String msg = payload.getString("message");
//
    }

    public void parseIncomingSMS(String raw_string) throws Exception {
        JSONObject payload = new JSONObject(raw_string);
        String title = payload.getString("title");
        String time = new SimpleDateFormat("dd-MMM-yyyy h:m:s a", java.util.Locale.getDefault()).format(System.currentTimeMillis());
        String msg = payload.getString("message");
//
    }

    public void parseAirtimeRequest(String raw_string) throws Exception {
        JSONObject payload = new JSONObject(raw_string);
        String title = payload.getString("title");
        String time = new SimpleDateFormat("dd-MMM-yyyy h:m:s a", java.util.Locale.getDefault()).format(System.currentTimeMillis());
        String msg = payload.getString("message");
//
    }
}

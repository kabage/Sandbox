package com.africastalking.sandbox.Backend;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.util.Log;

import java.text.ParseException;

/**
 * Created by kabage
 */
public class SipConnectionManager {
    SipManager mSipManager;
    SipProfile mSipProfile;
    SipAudioCall call;

    public void createSipManager(Context context) {
        if (mSipManager == null) {
            mSipManager = SipManager.newInstance(context);
        }


    }

    public void registerOnSipServer(String username, String password, String domain) {
        SipProfile.Builder builder = null;
        try {
            builder = new SipProfile.Builder(username, domain);
        } catch (ParseException e) {
            Log.e("ErrorCreatingProfile", e.toString());
        }
        builder.setPassword(password);
        mSipProfile = builder.build();

    }

    public void openLocalProfile(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.SipDemo.INCOMING_CALL");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, Intent.FILL_IN_DATA);
        try {
            mSipManager.open(mSipProfile, pendingIntent, null);
        } catch (SipException e) {
            Log.e("ErrorOpeningProfile", e.toString());
        }
    }

    public void setUpRegistrationListener() throws SipException {
        mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

            public void onRegistering(String localProfileUri) {
//                updateStatus("Registering with SIP Server...");
            }

            public void onRegistrationDone(String localProfileUri, long expiryTime) {
//                updateStatus("Ready");
            }

            public void onRegistrationFailed(String localProfileUri, int errorCode,
                                             String errorMessage) {
//                updateStatus("Registration failed.  Please check settings.");
            }
        });
    }

    public void setUpAudioCallListener() {
//SetUp call listener before making call
        SipAudioCall.Listener listener = new SipAudioCall.Listener() {

            @Override
            public void onCallEstablished(SipAudioCall call) {
                call.startAudio();
                call.setSpeakerMode(true);
                call.toggleMute();

            }

            @Override
            public void onCallEnded(SipAudioCall call) {
                // Do something.
            }
        };
    }

    public void closeLocalProfile() {
        if (mSipManager == null) {
            return;
        }
        try {
            if (mSipProfile != null) {
                mSipManager.close(mSipProfile.getUriString());
            }
        } catch (Exception e) {
            Log.d("Failed to close", e.toString());
        }
    }

    public void makeCall(String sipAddress, SipAudioCall.Listener listener) {
        try {
            call = mSipManager.makeAudioCall(mSipProfile.getUriString(), sipAddress, listener, 30);
        } catch (SipException e) {
            Log.e("errormakingcall", e.toString());
        }


    }
}

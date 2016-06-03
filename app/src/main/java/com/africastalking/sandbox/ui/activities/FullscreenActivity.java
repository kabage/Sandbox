package com.africastalking.sandbox.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.africastalking.swipe.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Lawrence on 11/25/15.
 */
public class FullscreenActivity extends AppCompatActivity {

    public static final String ARG_PHOTO = "photo";
    private static Context mContext;

    public static Intent getIntent(Context context, String image) {
        mContext = context;
        System.out.println("FullscreenActivity " + image);
        Intent intent = new Intent(context, FullscreenActivity.class);
        intent.putExtra(ARG_PHOTO, image);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ImageView fullscreenView = (ImageView) findViewById(R.id.fullscreen_view);
        String url = getIntent().getStringExtra(ARG_PHOTO);


        Picasso.with(getApplicationContext())
                .load(url)
                .into(fullscreenView);
    }
}

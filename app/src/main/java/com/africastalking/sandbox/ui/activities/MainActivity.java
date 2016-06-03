package com.africastalking.sandbox.ui.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;


import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.africastalking.sandbox.ui.fragments.HomeFragment;
import com.africastalking.swipe.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private int function_selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle fragment_selected = getIntent().getExtras();
        try {
            function_selected = (int) fragment_selected.get("fragment");
        } catch (Exception e) {

        }

        Ipconnect();
        String number = null;
        try {
            number = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("phone_number", "");
        } catch (Exception e) {
        }


//        Bundle bundle = new Bundle();
//        bundle.putString("json", CreateSocketConnection.makeRegistrationRequest(number));
//        Intent intent = new Intent(MainActivity.this, NotificationsHandler.class);
//        intent.putExtras(bundle);
//
//        startService(intent);
        Log.e("", "");
//        if (AppPreferencesManager.isIntroductionDone(getApplicationContext())) {
//            if (!AppPreferencesManager.isSignUpFormShown(getApplicationContext())) {
////                finish();
//                Toast.makeText(this, "starting service", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
//            }
//
//        } else {
////            finish();
//            Toast.makeText(this, "starting service", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
//        }


        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//
        displayView(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);


        switch (viewId) {
            case R.id.nav_home:
                fragment = new HomeFragment();

                Bundle data = new Bundle();
                data.putInt("fragment", function_selected);
                fragment.setArguments(data);
                title = " ";
                break;

            case R.id.nav_news:

                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void Ipconnect() {
        AsyncHttpClient.getDefaultInstance().websocket("http://103.3.63.131:1234", "my-protocol", new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    ex.printStackTrace();
                    return;
                }

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    @Override
                    public void onStringAvailable(String s) {
                        Log.e("Logged", s);
                    }
                });
                webSocket.setDataCallback(new DataCallback() {
                    public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
                        System.out.println("I got some bytes!");
                        // note that this data has been read
                        byteBufferList.recycle();
                    }
                });


                webSocket.setClosedCallback(new CompletedCallback() {
                    @Override
                    public void onCompleted(Exception ex) {
                        Ipconnect();

                    }
                });
                while (true) {
                    webSocket.send("a string");
                    webSocket.send(new byte[10]);
                    Log.e("sent", "sent");
                }

//                webSocket.send("a string");
//                webSocket.send(new byte[10]);

            }
        });

    }
}

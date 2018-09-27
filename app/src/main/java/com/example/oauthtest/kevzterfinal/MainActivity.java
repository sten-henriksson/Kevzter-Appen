package com.example.oauthtest.kevzterfinal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Fragment fragment = null;
    Class fragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Globals g = Globals.getInstance();
        int data=g.getData();

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");




        String profile= g.getUser();
        String email = g.getEmail();
        String pic = g.getPicture();
        Log.i("before",profile+email+pic);
        profile = mPrefs.getString("login", "0");
        email = mPrefs.getString("email", "0");
        pic = mPrefs.getString("pic", "0");

        Log.i("after",profile+email+pic);



        mDrawerLayout = findViewById(R.id.drawer_layout);
        fragmentClass = fragment1.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        TextView texVar= (TextView) findViewById(R.id.logout);
        texVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals g = Globals.getInstance();
                g.setUser("");
                g.setEmail("");
                g.setPicture("");
                g.setData(0);

                SharedPreferences mPrefs = getSharedPreferences("label", 0);
                SharedPreferences.Editor mEditor = mPrefs.edit();
                mEditor.putString("login", "0").commit();
                mEditor.putString("email", "0").commit();
                mEditor.putString("pic", "0").commit();
                mEditor.putString("saved", "0").commit();
                mEditor.apply();
                mEditor.commit();
                changetotwitchactivity();
            }
        });




        NavigationView navigationView = findViewById(R.id.nav_view);

        NavigationView navview = (NavigationView) findViewById(R.id.nav_view);
        View header = navview.getHeaderView(0);
        ImageView simpleImageView = (ImageView) header.findViewById(R.id.imageviewww);

        try
        {
            Picasso.get().load(pic).into(simpleImageView);//statements that may cause an exception
        }
        catch (Exception e){
            changetotwitchactivity();
        }

        TextView text = (TextView) header.findViewById(R.id.textView2);
        text.setText(profile);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        Log.i("INTENTINTENTINTENTINTE",menuItem.toString());
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if(menuItem.getItemId()==R.id.menu1){
                            fragmentClass = fragment1.class;
                        }
                        else if(menuItem.getItemId()==R.id.menu2){
                            fragmentClass = fragment2.class;
                        }
                        else if(menuItem.getItemId()==R.id.menu3){
                            fragmentClass = fragment3.class;
                        }
                        else if(menuItem.getItemId()==R.id.menu4){
                            fragmentClass = fragment4.class;
                        }

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

                        return true;
                    }
                });
        //firebase stuff for notifications
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNoti = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID,Constants.CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);

            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            mNoti.createNotificationChannel(mChannel);




        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private final OkHttpClient client = new OkHttpClient();
    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCl1ihalsfme2TpTFg1TPXdg&maxResults=25&order=date&key=AIzaSyAYUGkL36EioOyA4EfyQL9vjm0-lf7JW5s%22")
                .build();
        Log.i("url",""+request.toString());
        Log.i("header",""+request.headers());

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("INTENTINTENTINTENTINTE","fail");
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String in = response.body().string();
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa"+in);

                    try {

                        JSONObject jObj1 = new JSONObject(in);
                        JSONArray data  = jObj1.getJSONArray("data");
                        String temperature = data.getString(0);
                        JSONObject jObj2 = new JSONObject(temperature);
                        String login = jObj2.getString("login");
                        String displayname = jObj2.getString("display_name");
                        String profile_image_url = jObj2.getString("profile_image_url");
                        String email = jObj2.getString("email");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
    public void changetotwitchactivity() {
            Log.i("INTENTINTENTINTENTINTE","changerun");
            Intent intent = new Intent(this, twitchAuthO.class);
            startActivity(intent);
        }
    @Override
    public void onDestroy() {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        SharedPreferences.Editor mEditor = mPrefs.edit();
        if(user=="0"){

            mEditor.putString("saved", "0").commit();
            mEditor.putString("saved", "0").apply();

        }
        else{
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
        }



        super.onDestroy();

    }
    public void onResume()
    {

        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        Log.i("tag","onstart"+saved);
        if(saved=="0"){

            changetotwitchactivity();
        }
        else{

        }
        super.onResume();

    }

    public void onPause()
    {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        SharedPreferences.Editor mEditor = mPrefs.edit();
        Log.i("tag","onp"+saved+user);
        if(user=="0"){

            mEditor.putString("saved", "0").commit();
            mEditor.putString("saved", "0").apply();
            changetotwitchactivity();
        }
        else{
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
        }

        super.onPause();

    }
    public void onStop()
    {
        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        String saved = mPrefs.getString("saved", "0");
        String user = mPrefs.getString("login", "0");
        Log.i("tag","onstop"+saved+user);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        if(user=="0"){

            mEditor.putString("saved", "0").commit();
            mEditor.putString("saved", "0").apply();
        }
        else{
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
        }

        super.onStop();

    }


    }

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
        Log.i("after",data+" "+saved);
            if(data==100){

            }
            else{
                changetotwitchactivity();
            }



        if(saved=="1"){

        }
        else{
            changetotwitchactivity();
        }



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
                mEditor.putString("login", "").commit();
                mEditor.putString("email", "").commit();
                mEditor.putString("pic", "").commit();
                mEditor.putString("saved", "0").commit();
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

    public void changetotwitchactivity() {


            Log.i("INTENTINTENTINTENTINTE","changerun");

            Intent intent = new Intent(this, twitchAuthO.class);
            startActivity(intent);
        }


    }

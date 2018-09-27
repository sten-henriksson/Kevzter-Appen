package com.example.oauthtest.kevzterfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class twitchAuthO extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitch_auth_o);


        final Button button = findViewById(R.id.authbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openWebPage("https://id.twitch.tv/oauth2/authorize?response_type=token+id_token&client_id=jbccawmekfaghvtagoob984qgl6f65&redirect_uri=my.special.scheme:/oauth2callback&scope=user:edit+user:read:email+openid&state=c3ab8aa609ea11e793ae92361f002671");
            }
        });
        Intent intent = getIntent();
        Uri uri = intent.getData();

        if (uri != null && uri.toString()
                .startsWith("my.special.scheme://"))
        {

            String[] params = uri.toString().split("&");
            Map<String, String> map = new HashMap<String, String>();
            for (String param : params)
            {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
            Log.i("INTENTINTENTINTENTINTE",""+map.get("my.special.scheme:///oauth2callback#access_token"));
            try {
                run(map.get("my.special.scheme:///oauth2callback#access_token"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }



    }


    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private final OkHttpClient client = new OkHttpClient();

    public void run(String bearer) throws Exception {
        Request request = new Request.Builder()
                .url("https://api.twitch.tv/helix/users").removeHeader("tags").addHeader("Authorization","Bearer "+bearer)
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
                        Globals g = Globals.getInstance();
                        g.setUser(login);
                        g.setEmail(email);
                        g.setPicture(profile_image_url);
                        g.setData(100);
                        SharedPreferences mPrefs = getSharedPreferences("label", 0);
                        SharedPreferences.Editor mEditor = mPrefs.edit();
                        mEditor.putString("login", login).commit();
                        mEditor.putString("email", email).commit();
                        mEditor.putString("pic", profile_image_url).commit();
                        mEditor.putString("saved", "1").commit();


                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
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


        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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

        }
        else{
            mEditor.putString("saved", "1").commit();
            mEditor.putString("saved", "1").apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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


package com.example.oauthtest.kevzterfinal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

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

        }



    }


    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}


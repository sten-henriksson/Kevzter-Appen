package com.example.oauthtest.kevzterfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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

public class fortnitefragment extends Fragment {

    String[] itemname ={

    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragmentfortnite, container, false);
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("fortnite", 0);
        String number = "10";
        int i = Integer.parseInt(number);

        itemname = new String[i];


        i--;

        while(i>=0){

            String loopintt = String.valueOf(i);

            String bild = mPrefs.getString("shoppic"+loopintt, "0");


            itemname[i]=bild;

            i--;
        }
        int i2 = 0;
        i=6;
        while(i>=0){

            String loopintt = String.valueOf(i);

            String bild = mPrefs.getString("shoppicf"+loopintt, "0");

            Log.i("arrayitemname",""+bild) ;
            if(bild.length()>1){
                itemname[i2+6]=bild;
                Log.i("arrays",""+itemname[i+6]) ;
                i2++;
            }


            i--;
        }

        Log.i("arrays",""+itemname.length) ;


        ListView list = (ListView)view.findViewById(R.id.listmenufortnite);

        Log.i("arrayy",itemname.toString());
        CustomListAdapterFortnite adapter=new CustomListAdapterFortnite(this.getActivity(), itemname);

        list.setAdapter(adapter);

        list.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView , View view , int position ,long arg3)
            {
                Log.i("Itemclicked","tushar:itemclicked"+position) ;

            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int a = metrics.heightPixels;

        Log.i("metrics",metrics.toString());

        ViewGroup.LayoutParams params = list.getLayoutParams();
        float b = metrics.scaledDensity;
        if(b==1.5f){
            params.height = 1750;
            Log.i("metrics",""+b);
        }
        else if(b==3f){
            params.height = 1650;
            Log.i("metrics",""+b);
        }

        list.setLayoutParams(params);

        return view;
    }
    private final OkHttpClient client = new OkHttpClient();

    public void openWebPage(String url) {
        Uri webpage = Uri.parse("https://www.youtube.com/watch?v="+url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(this.getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
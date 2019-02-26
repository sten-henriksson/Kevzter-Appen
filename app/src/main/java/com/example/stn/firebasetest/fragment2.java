package com.example.stn.firebasetest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Arrays;
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

public class fragment2 extends Fragment {

    String[] itemname ={

    };

    String[] itemname2={



    };
    String[] Urls={



    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment2layout, container, false);


        Log.i("arrays",""+itemname.length+""+itemname2.length+""+Urls.length) ;
        SharedPreferences mPrefs = this.getActivity().getSharedPreferences("label", 0);
        String number = mPrefs.getString("numberof", "0");
        int i = Integer.parseInt(number);
        itemname2 = new String[i];
        itemname = new String[i];
        Urls = new String[i];

        i--;
        while(i>=0){

            String loopintt = String.valueOf(i);
            String titel = mPrefs.getString("titel"+loopintt, "0");
            String bild = mPrefs.getString("bild"+loopintt, "0");
            String videoId = mPrefs.getString("videoId"+loopintt, "0");
            itemname2[i]=titel;
            itemname[i]=bild;
            Urls[i]=videoId;
            i--;
        }
        Log.i("arrays",""+itemname.length+""+itemname2.length+""+Urls.length) ;

        //fixes the bug with diffren resultions on diffrent phones
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int a = metrics.heightPixels;
        Log.i("metrics",metrics.toString());

        ListView list = (ListView)view.findViewById(R.id.listmenu);
        ViewGroup.LayoutParams params = list.getLayoutParams();
        //fix for overlapping toolbar
        float b = metrics.scaledDensity;

        list.setLayoutParams(params);
        CustomListAdapter adapter=new CustomListAdapter(this.getActivity(), itemname, itemname2);

        list.setAdapter(adapter);

        list.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView , View view , int position ,long arg3)
            {
                Log.i("Itemclicked","tushar:itemclicked"+position) ;
                String url = Urls[position];
                openWebPage(url);
            }
        });


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
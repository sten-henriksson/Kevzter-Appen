package com.example.oauthtest.kevzterfinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


        ListView list = (ListView)view.findViewById(R.id.listmenu);

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
    public void run() throws Exception {
        Log.i("json","start");
        Request request = new Request.Builder()
                .url("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=UCl1ihalsfme2TpTFg1TPXdg&maxResults=10&order=date&key=AIzaSyAYUGkL36EioOyA4EfyQL9vjm0-lf7JW5s")
                .build();
        Log.i("url",""+request.toString());
        Log.i("header",""+request.headers());

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.i("INTENTINTENTINTENTINTE","fail");
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                Log.i("json","start1");
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                    Log.i("json","start2");
                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    String in = response.body().string();
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa"+in);

                    try {


                        JSONObject jObj1 = new JSONObject(in);
                        JSONArray data  = jObj1.getJSONArray("items");

                        String temperature = data.getString(0);
                        JSONObject jObj2 = new JSONObject(temperature);
                        String temp2 = jObj2. getString("snippet");
                        JSONObject jObj3 = new JSONObject(temp2);
                        //gettitel here
                        String temp3 = jObj3.getString("thumbnails");
                        //Getthumbnailhere
                        JSONObject jObj4 = new JSONObject(temp3);

                        String temp4 = jObj4.getString("default") ;
                        JSONObject jObj5 = new JSONObject(temp4);
                        String temp5 = jObj2.getString("id");
                        JSONObject jObj6= new JSONObject(temp5);


                        String bild = jObj5.getString("url");
                        String titel = jObj3.getString("title");
                        String videoid = jObj6.getString("videoId");


                        itemname2 = new String[10];
                        itemname2[0]=titel;


                        //bild
                        Log.i("json1",bild+" "+titel+" "+itemname2[0]);





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse("https://www.youtube.com/watch?v="+url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(this.getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
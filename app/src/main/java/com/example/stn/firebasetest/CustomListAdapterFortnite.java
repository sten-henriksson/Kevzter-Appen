package com.example.stn.firebasetest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomListAdapterFortnite extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itempic;

    int ab=0;
    public CustomListAdapterFortnite(Activity context, String[] itempic) {
        super(context, R.layout.mylist, itempic);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itempic=itempic;

    }

    public View getView(int position,View view,ViewGroup parent) {
        //position = ab;
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.fortnitelist, null,true);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView4);
        ImageView imageView1 = (ImageView) rowView.findViewById(R.id.imageView6);


        SharedPreferences mPrefs = context.getSharedPreferences("fortnite", 0);





















        int a = position;
        int b = position;
        if(position==0){
            a = 0;
            b = 1;
        }
        if(position==1){
            a = 2;
            b = 3;
        }
        if(position==2){
            a = 4;
            b = 5;
        }
        if(position==3){
            a = 6;
            b = 7;
        }
        if(position==4){
            a = 8;
            b = 9;
        }
        if(position==5){
            a = 10;
            b = 11;
        }
        if(position==6){
            a = 12;
            b = 13;
        }
        if(position==7){
            a = 14;
            b = 15;
        }
        if(position==8){
            a = 16;
            b = 17;
        }
        if(position>9){
            a = 18;
            b = 19;
        }

        int lenght1 = mPrefs.getInt("lenghtf", 0);

        if(b>=lenght1){
            b = 99;
        }
        if(a>=lenght1){
            a = 99;
        }
        String bild = mPrefs.getString("shoppic"+a, "0");
        String bild1 = mPrefs.getString("shoppic"+b, "0");
        Log.i("INTENTINTENTINTENTINTE",""+position+" "+a+" "+b+" "+lenght1);
        try
        {

            Picasso.get().load(bild).into(imageView);//statements that may cause an exception

        }
        catch (Exception e){

        }
        try
        {

            Picasso.get().load(bild1).into(imageView1);//statements that may cause an exception

        }
        catch (Exception e){

        }
        ab++;

        return rowView;

    };
}
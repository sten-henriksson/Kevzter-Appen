package com.example.stn.firebasetest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class fragment1 extends Fragment {
    private Fragment fragment = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment1layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView twitch = (ImageView) view.findViewById(R.id.twitchtext);
        twitch.setClickable(true);
        twitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.twitch.tv/kevzter");
            }
        });

        ImageView youtube = (ImageView) view.findViewById(R.id.youtubetext);
        youtube.setClickable(true);
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = (Fragment) fragment2.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        ImageView instagram = (ImageView) view.findViewById(R.id.instagram);
        instagram.setClickable(true);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.instagram.com/Kevzter/");
            }
        });

        ImageView snapchat = (ImageView) view.findViewById(R.id.snapchat);
        snapchat.setClickable(true);
        snapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://snapchat.com/add/Kevzter92");
            }
        });

        ImageView itemshop = (ImageView) view.findViewById(R.id.itemshop);
        itemshop.setClickable(true);
        itemshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fragment = (Fragment) fortnitefragment.class.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        ImageView ads = (ImageView) view.findViewById(R.id.add);
        ads.setClickable(true);
        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showad();
            }
        });

    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse( url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(this.getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}

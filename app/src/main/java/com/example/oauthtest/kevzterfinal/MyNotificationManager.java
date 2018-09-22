package com.example.oauthtest.kevzterfinal;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyNotificationManager {
    private Context mCtx;
    private static MyNotificationManager mInstance;
    private  MyNotificationManager(Context context){
        mCtx = context;
    }
    public static synchronized MyNotificationManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    };

    public void displayNotification(String title,String body){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(body);
        //what will open noti
        Intent intent = new Intent(mCtx,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationmanager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        if(mNotificationmanager != null){
            mNotificationmanager.notify(1,mBuilder.build());
        }
    }


}

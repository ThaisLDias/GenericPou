package com.example.thaisdias.jamvpoupou;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class PouService extends Service {

    public static Boolean hungry = false;
    public static Boolean thirsty = false;
    public PouService() {
    }

    @Override
    public ComponentName startService(Intent service) {

        return super.startService(service);
    }

    private void Send()
    {

        SharedPreferences sh;
        sh = this.getSharedPreferences("poujamv", Context.MODE_PRIVATE);
        if(sh.getFloat("Hunger",50)<25) {hungry = true; Log.d("Disgurpe",hungry+"");}
        else {thirsty = true;Log.d("Disgurpe2",thirsty+"");}
        //Building the notification.
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.celoba)
                .setContentTitle((sh.getFloat("Hunger",50)<25)?"Celoba is hungry!":"Celoba is thirsty!")
                .setContentText((sh.getFloat("Hunger",50)<25)?"Feed him!":"Give him water!")
                .setAutoCancel(true);

        //Intent to be started if user activate the notification panel.
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        //Connecting notification with intent.
        mBuilder.setContentIntent(pi);

        //Need permission on manifest.
        mBuilder.setVibrate(new long[] {100, 250, 100, 500});

        //Getting the OS notification service.
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Sending the notification.
        mNotificationManager.notify(R.string.app_name, mBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }

        SharedPreferences sh;
        sh = this.getSharedPreferences("poujamv", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sh.edit();
        ed.putFloat("Thirsty",sh.getFloat("Thirsty",50) -2);
        if(sh.getFloat("Thirsty",50)<0)ed.putFloat("Thirsty",0);
        ed.putFloat("Hunger",sh.getFloat("Hunger",50) -1);
        if(sh.getFloat("Hunger",50)<0)ed.putFloat("Hunger",0);
        ed.apply();
        if ((sh.getFloat("Thirsty",50) <= 24 && !thirsty) || (sh.getFloat("Hunger",50) <= 24 && !hungry)) {Send();}

        startService(intent);
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

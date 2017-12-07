package com.example.androiddevelopment.glumcilegende.async;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.tools.ReviewerTools;

/**
 * Created by BBLOJB on 7.12.2017.
 *  *
 * BroadcastReceiver je komponenta koja moze da reaguje na poruke drugih delova
 * samog sistema kao i korisnicki definisanih. Cesto se koristi u sprezi sa
 * servisima i asinhronim zadacima.
 *
 * Pored toga on moze da reaguje i na neke sistemske dogadjaje prispece sms poruke
 * paljenje uredjaja, novi poziv isl.
 */

public class SimpleReceiver extends BroadcastReceiver {

    private static int notificationID = 1;

    /**
     * Moramo pozvati unutar BroadcastReceiver-a zato sto on ima vezu ka nasoj aktivnosti
     * gde se lista zapravo nalazi.
     * */
    private void readFAFL(Context context){
        //Load glumac names from array resource

        String[] glumci = ReviewerTools.readFF(context, "myfile.txt").split("\n");

        //Create an ArrayAdapter from the array of Strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_item, glumci);
        ListView listView = (ListView) ((Activity)context).findViewById(R.id.glumci);

        //Assign adapter to ListView
        listView.setAdapter(adapter);
    }
    @Override
    /**
     * Intent je bitan parametar za BroadcastReceiver. Kada posaljemo neku poruku,
     * ovaj Intent cuva akciju i podatke koje smo mu poslali.
     * */
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        Log.i("MY_ANDROID_APP", "Receive");
        /**
         * Posto nas BroadcastReceiver reaguje samo na jednu akciju koju smo definisali
         * dobro je da proverimo da li smo dobili bas tu akciju. Ako jesmo onda mozemo
         * preuzeti i sadrzaj ako ga ima.
         *
         * Voditi racuna o tome da se naziv akcije kada korisnik salje Intent mora poklapati sa
         * nazivom akcije kada akciju proveravamo unutar BroadcastReceiver-a. Isto vazi i za podatke.
         * Dobra praksa je da se ovi nazivi izdvoje unutar neke staticke promenljive.
         * */

         if(intent.getAction().equals("SYNC_DATA")) {
            boolean showMessage = sharedPreferences.getBoolean(context.getString(R.string.allow_message), false);

        if (showMessage){
            int resultCode = intent.getExtras().getInt("RESULT_CODE");
            prepareNotification(resultCode, context);
        }
        readFAFL(context);
        }
    }
    //male izmene i na metodi koja formira notifikacije
    //Ako je stigla poruka na filter za prikaz poruke komentara
    //poslacemo i sadrzaj poruke i naslov Bundle objekat
    //Takodje cemo poslati i drugi notificationID
    //posto hocemo da prikazemo dve razlicite vrste poruka, moramo definisati i dva id-a
    private void prepareNotification(int resuletCode, Context context) {
        NotificationManager mnm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        Log.i("MY_ANDROID_APP", "Notif");

        if (resuletCode == ReviewerTools.TYPE_NOT_CONNECTED) {
            mBuilder.setSmallIcon(R.drawable.ic_action_settings);
            mBuilder.setContentTitle(context.getString(R.string.autosync_problem));
            mBuilder.setContentText(context.getString(R.string.no_internet));

        } else if (resuletCode == ReviewerTools.TYPE_MOBILE) {
            mBuilder.setSmallIcon(R.drawable.ic_action_like);
            mBuilder.setContentTitle(context.getString(R.string.autosync_warning));
            mBuilder.setContentText(context.getString(R.string.connect_to_wifi));
        } else {
            mBuilder.setSmallIcon(R.drawable.ic_action_refresh);
            mBuilder.setContentTitle(context.getString(R.string.autosync));
            mBuilder.setContentText(context.getString(R.string.good_news_sync));
        }

        mBuilder.setLargeIcon(bm);
        // notificationID allows you to update the notification later on.
        mnm.notify(notificationID, mBuilder.build());

    }
}

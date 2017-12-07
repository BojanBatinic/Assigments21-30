package com.example.androiddevelopment.glumcilegende.async;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

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

    @Override
    /**
     * Intent je bitan parametar za BroadcastReceiver. Kada posaljemo neku poruku,
     * ovaj Intent cuva akciju i podatke koje smo mu poslali.
     * */
    public void onReceive(Context context, Intent intent) {
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

        int resultCode = intent.getExtras().getInt("RESULT_CODE");
        if(intent.getAction().equals("SYNC_DATA")) {

            prepareNotification(resultCode, context, 1, null);
        }else if (intent.getAction().equals("MY_COMMENT")){
            prepareNotification(resultCode, context, 2, intent.getExtras());
        }
    }
    //male izmene i na metodi koja formira notifikacije
    //Ako je stigla poruka na filter za prikaz poruke komentara
    //poslacemo i sadrzaj poruke i naslov Bundle objekat
    //Takodje cemo poslati i drugi notificationID
    //posto hocemo da prikazemo dve razlicite vrste poruka, moramo definisati i dva id-a
    private void prepareNotification(int resuletCode, Context context, int notifID, Bundle bundle){
        NotificationManager mnm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);

        if (bundle == null){

            if (resuletCode == ReviewerTools.TYPE_NOT_CONNECTED) {
                mBuilder.setSmallIcon(R.drawable.ic_action_settings);
                mBuilder.setContentTitle(context.getString(R.string.autosync_problem));
                mBuilder.setContentText(context.getString(R.string.no_internet));

            }else if (resuletCode == ReviewerTools.TYPE_MOBILE) {
                mBuilder.setSmallIcon(R.drawable.ic_action_like);
                mBuilder.setContentTitle(context.getString(R.string.autosync_warning));
                mBuilder.setContentText(context.getString(R.string.connect_to_wifi));
            }else{
                mBuilder.setSmallIcon(R.drawable.ic_action_refresh);
                mBuilder.setContentTitle(context.getString(R.string.autosync));
                mBuilder.setContentText(context.getString(R.string.good_news_sync));
            }

        }else{
            String title = bundle.getString("title");
            String comment = bundle.getString("comment");

            mBuilder.setSmallIcon(R.drawable.ic_action_settings);
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(comment);
        }
        mBuilder.setLargeIcon(bm);
        //notificationID allows you to update the notification later on.
        mnm.notify(notifID, mBuilder.build());
    }
}

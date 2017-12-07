package com.example.androiddevelopment.glumcilegende.async;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.fragments.ListFragment;
import com.example.androiddevelopment.glumcilegende.tools.ReviewerTools;

import java.net.Proxy;
import java.util.List;


/**
 * Created by BBLOJB on 24.11.2017..
 * AsyncTask klasa prima tri parametra prilikom specijalizacije
 * Korisnici sami definisu tip u zavisnosti od posla koji zele da obave.
 *
 * Prvi argument predstavlja ulazne parametre, ono sto zelimo
 * da posaljemo zadatku. Recimo ime fajla koji zelimo da skinemo
 *
 * Drugi argument je indikator kako ce se meriti progres. Koliko je posla
 * zavrseno i koliko je posla ostalo.
 *
 * Treci parametar je povratna vrednost, tj sta ce metoda doInBackground
 * vratiti kao povratnu vrednost metodi onPostExecute
 */

public class SimpleSyncTask extends AsyncTask<Integer, Void, Integer>{

    private Context context;

    public SimpleSyncTask(Context context){
        this.context = context;
    }
    /**
     * Metoda se poziva pre samog starta pozadinskog zadatka
     * Sve pripreme odraditi u ovoj metodi, ako ih ima.
     */
    @Override
    protected void onPreExecute(){

    }

    /**
     * Posao koji se odvija u pozadini, ne blokira glavnu nit aplikacije.
     * Sav posao koji dugo traje izvrsavati unutar ove metode.
     */
    @Override
    protected Integer doInBackground(Integer... params) {
        try {
            //simulacija posla koji se obavlja u pozadini i traje duze vreme
            Thread.sleep(500);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return params[0];
    }

   /* private void createNotification(String contentTitle, String contentText){

        NotificationManager mnm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Build the notification using Notification.Builder
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setAutoCancel(true)
                .setContentTitle(contentTitle)
                .setContentText(contentText);

        //Show the notification
        mnm.notify(1, builder.build());
    }*/

    /**
     * Kada se posao koji se odvija u pozadini zavrsi, poziva se ova metoda
     * Ako je potrebno osloboditi resurse ili obrisati elemente koji vise ne trebaju.
     */
    @Override
    protected void onPostExecute(Integer type){
      /*  String text = ReviewerTools.getConnectionType(type);
        Toast.makeText( context, text, Toast.LENGTH_SHORT).show();
        //dodatni zadak
        createNotification("Termin 22 dodatni zadatak", text);*/
        //Toast.makeText(context, ReviewerTools.getConnectionType(type), Toast.LENGTH_SHORT).show();
        /**
         * Da bi poslali poruku BroadcastReceiver-u poterbno je da definisiemo Intent sa sadrzajem.
         * Definisemo intent i sa njim nasu akciju SYNC_DATA. Ovo radimo da bi BroadcastReceiver
         * znao kako da reaguje kada dobije poruku tog tipa.
         * Uz poruku mozemo vezati i neki sadrazaj RESULT_CODE u ovom slucaju.
         * Jedan BroadcastReceiver moze da prima vise poruka iz aplikacije i iz tog razloga definisanje
         * akcije je bitna stvar.
         *
         * Voditi racuna o tome da se naziv akcije kada korisnik salje Intent mora poklapati sa
         * nazivom akcije kada akciju proveravamo unutar BroadcastReceiver-a. Isto vazi i za podatke.
         * Dobra praksa je da se ovi nazivi izdvoje unutar neke staticke promenljive.
         * */
        Intent ints = new Intent("SYNC_DATA");
        ints.putExtra("RESULT_CODE", type);
        context.sendBroadcast(ints);

    }

}

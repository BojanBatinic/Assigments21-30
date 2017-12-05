package com.example.androiddevelopment.glumcilegende.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.fragments.ListFragment;

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

public class SimpleSyncTask extends AsyncTask<Void, Void, Void>{

    private Activity activity;
    private ListFragment.OnProductSelectedListener listener;

    public SimpleSyncTask(Activity activity){
        this.activity = activity;
        listener = (ListFragment.OnProductSelectedListener) activity;
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
    protected Void doInBackground(Void... params) {
        try {
            //simulacija posla koji se obavlja u pozadini i traje duze vreme
            Thread.sleep(6000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private void fillGlumci(){
        // Load glumci names from array resource
        String[] glumci = activity.getResources().getStringArray(R.array.glumci);
        // Create an ArrayAdaptar from the array of Strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.list_item, glumci);
        ListView listView = (ListView) activity.findViewById(R.id.glumci);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // send the URL to the host activity
                listener.onProductSelected((int)id);
            }
        });
    }

    /**
     * Kada se posao koji se odvija u pozadini zavrsi, poziva se ova metoda
     * Ako je potrebno osloboditi resurse ili obrisati elemente koji vise ne trebaju.
     */
    @Override
    protected void onPostExecute(Void aVoid){
        Toast.makeText(activity, "Sync done", Toast.LENGTH_SHORT).show();
        fillGlumci();
    }

}

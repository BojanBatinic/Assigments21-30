package com.example.androiddevelopment.glumcilegende.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.activities.MainActivity;
import com.example.androiddevelopment.glumcilegende.db.model.Film;
import com.example.androiddevelopment.glumcilegende.db.model.Glumac;
import com.example.androiddevelopment.glumcilegende.provider.FilmProvider;
import com.example.androiddevelopment.glumcilegende.provider.GlumacProvider;
import com.example.androiddevelopment.glumcilegende.tools.ReviewerTools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by BBLOJB on 21.11.2017..
 */

// Each fragment extends Fragment class
public class DetailFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static int NOTIFICATION_ID = 1;

    private Glumac glumac = null;

    // onCreate method is a life-cycle method that is called when creating the fragment.
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

       /* if (glumac == null) {
            glumac = GlumacProvider.getGlumacById(0);
        }*/

    }

    // onActivityCreated method is a life-cycle method that is called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Shows a toast message (a pop-up message)
        // Toast.makeText(getActivity(), "DetailFragment.onActivityCreated()", Toast.LENGTH_SHORT).show();

        if (savedInstanceState != null) {
            glumac = new Glumac();
            glumac.setmId(savedInstanceState.getInt("id"));
            glumac.setmName(savedInstanceState.getString("name"));
            glumac.setBiografija(savedInstanceState.getString("biografija"));
            glumac.setRating(savedInstanceState.getFloat("rating"));
            glumac.setImage(savedInstanceState.getString("image"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            savedInstanceState.putInt("id", glumac.getmId());
            savedInstanceState.putString("name", glumac.getmName());
            savedInstanceState.putString("biografija", glumac.getBiografija());
            savedInstanceState.putFloat("rating", glumac.getRating());
            savedInstanceState.putString("image", glumac.getImage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("DetailFragment", "OnCreateView");

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(glumac.getmName());

        //finds "tvBiografija" TextView and sets "text" property
        TextView biografija = (TextView) view.findViewById(R.id.biografija);
        biografija.setText(glumac.getBiografija());

        //finds "rbRating" RatingBar and sets "rating" property
        RatingBar rating = (RatingBar) view.findViewById(R.id.rating);
        rating.setRating(glumac.getRating());

        //Finds "ivImage" ImageView and sets "imageDrawable" property
        ImageView ivImage = (ImageView) view.findViewById(R.id.image);
        InputStream is = null;
        try {
            is = getActivity().getAssets().open(glumac.getImage());
            Drawable drawable = Drawable.createFromStream(is, null);
            ivImage.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //finds "spFilm" Spiner and sets "selection" property
        Spinner spinner = (Spinner) view.findViewById(R.id.film);

        try {
            List<com.example.androiddevelopment.glumcilegende.db.model.Film> list = ((MainActivity) getActivity()).getDatabaseHelper().getFilmDao().queryForAll();
            ArrayAdapter<com.example.androiddevelopment.glumcilegende.db.model.Film> dataAdapter = new ArrayAdapter<Film>(getActivity(), android.R.layout.simple_spinner_item, list);
            spinner.setAdapter(dataAdapter);

            for (int i=0; i<list.size(); i++){
               if (list.get(i).getId() == glumac.getFilm().getId()){
                   spinner.setSelection(i);
                   break;
               }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return view;
    }

    public void setGlumac(Glumac glumac) {
        this.glumac = glumac;
    }


   /* public void updateGlumac(Glumac glumac) {
        this.glumac = glumac;

        EditText name = (EditText) getActivity().findViewById(R.id.name);
        name.setText(glumac.getmName());

        //finds "tvBiografija" TextView and sets "text" property
        EditText biografija = (EditText) getActivity().findViewById(R.id.biografija);
        biografija.setText(glumac.getBiografija());

        //finds "rbRating" RatingBar and sets "rating" property
        RatingBar rating = (RatingBar) getActivity().findViewById(R.id.rating);
        rating.setRating(glumac.getRating());

        //Finds "ivImage" ImageView and sets "imageDrawable" property
        ImageView ivImage = (ImageView) getActivity().findViewById(R.id.image);
        InputStream is = null;
        try {
            is = getActivity().getAssets().open(glumac.getImage());
            Drawable drawable = Drawable.createFromStream(is, null);
            ivImage.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Spinner film = (Spinner) getActivity().findViewById(R.id.film);
        film.setSelection(glumac.getFilm().getId());*/
    //}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // You can retrieve the selected item using
        //product.setFilm(FilmProvider.getFilmById((int)id));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //product.setFilm(null);
    }

    /**
     * Kada dodajemo novi element u toolbar potrebno je da obrisemo prethodne elmente
     * zato pozivamo menu.clear() i dodajemo nove toolbar elemente
     * */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        menu.clear();
        inflater.inflate(R.menu.detail_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void updateGlumac(Glumac glumac) {
        this.glumac = glumac;

        EditText name = (EditText) getActivity().findViewById(R.id.name);
        name.setText(glumac.getmName());

        //finds "tvBiografija" TextView and sets "text" property
        EditText biografija = (EditText) getActivity().findViewById(R.id.biografija);
        biografija.setText(glumac.getBiografija());

        //finds "rbRating" RatingBar and sets "rating" property
        RatingBar rating = (RatingBar) getActivity().findViewById(R.id.rating);
        rating.setRating(glumac.getRating());

        //Finds "ivImage" ImageView and sets "imageDrawable" property
        ImageView ivImage = (ImageView) getActivity().findViewById(R.id.image);
        InputStream is = null;
        try {
            is = getActivity().getAssets().open(glumac.getImage());
            Drawable drawable = Drawable.createFromStream(is, null);
            ivImage.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* Spinner film = (Spinner) getActivity().findViewById(R.id.film);
        film.setSelection(glumac.getFilm().getId());*/
    }

    public void doUpdateElement(){
        if (glumac != null){
            EditText name = (EditText) getActivity().findViewById(R.id.name);
            glumac.setmName(name.getText().toString());

            EditText biografija = (EditText) getActivity().findViewById(R.id.biografija);
            glumac.setBiografija(biografija.getText().toString());

            RatingBar ratingBar = (RatingBar) getActivity().findViewById(R.id.rating);
            glumac.setRating(ratingBar.getRating());

            Spinner film = (Spinner) getActivity().findViewById(R.id.film);
            com.example.androiddevelopment.glumcilegende.db.model.Film f = (com.example.androiddevelopment.glumcilegende.db.model.Film)film.getSelectedItem();
            glumac.setFilm(f);

            try {
                ((MainActivity) getActivity()).getDatabaseHelper().getGlumacDao().update(glumac);
            }catch (SQLException e){
                e.printStackTrace();
            }
            getActivity().onBackPressed();
        }
    }

    private void doRemoveElement(){
        if (glumac != null){
            try{
                ((MainActivity) getActivity()).getDatabaseHelper().getGlumacDao().delete(glumac);
            }catch (SQLException e){
                e.printStackTrace();
            }
            getActivity().onBackPressed();
        }
    }
/**
 * Na fragment dodajemo element za brisanje elementa i za izmenu podataka
 * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.remove:
                doRemoveElement();
                break;
            case R.id.update:
                doUpdateElement();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

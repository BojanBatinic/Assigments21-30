package com.example.androiddevelopment.glumcilegende.fragments;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.model.Film;
import com.example.androiddevelopment.glumcilegende.model.Glumac;
import com.example.androiddevelopment.glumcilegende.provider.FilmProvider;
import com.example.androiddevelopment.glumcilegende.provider.GlumacProvider;

import java.io.IOException;
import java.io.InputStream;


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

        if (glumac == null) {
            glumac = GlumacProvider.getGlumacById(0);
        }

    }

    // onActivityCreated method is a life-cycle method that is called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Shows a toast message (a pop-up message)
        // Toast.makeText(getActivity(), "DetailFragment.onActivityCreated()", Toast.LENGTH_SHORT).show();

        if (savedInstanceState != null) {
            glumac = new Glumac();
            glumac.setId(savedInstanceState.getInt("id"));
            glumac.setName(savedInstanceState.getString("name"));
            glumac.setBiografija(savedInstanceState.getString("biografija"));
            glumac.setRating(savedInstanceState.getFloat("rating"));
            int filmId = savedInstanceState.getInt("film_id");
            Film film = FilmProvider.getFilmById(filmId);
            glumac.setFilm(film);
            glumac.setImage(savedInstanceState.getString("image"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            savedInstanceState.putInt("id", glumac.getId());
            savedInstanceState.putString("name", glumac.getName());
            savedInstanceState.putString("biografija", glumac.getBiografija());
            savedInstanceState.putFloat("rating", glumac.getRating());
            savedInstanceState.putInt("film_id", glumac.getFilm().getId());
            savedInstanceState.putString("image", glumac.getImage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v("DetailFragment", "OnCreateView");

        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(glumac.getName());

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
        Spinner film = (Spinner) view.findViewById(R.id.film);
        String[] filmovi = getActivity().getResources().getStringArray(R.array.film_names);
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, filmovi);
        film.setAdapter(adapter);
        film.setSelection(glumac.getFilm().getId());

        return view;
    }

    public void setGlumac(int id) {
        glumac = GlumacProvider.getGlumacById(id);
    }

    public void updateProduct(int id) {
        glumac = GlumacProvider.getGlumacById(id);

        TextView name = (TextView) getActivity().findViewById(R.id.name);
        name.setText(glumac.getName());

        //finds "tvBiografija" TextView and sets "text" property
        TextView biografija = (TextView) getActivity().findViewById(R.id.biografija);
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

        Spinner film = (Spinner) getActivity().findViewById(R.id.film);
        film.setSelection(glumac.getFilm().getId());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // You can retrieve the selected item using
        //product.setFilm(CategoryProvider.getCategoryById((int)id));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //product.setFilm(null);
    }

}

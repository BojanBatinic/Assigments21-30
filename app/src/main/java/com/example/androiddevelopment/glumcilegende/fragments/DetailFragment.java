package com.example.androiddevelopment.glumcilegende.fragments;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.androiddevelopment.glumcilegende.R;
import com.example.androiddevelopment.glumcilegende.activities.MainActivity;
import com.example.androiddevelopment.glumcilegende.db.model.Glumac;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;


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

        //finds "tvFilm" TextView and sets "text" property
        TextView film = (TextView) view.findViewById(R.id.film);
        film.setText(glumac.getFilm().getName());

        //finds "rbRating" RatingBar and sets "rating" property
        RatingBar ratingB = (RatingBar) view.findViewById(R.id.rating);
        ratingB.setRating(glumac.getRating());

        //Finds "ivImage" ImageView and sets "imageDrawable" property
        ImageView ivImage = (ImageView) view.findViewById(R.id.image);

        Uri mUri = Uri.parse(glumac.getImage());
        ivImage.setImageURI(mUri);

        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.like);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creates notification with the notification builder
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_action_like);
                builder.setSmallIcon(R.drawable.ic_action_like);
                builder.setContentTitle(getActivity().getString(R.string.notification_title));
                builder.setContentText(getActivity().getString(R.string.notification_text));
                builder.setLargeIcon(bitmap);

                // Shows notification with the notification manager (notification ID is used to update the notification later on)
                NotificationManager manager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(NOTIFICATION_ID, builder.build());
            }
        });

        return view;
    }

    public void setGlumac(Glumac glumac) {
        this.glumac = glumac;
    }


    public void updateGlumac(Glumac glumac) {
        this.glumac = glumac;

        TextView name = (TextView) getActivity().findViewById(R.id.name);
        name.setText(glumac.getmName());

        //finds "tvBiografija" TextView and sets "text" property
        TextView biografija = (TextView) getActivity().findViewById(R.id.biografija);
        biografija.setText(glumac.getBiografija());

        //finds "rbRating" RatingBar and sets "rating" property
        RatingBar ratingB = (RatingBar) getActivity().findViewById(R.id.rating);
        ratingB.setRating(glumac.getRating());

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

    }

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


/**
 * Na fragment dodajemo element za brisanje elementa i za izmenu podataka
 * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.remove:
                try{
                    if(glumac != null){
                        ((MainActivity) getActivity()).getDatabaseHelper().getGlumacDao().delete(glumac);
                        getActivity().onBackPressed();
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

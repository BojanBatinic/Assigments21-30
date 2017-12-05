package com.example.androiddevelopment.glumcilegende.provider;

import com.example.androiddevelopment.glumcilegende.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BBLOJB on 20.11.2017..
 */

public class FilmProvider {

    public static List<Film> getFilmovi(){

        List<Film> filmovi = new ArrayList<>();
        filmovi.add(new Film(0, "Partizanski"));
        filmovi.add(new Film(1, "Mangupski"));
        filmovi.add(new Film(2, "Komedija"));
        return filmovi;
    }

    public static Film getFilmById(int id){
        switch (id){
            case 0:
                return new Film(0, "Partizanski");
            case 1:
                return new Film(1, "Mangupski");
            case 2:
                return new Film(2, "Komedija");
            default:
                return null;
        }
    }
}

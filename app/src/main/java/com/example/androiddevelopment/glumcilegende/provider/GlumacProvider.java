package com.example.androiddevelopment.glumcilegende.provider;

import com.example.androiddevelopment.glumcilegende.model.Glumac;
import com.example.androiddevelopment.glumcilegende.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BBLOJB on 20.11.2017..
 */

public class GlumacProvider {

    public static List<Glumac> getGlumci() {

        Film partizanski = new Film(0, "Partizanski");
        Film mangupski = new Film(1, "Mangupski");
        Film komedija = new Film(2, "Komedija" );

        List<Glumac> glumci = new ArrayList<>();
        glumci.add(new Glumac(0, "velimir.jpg", "Velimir Bata Živojinović", "Legenda jugoslovenskog i srpskog glumišta, uvek partizan...", 5.0f, partizanski));
        glumci.add(new Glumac(1, "dragan.jpg", "Dragan Gaga Nikolić", "Večni mangup jugoslovesnkog i srpskog glumišta, čime postaje legenda", 5.0f, mangupski));
        glumci.add(new Glumac(2, "zoran.jpg", "Zoran Radmilović", "Zauvek Radovan III, retko duhovit i dosetljiv, legenda među legendama", 5.0f, komedija));
        return glumci;
    }

    public static Glumac getGlumacById(int id){

        Film partizanski = new Film(0, "Partizanski");
        Film mangupski = new Film(1, "Mangupski");
        Film komedija = new Film(2, "Komedija" );

        switch (id){
            case 0:
                return new Glumac(0, "velimir.jpg", "Velimir Bata Živojinović", "Legenda jugoslovenskog i srpskog glumišta, uvek partizan...", 5.0f, partizanski);
            case 1:
                return new Glumac(1, "dragan.jpg", "Dragan Gaga Nikolić", "Večni mangup jugoslovesnkog i srpskog glumišta, čime postaje legenda", 5.0f, mangupski);
            case 2:
                return new Glumac(2, "zoran.jpg", "Zoran Radmilović", "Zauvek Radovan III, retko duhovit i dosetljiv, legenda među legendama", 5.0f, komedija);
                default:
                    return null;
        }
    }
}

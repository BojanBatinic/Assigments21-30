package com.example.androiddevelopment.glumcilegende.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BBLOJB on 20.11.2017..
 */

public class Film {

    private int id;
    private String name;
    private List<Glumac> glumci;

    public Film() {
        glumci = new ArrayList<>();
    }

    public Film(int id, String name) {
        this.id = id;
        this.name = name;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Glumac> getGlumci() {
        return glumci;
    }

    public void setGlumci(List<Glumac> glumci) {
        this.glumci = glumci;
    }
}

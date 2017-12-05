package com.example.androiddevelopment.glumcilegende.model;

/**
 * Created by BBLOJB on 20.11.2017..
 */

public class Glumac {

    private int id;
    private String image;
    private String name;
    private String biografija;
    private float rating;
    private Film film;

    public Glumac() {
    }

    public Glumac(int id, String image, String name, String biografija, float rating, Film film) {

        this.id = id;
        this.image = image;
        this.name = name;
        this.biografija = biografija;
        this.rating = rating;
        this.film = film;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiografija() {
        return biografija;
    }

    public void setBiografija(String biografija) {
        this.biografija = biografija;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public String toString() {
        return name;
    }
}

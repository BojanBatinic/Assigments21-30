package com.example.androiddevelopment.glumcilegende.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by BBLOJB on 8.12.2017..
 */
@DatabaseTable(tableName = Glumac.TABLE_NAME_USERS)
public class Glumac {

    public static final String TABLE_NAME_USERS = "glumci";

    public static final String FILED_NAME_ID = "id";
    public static final String FILED_NAME_NAME = "name";
    public static final String FILED_NAME_BIOGRAFIJA = "biografija";
    public static final String FIELD_NAME_RATING = "rating";
    public static final String FIELD_NAME_IMAGE = "image";
    public static final String FIELD_NAME_FILM = "film";

    @DatabaseField(columnName = FILED_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FILED_NAME_NAME)
    private String mName;

    @DatabaseField(columnName = FILED_NAME_BIOGRAFIJA)
    private String biografija;

    @DatabaseField(columnName = FIELD_NAME_RATING)
    private float rating;

    @DatabaseField(columnName = FIELD_NAME_IMAGE)
    private String image;

    @DatabaseField(columnName = FIELD_NAME_FILM, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Film film;

    //ORMLite zahteva prazan konstuktur u klasama koje opisuju tabele u bazi!
    public Glumac() {
    }

    //getter and setters


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    @Override
    public String toString() {
        return mName;
    }
}

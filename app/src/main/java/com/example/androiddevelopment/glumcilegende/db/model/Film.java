package com.example.androiddevelopment.glumcilegende.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by BBLOJB on 8.12.2017..
 */
@DatabaseTable(tableName = Film.TABLE_NAME_USERS)
public class Film {

    public static final String TABLE_NAME_USERS = "filmovi";
    public static final String FILED_NAME_ID = "id";
    public static final String FILED_NAME_NAME = "name";
    public static final String FILED_NAME_IMAGE = "image";

    @DatabaseField(columnName = FILED_NAME_ID, generatedId = true)
    private int id;

    @DatabaseField(columnName = FILED_NAME_NAME)
    private String name;

    @DatabaseField(columnName = FILED_NAME_IMAGE)
    private String image;

    //ORMLite zahteva prazan konstuktur u klasama koje opisuju tabele u bazi!
    public Film() {
    }

    public Film(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return name;
    }
}

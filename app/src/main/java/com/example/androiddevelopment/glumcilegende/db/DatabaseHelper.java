package com.example.androiddevelopment.glumcilegende.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.androiddevelopment.glumcilegende.db.model.Film;
import com.example.androiddevelopment.glumcilegende.db.model.Glumac;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.DataOutput;
import java.sql.SQLException;

/**
 * Created by BBLOJB on 8.12.2017..
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    //Daje se ime bazi
    private static final String DATABASE_NAME = "ormlite.db";
    //Daje se i pocetna verzija baze, krece se od verzije 1
    private static final int DATABASE_VERSION = 1;

    private Dao<Glumac, Integer> mGlumacDao = null;
    private Dao<Film, Integer> mFilmDao = null;

    //dodajemo konstruktor zbog pravilne inicijalizacije biblioteke
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke
    //prilikom kreiranja moramo pozvati TableUtils.createTable za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Film.class);
            TableUtils.createTable(connectionSource, Glumac.class);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    //kada zelimo da izmenomo tabele, moramo pozvati TableUtils.dropTable za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, Film.class, true);
            TableUtils.dropTable(connectionSource, Glumac.class, true);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    //jedan Dao objekat sa kojim komuniciramo. Ukoliko imamo vise tabela
    //potrebno je napraviti Dao objekat za svaku tabelu

    public Dao<Glumac, Integer> getGlumacDao() throws SQLException {
        if (mGlumacDao == null){
            mGlumacDao = getDao(Glumac.class);
        }
        return mGlumacDao;
    }

    public Dao<Film, Integer> getFilmDao() throws SQLException {
        if (mFilmDao == null){
            mFilmDao = getDao(Film.class);
        }
        return mFilmDao;
    }
    //obavezno prilikom zatvaranja rada sa bazom osloboditi resurse
    @Override
    public void close(){
        mGlumacDao = null;
        mFilmDao = null;

        super.close();
    }
}

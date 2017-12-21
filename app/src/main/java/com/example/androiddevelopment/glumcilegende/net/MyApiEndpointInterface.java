package com.example.androiddevelopment.glumcilegende.net;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import com.example.androiddevelopment.glumcilegende.net.model.Event;

/**
 * Created by BBLOJB on 21.12.2017..
 */

/**
 * Klasa koja opisuje koji tj mapira putanju servisa
 * opisuje koji metod koristimo ali i sta ocekujemo kao rezultat
 *
 * */
public interface MyApiEndpointInterface {
    @GET("artists/{artist}/events")
    Call<List<Event>> getArtistByName(@Path("artist") String artist, @QueryMap Map<String, String> options);
}

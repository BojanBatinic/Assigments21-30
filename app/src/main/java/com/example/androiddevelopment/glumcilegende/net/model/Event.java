package com.example.androiddevelopment.glumcilegende.net.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by BBLOJB on 21.12.2017..
 */

public class Event {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("artist_id")
    @Expose
    private String artistId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("lineup")
    @Expose
    private List<String> lineup = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<String> getLineup() {
        return lineup;
    }

    public void setLineup(List<String> lineup) {
        this.lineup = lineup;
    }

}


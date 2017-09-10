package com.example.android.mymusic;

import java.io.Serializable;

public class Music implements Serializable {
    private String data;
    private String title;
    private String album;
    private String artist;
    private long duration;
    private double price = 0;

    public Music(String data, String title, String album, String artist, long duration) {
        this.data = data;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
    }

    public Music(String title, String artist, long duration, double price) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.price = price;
    }

    public String getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration() {
        return duration;
    }

    public double getPrice() {
        return price;
    }
}

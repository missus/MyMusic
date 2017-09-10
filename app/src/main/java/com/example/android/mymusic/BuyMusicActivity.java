package com.example.android.mymusic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class BuyMusicActivity extends AppCompatActivity {

    public ArrayList<Music> musicList;
    private MusicAdapter musicAdapter;
    private static int BUY_TYPE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_music_activity);

        musicList = new ArrayList<Music>();
        musicList.add(new Music("Title 0", "Artist 0", 120000, 3.21));
        musicList.add(new Music("Title 1", "Artist 1", 1600000, 1.4));
        musicList.add(new Music("Title 2", "Artist 2", 220000, 0.99));

        ListView listView = (ListView) findViewById(R.id.list_view_buy_music);
        musicAdapter = new MusicAdapter(this, musicList, BUY_TYPE);
        listView.setAdapter(musicAdapter);
    }
}

package com.example.android.mymusic;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {

    List<Music> musicList = Collections.emptyList();
    Activity context;
    int type;
    private static int BUY_TYPE = 0;

    public MusicAdapter(Activity context, ArrayList<Music> music, int type) {
        super(context, 0, music);
        this.musicList = music;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            if (type != BUY_TYPE) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_my, parent, false);
            } else {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_buy, parent, false);
            }
        }

        Music currentMusic = getItem(position);

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentMusic.getTitle());

        TextView artistTextView = (TextView) listItemView.findViewById(R.id.artist);
        artistTextView.setText(currentMusic.getArtist());

        TextView durationTextView = (TextView) listItemView.findViewById(R.id.duration);
        long duration = currentMusic.getDuration();
        durationTextView.setText(Utilities.milliSecondsToTimer(duration));

        if (type == BUY_TYPE) {
            double price = currentMusic.getPrice();
            TextView priceTextView = (TextView) listItemView.findViewById(R.id.price);
            priceTextView.setText(context.getString(R.string.price_text, price));
        }
        return listItemView;
    }
}
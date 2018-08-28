package com.example.android.mymusic;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter extends ArrayAdapter<Music> {

    private List<Music> mMusicList;
    private Activity mContext;
    private int mType;
    private static int BUY_TYPE = 0;

    public MusicAdapter(Activity context, ArrayList<Music> music, int type) {
        super(context, 0, music);
        mMusicList = music;
        mContext = context;
        mType = type;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            if (mType != BUY_TYPE) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_my, parent, false);
            } else {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_buy, parent, false);
            }
        }

        Music currentMusic = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.title);
        titleTextView.setText(currentMusic.getTitle());

        TextView artistTextView = listItemView.findViewById(R.id.artist);
        artistTextView.setText(currentMusic.getArtist());

        TextView durationTextView = listItemView.findViewById(R.id.duration);
        long duration = currentMusic.getDuration();
        durationTextView.setText(Utilities.milliSecondsToTimer(duration));

        if (mType == BUY_TYPE) {
            double price = currentMusic.getPrice();
            TextView priceTextView = listItemView.findViewById(R.id.price);
            priceTextView.setText(mContext.getString(R.string.price_text, price));
        }
        return listItemView;
    }
}
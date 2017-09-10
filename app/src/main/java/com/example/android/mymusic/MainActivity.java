package com.example.android.mymusic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buyMusicButton;
    private Button myMusicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buyMusicButton = (Button) findViewById(R.id.buy_music);
        myMusicButton = (Button) findViewById(R.id.my_music);
        buyMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BuyMusicActivity.class);
                startActivity(intent);
            }
        });
        myMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyMusicActivity.class);
                startActivity(intent);
            }
        });
    }

}

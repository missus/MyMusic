package com.example.android.mymusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class PlayMusicActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private SeekBar musicProgressBar;
    private TextView titleView;
    private TextView currentDurationView;
    private TextView totalDurationView;
    private TextView artistView;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Utilities utilities;
    private int seekForwardTime = 5000;
    private int seekBackwardTime = 5000;
    private int currentMusicIndex = 0;
    private ArrayList<Music> musicList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_music_activity);
        Intent intent = getIntent();
        musicList = (ArrayList<Music>) intent.getSerializableExtra("list");
        currentMusicIndex = intent.getIntExtra("position", 0);

        btnPlay = findViewById(R.id.play);
        btnForward = findViewById(R.id.forward);
        btnBackward = findViewById(R.id.backward);
        btnNext = findViewById(R.id.next);
        btnPrevious = findViewById(R.id.previous);
        musicProgressBar = findViewById(R.id.music_progress);
        titleView = findViewById(R.id.title);
        artistView = findViewById(R.id.artist);
        currentDurationView = findViewById(R.id.current_duration);
        totalDurationView = findViewById(R.id.total_duration);

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        utilities = new Utilities();
        musicProgressBar.setOnSeekBarChangeListener(this);
        mediaPlayer.setOnCompletionListener(this);
        playMusic(currentMusicIndex);

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        btnPlay.setImageResource(R.drawable.play);
                        btnPlay.setContentDescription(getString(R.string.play));
                    }
                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        btnPlay.setContentDescription(getString(R.string.pause));
                    }
                }
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if (currentPosition + seekForwardTime <= mediaPlayer.getDuration()) {
                    mediaPlayer.seekTo(currentPosition + seekForwardTime);
                } else {
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int currentPosition = mediaPlayer.getCurrentPosition();
                if (currentPosition - seekBackwardTime >= 0) {
                    mediaPlayer.seekTo(currentPosition - seekBackwardTime);
                } else {
                    mediaPlayer.seekTo(0);
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (PlayMusicActivity.this.currentMusicIndex < (PlayMusicActivity.this.musicList.size() - 1)) {
                    playMusic(PlayMusicActivity.this.currentMusicIndex + 1);
                    PlayMusicActivity.this.currentMusicIndex = PlayMusicActivity.this.currentMusicIndex + 1;
                } else {
                    playMusic(0);
                    PlayMusicActivity.this.currentMusicIndex = 0;
                }

            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (PlayMusicActivity.this.currentMusicIndex > 0) {
                    playMusic(PlayMusicActivity.this.currentMusicIndex - 1);
                    PlayMusicActivity.this.currentMusicIndex = PlayMusicActivity.this.currentMusicIndex - 1;
                } else {
                    playMusic(PlayMusicActivity.this.musicList.size() - 1);
                    PlayMusicActivity.this.currentMusicIndex = PlayMusicActivity.this.musicList.size() - 1;
                }
            }
        });

    }

    public void playMusic(int musicIndex) {
        try {
            Music currentMusic = musicList.get(musicIndex);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(currentMusic.getData());
            mediaPlayer.prepare();
            mediaPlayer.start();
            String title = currentMusic.getTitle();
            titleView.setText(title);
            String artist = currentMusic.getArtist();
            artistView.setText(artist);

            btnPlay.setImageResource(R.drawable.pause);

            musicProgressBar.setProgress(0);
            musicProgressBar.setMax(100);
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateProgressBar() {
        handler.postDelayed(updateTimeTask, 100);
    }

    private Runnable updateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            totalDurationView.setText(utilities.milliSecondsToTimer(totalDuration));
            currentDurationView.setText(utilities.milliSecondsToTimer(currentDuration));
            int progress = utilities.getProgressPercentage(currentDuration, totalDuration);
            musicProgressBar.setProgress(progress);
            handler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateTimeTask);
        int totalDuration = mediaPlayer.getDuration();
        int currentPosition = utilities.progressToTimer(seekBar.getProgress(), totalDuration);
        mediaPlayer.seekTo(currentPosition);
        updateProgressBar();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        if (currentMusicIndex < (musicList.size() - 1)) {
            playMusic(currentMusicIndex + 1);
            currentMusicIndex = currentMusicIndex + 1;
        } else {
            playMusic(0);
            currentMusicIndex = 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
    }
}
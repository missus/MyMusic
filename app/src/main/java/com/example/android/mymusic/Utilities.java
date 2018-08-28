package com.example.android.mymusic;


import java.util.concurrent.TimeUnit;

public class Utilities {

    public static String milliSecondsToTimer(long duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }

    public int getProgressPercentage(long currentDuration, long totalDuration) {
        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        Double percentage = (((double) currentSeconds) / totalSeconds) * 100;
        return percentage.intValue();
    }

    public int progressToTimer(int progress, int totalDuration) {
        totalDuration = totalDuration / 1000;
        int currentDuration = (int) ((((double) progress) / 100) * totalDuration);
        return currentDuration * 1000;
    }
}

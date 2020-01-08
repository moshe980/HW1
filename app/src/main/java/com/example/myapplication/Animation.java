package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = -1;
        startTime = System.currentTimeMillis();
    }
    public void setDelay(long d){delay = d;}


    public void setFrame(int i){currentFrame= i;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame = -1;
            playedOnce = true;
        }
    }
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public int getFrame(){return currentFrame;}
    public boolean playedOnce(){return playedOnce;}

}

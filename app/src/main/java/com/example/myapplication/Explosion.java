package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;

public class Explosion implements GameObject {
    private int x;
    private int y;
    private int width;
    private Bitmap spritesheet;
    private Bitmap image;

    public Explosion(Bitmap res, int w, int numFrames) {

        this.width = w;


        spritesheet = res;


        image = Bitmap.createBitmap(spritesheet, 0, 0, width, spritesheet.getHeight());


    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, x, y, null);


    }

    @Override
    public void update() {

    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

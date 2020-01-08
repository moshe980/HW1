package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Background implements GameObject {
    private Bitmap image;
    private int x, y;
    private int dy;

    public Background(Bitmap res) {
        image = res;
        y = -Constants.SCREEN_HEIGHT;
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

        if (y > 0) {
            canvas.drawBitmap(image, x, -Constants.SCREEN_HEIGHT, null);
        }
    }

    @Override
    public void update() {
        y += dy;
        if (y > 0) {
            y = -Constants.SCREEN_HEIGHT;
        }
    }

    public void setVactor(int dy) {
        this.dy = dy;
    }
}

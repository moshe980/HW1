package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

class Obstacle implements GameObject {
    private int counter1, counter2;
    private Rect rectangles[];
    public static final int RECT_HEIGHT = 150;
    public static final int GAP = 15;
    Bitmap image;


    public Obstacle(Bitmap image, int positionX, int positionY) {
        rectangles = new Rect[5];
        counter1 = new Random().nextInt(5);
        counter2 = new Random().nextInt(5);
        this.image = image;

        rectangles[0] = new Rect(GAP, positionY, positionX, positionY + RECT_HEIGHT);
        rectangles[1] = new Rect(positionX + GAP, positionY, positionX * 2, positionY + RECT_HEIGHT);
        rectangles[2] = new Rect(positionX * 2 + GAP, positionY, positionX * 3 - GAP, positionY + RECT_HEIGHT);
        rectangles[3] = new Rect(positionX * 3 + GAP, positionY, positionX * 4, positionY + RECT_HEIGHT);
        rectangles[4] = new Rect(positionX * 4 + GAP, positionY, positionX * 5 - GAP, positionY + RECT_HEIGHT);

    }

    public Rect getRectangle() {
        return rectangles[0];
    }

    public void incrementY(float y) {
        for (Rect rect : rectangles) {
            rect.top += y;
            rect.bottom += y;
        }


    }

    @Override
    public void draw(Canvas canvas) {
        rectangles[counter1].setEmpty();
        rectangles[counter2].setEmpty();

        for (Rect rect : rectangles) {
            canvas.drawBitmap(image, null, rect, null);


        }

    }

    @Override
    public void update() {
    }

    public boolean playerCollide(Player player) {
        for (Rect rect : rectangles) {
            if (rect.intersect(player.getRectangle())) {
                rect.setEmpty();
                return true;
            }
        }

        return false;


    }

}

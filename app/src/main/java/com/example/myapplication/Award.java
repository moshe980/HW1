package com.example.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class Award implements GameObject {
    private int counter;
    private Rect rectangles[];
    public static final int RECT_HEIGHT = 130;
    public static final int GAP = 20;
    Bitmap image;



    public Award(Bitmap image,int positionX, int positionY) {
        rectangles = new Rect[5];
        counter = new Random().nextInt(5);

        this.image=image;


        rectangles[0] = new Rect(GAP, positionY, positionX, positionY + RECT_HEIGHT);
        rectangles[1] = new Rect(positionX + GAP, positionY, positionX * 2, positionY + RECT_HEIGHT);
        rectangles[2] = new Rect(positionX * 2 + GAP, positionY, positionX * 3 - GAP, positionY + RECT_HEIGHT);
        rectangles[3] = new Rect(positionX*3 + GAP, positionY, positionX * 4, positionY + RECT_HEIGHT);
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

        for (int i = 0; i < rectangles.length; i++) {
            if (i != counter) {
                rectangles[i].setEmpty();
            }else {
                canvas.drawBitmap(image,null,rectangles[i],null);

            }
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

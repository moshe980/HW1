package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

class Obstacle implements GameObject {
    private int counter;
    private Rect rectangles[];
    public static final int RECT_HEIGHT = 150;
    public static final int GAP = 15;
    private Random random = new Random();


    public Obstacle(int positionX, int positionY) {
        rectangles = new Rect[3];
        counter = random.nextInt(3);
        rectangles[0] = new Rect(GAP, positionY, positionX, positionY + RECT_HEIGHT);
        rectangles[1] = new Rect(positionX + GAP, positionY, positionX * 2, positionY + RECT_HEIGHT);
        rectangles[2] = new Rect(positionX * 2 + GAP, positionY, positionX * 3 - GAP, positionY + RECT_HEIGHT);

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
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        rectangles[counter].setEmpty();
        for (Rect rect : rectangles) {
            canvas.drawRect(rect, paint);
        }

    }

    @Override
    public void update() {

    }
//15
    public boolean playerCollide(Player player) {
        for(Rect rect:rectangles){
           if( rect.contains((int)player.getPositionX(),(int)(player.getPositionY()-player.getRadius()*2))) {
               rect.setEmpty();
               return true;
           }
        }

        return false;


    }

}

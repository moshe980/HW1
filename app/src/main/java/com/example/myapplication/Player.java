package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

class Player implements GameObject {
    private Rect rectangle;
    Bitmap image;
    private int state=0;

    public Player(Bitmap image,Rect rectangle){
        this.image=image;
        this.rectangle=rectangle;;


    }


    public Rect getRectangle(){
        return rectangle;
    }

    public void setImage(Bitmap image){
        this.image=image;
    }


    public void draw(Canvas canvas) {

        canvas.drawBitmap(image,null,rectangle,null);
    }

    @Override
    public void update() {
    }


    public void update(Point point){
        float oldLeft=rectangle.left;

        rectangle.set(point.x-rectangle.width()/2,Constants.SCREEN_HEIGHT / 20 * 18,point.x+rectangle.width()/2,
                Constants.SCREEN_HEIGHT / 20 * 18+150);
        state=0;
        if(rectangle.left-oldLeft>0)
             state=1;
        if (rectangle.left-oldLeft<0)
             state=2;
        if(rectangle.left-oldLeft==365)
            state=0;
    }
    public int getState(){
        return state;
    }
}

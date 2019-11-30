package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Player implements GameObject {
    private double positionX;
    private double positionY;
    private double radius;

    public Player(double positionX,double positionY,double radius){
        this.positionX=positionX;
        this.positionY=positionY;
        this.radius=radius;

       ;

    }
    public double getLeft(){
        return positionX-radius;
    }
    public double getright(){
        return positionX+radius;
    }

    public double getTop(){
        return positionY+radius;
    }

    public double getBottom(){
        return positionY-radius;
    }


    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle((float)positionX,(float)positionY,(float)radius,paint);

    }

    @Override
    public void update() {

    }

    public void setPosision(double positionX, double positionY) {
        this.positionX=positionX;
        this.positionY=positionY;
    }
    public void setPosisionX(double positionX) {
        this.positionX=positionX;
    }

    public double getPositionX(){
        return positionX;
    }

    public double getPositionY(){
        return positionY;
    }
}

package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Player implements GameObject {
    private double positionX;
    private double positionY;
    private double radius;

    private Animation idle;
    private  Animation flyRight;
    private  Animation flyLeft;

    public Player(double positionX,double positionY,double radius){
        this.positionX=positionX;
        this.positionY=positionY;
        this.radius=radius;

        BitmapFactory bf= new BitmapFactory();
        Bitmap idle_Image=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player);
        Bitmap flyLeft_image=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_left);
        Bitmap flyRight_image=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.playe_rlight);

        idle=new Animation(new Bitmap[]{idle_Image},2);
        flyRight=new Animation(new Bitmap[]{flyRight_image},0.5f);

        flyLeft=new Animation(new Bitmap[]{flyLeft_image},0.5f);


    }

    public double getRadius(){
        return radius;
    }



    public void draw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle((float)positionX,(float)positionY,(float)radius,paint);

    }

    @Override
    public void update() {
    float
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

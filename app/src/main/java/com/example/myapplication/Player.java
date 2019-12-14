package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

class Player implements GameObject {
    private Rect rectangle;

    private Animation idle;
    private  Animation flyRight;
    private  Animation flyLeft;
    private AnimationManager animationManager;

    public Player(Rect rectangle){
        this.rectangle=rectangle;

        BitmapFactory bf= new BitmapFactory();
        Bitmap idle_Image=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player);
        Bitmap flyLeft_image=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_left);
        Bitmap flyRight_image=bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.playe_rlight);

        idle=new Animation(new Bitmap[]{idle_Image},0);
        flyRight=new Animation(new Bitmap[]{flyRight_image},0);

       flyLeft=new Animation(new Bitmap[]{flyLeft_image},0);

        animationManager=new AnimationManager(new Animation[]{idle,flyRight,flyLeft});


    }

    public Rect getRectangle(){
        return rectangle;
    }



    public void draw(Canvas canvas) {
       animationManager.draw(canvas,rectangle);

    }

    @Override
    public void update() {
        animationManager.update();
    }


    public void update(Point point){
        float oldLeft=rectangle.left;

        rectangle.set(point.x-rectangle.width()/2,point.y-rectangle.height()/2,point.x+rectangle.width()/2,
                point.y+rectangle.height());

        int state=0;
        if(rectangle.left-oldLeft>5)
            state=1;
        else if (rectangle.left-oldLeft<-5)
            state=2;

        animationManager.playAnime(state);
        animationManager.update();
}
}

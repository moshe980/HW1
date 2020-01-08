package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;

public class Explosion implements GameObject {
    private int x;
    private int y;
    private int width;
    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private Bitmap[] image;

    public Explosion(Bitmap res, int w, int numFrames)
    {

        this.width = w;

        image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i = 0; i<image.length; i++)
        {

            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,spritesheet.getHeight());
        }
        animation.setFrames(image);
        animation.setDelay(10);



    }
    @Override
    public void draw(Canvas canvas)
    {

            canvas.drawBitmap(image[0],x,y,null);




    }
    @Override
    public void update()
    {

    }
    public void setPosition(int x,int y){
        this.x = x;
        this.y = y;
    }

}

package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosion implements GameObject {
    private int rowIndex = 0 ;
    private int colIndex = -1 ;

    private boolean finish= false;
    private GameSurface gameSurface;


    public Explosion(Bitmap bitmap,int x,int y,int width,int height,int numFrames){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        image =new Bitmap[numFrames];
        this.bitmap=bitmap;

        for (int i=0;i<image.length;i++)
        {
            if(i%5==0&&i>0)
                row++;
            image[i]=Bitmap.createBitmap(bitmap,(i-(5*row))*width,height,width,height);
        }
        animation=new Animation(image,0.5f);

        animation.setFrames(image);

        animationManager=new AnimationManager(new Animation[]{animation});


    }


    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas,new Rect(100,100,200,200));
    }

    @Override
    public void update() {
        animationManager.update();

    }
    public int getHeight(){
        return height;
    }
}

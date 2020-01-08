package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class AwardsManager implements GameObject {
    private ArrayList<Award> awards;
    public static final int OBSTACLES_GAP=1000;
    private long startTime;
    private long initTime;
    private Bitmap image;


    public AwardsManager(Bitmap image) {
        this.image=image;

        startTime = initTime = System.currentTimeMillis();
        awards = new ArrayList<>();
        populateObstacles();
    }

    private void populateObstacles() {

        int currY = -5 * 100000;//Game time

        while (currY < 0){
            awards.add(new Award(image,Constants.SCREEN_WIDTH/7, currY));
            //The gap between each line
            currY += Award.RECT_HEIGHT + OBSTACLES_GAP+100;
        }

    }

    @Override


    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);//acceleration
        startTime = System.currentTimeMillis();
        float speed = 3;

        for (int i = 0; i < awards.size() - 1; i++) {
            awards.get(i).incrementY(speed );
        }

        if (awards.get(awards.size() - 1).getRectangle().top > Constants.SCREEN_HEIGHT) {

            awards.add(0, new Award(image,350, 0));
            awards.remove(awards.size() - 1);

        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Award ob : awards) {
            ob.draw(canvas);
        }
    }

    public boolean playerCollide(Player player) {
        for (int i=0;i<awards.size()-1;i++) {

            if (awards.get(i).playerCollide(player)) {
                return true;
            }

        }

        return false;
    }

}

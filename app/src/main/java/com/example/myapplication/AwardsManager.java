package com.example.myapplication;

import android.graphics.Canvas;

import java.util.ArrayList;

public class AwardsManager implements GameObject {
    private ArrayList<Awards> awards;
    public static final int OBSTACLES_GAP=1000;
    private long startTime;
    private long initTime;

    public AwardsManager() {
        startTime = initTime = System.currentTimeMillis();
        awards = new ArrayList<>();
        populateObstacles();
    }

    private void populateObstacles() {
        //Game time
        int currY = -5 * 100000;

        while (currY < 0){
            awards.add(new Awards(Constants.SCREEN_WIDTH/5, currY));
            //The gap between each line
            currY += Obstacle.RECT_HEIGHT + OBSTACLES_GAP+100;
        }

    }

    @Override


    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);//acceleration
        startTime = System.currentTimeMillis();
        float speed = 5;

        for (int i = 0; i < awards.size() - 1; i++) {
            awards.get(i).incrementY(speed );
        }

        if (awards.get(awards.size() - 1).getRectangle().top > Constants.SCREEN_HEIGHT) {

            awards.add(0, new Awards(350, 0));
            awards.remove(awards.size() - 1);

        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Awards ob : awards) {
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

package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;

import java.util.ArrayList;

public class ObstacleManager implements GameObject {
    private ArrayList<Obstacle> obstacles;
    public static final int OBSTACLES_GAP=1000;
    private long startTime;
    private long initTime;
    private float speed;

    public ObstacleManager() {
        startTime = initTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        populateObstacles();
    }

    private void populateObstacles() {
        //Game time
        int currY = -5 * 100000;

        while (currY < 0){
        obstacles.add(new Obstacle(Constants.SCREEN_WIDTH/3, currY));
        //The gap between each line
        currY += Obstacle.RECT_HEIGHT + OBSTACLES_GAP;
    }

    }

    @Override


    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);//acceleration
        startTime = System.currentTimeMillis();
        float speed = (float) (Math.sqrt(1 + (startTime - initTime) / 1000.0)) * Constants.SCREEN_HEIGHT / (10000.0f);

        for (int i = 0; i < obstacles.size() - 1; i++) {
            obstacles.get(i).incrementY(speed * elapsedTime);
        }

        if (obstacles.get(obstacles.size() - 1).getRectangle().top > Constants.SCREEN_HEIGHT) {
            int xStart = Constants.SCREEN_WIDTH / 3;

            obstacles.add(0, new Obstacle(350, 0));
            obstacles.remove(obstacles.size() - 1);

        }
    }

    @Override
    public void draw(Canvas canvas) {
        for (Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
    }

    public boolean playerCollide(Player player) {
        for (int i=0;i<obstacles.size()-1;i++) {

            if (obstacles.get(i).playerCollide(player)) {
                return true;
            }

        }

        return false;
    }
}

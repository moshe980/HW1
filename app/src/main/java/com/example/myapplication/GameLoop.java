package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameLoop extends Thread {
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    public static   boolean flag=false;
    private Context context;


    public GameLoop(Game game, SurfaceHolder surfaceHolder, Context context) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        this.context=context;


    }
    public void setRunning(Boolean isRunning){
        this.isRunning=isRunning;
    }


    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();
        int frameCount=0;

        Canvas canvas = null;
        while (isRunning) {
            //update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    game.draw(canvas);
                    if(game.getGameOver()){
                        game.surfaceDestroyed(surfaceHolder);
                        Intent intent=new Intent(context,Registration.class);
                        context.startActivity(intent);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            frameCount++;
            if(frameCount==30){
                frameCount=0;
            }
        }
    }
    public static boolean getFlag(){
        return  flag;
    }
}

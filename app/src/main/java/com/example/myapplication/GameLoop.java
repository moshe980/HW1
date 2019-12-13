package com.example.myapplication;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class GameLoop extends Thread {
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;


    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;

    }


    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();


        Canvas canvas = null;
        while (isRunning) {
            //update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    game.draw(canvas);
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

        }
    }
}

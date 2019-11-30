package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AsyncPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private  Player player;
    private ObstacleManager obstacleManager;
    private GameLoop gameLoop;
    private  boolean gameOver = false;
    private  double lives = 3;
    private  double score=0;


    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game objects
        player = new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT/2+800, 50);
        obstacleManager = new ObstacleManager();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(gameOver){
                    gameOver=false;
                    reset();

                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver)
                    player.setPosisionX(event.getX());
                break;
            case MotionEvent.ACTION_UP:

        }
        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        obstacleManager.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Score: "+(int)score, 50, 50 + paint.descent() - paint.ascent(), paint);
        canvas.drawText("Lives: "+(int)lives, 600, 50 + paint.descent() - paint.ascent(), paint);


        if (gameOver) {
            drawGameOver(canvas);
        }

    }


    public void update() {
        if (!gameOver) {
            player.update();
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)) {
                lives=lives-0.06;
            }else {
                score=score+0.03;
            }
            if ((int)lives ==0) {
                gameOver = true;

            }


        }
    }

    public void drawGameOver(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(150);
        canvas.drawText("GAME OVER", 110, 500, paint);

    }
    public void reset() {
        lives=3;
        score=0;
        obstacleManager = new ObstacleManager();
        player = new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT/2+800, 50);


    }

}

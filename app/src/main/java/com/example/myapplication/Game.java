package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AsyncPlayer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private Player player;
    private ObstacleManager obstacleManager;
    private GameLoop gameLoop;
    private boolean gameOver = false;
    private int lives = 3;
    private double score = 0;
    private double record = 0;
    private boolean isBreak = false;


    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game  objects
        player = new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT/20*19, Constants.SCREEN_WIDTH / 20);
        obstacleManager = new ObstacleManager();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameOver) {
                    gameOver = false;
                    reset();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver) {
                    if ((int) event.getX() <= 0) {
                        player.setPosisionX(50);
                    }
                    if (event.getX() >= Constants.SCREEN_WIDTH) {
                        player.setPosisionX(Constants.SCREEN_WIDTH - player.getRadius());
                    }
                    if (event.getX() > 0 && event.getX() < Constants.SCREEN_WIDTH) {
                        player.setPosisionX((int) event.getX());
                    }
                }
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
        paint.setTextSize(Constants.SCREEN_WIDTH / 10);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Lives: " + lives, Constants.SCREEN_WIDTH / 2 + paint.descent() - paint.ascent(), 50 + paint.descent() - paint.ascent(), paint);
        canvas.drawText("Score: " + (int) score, 0, 50 + paint.descent() - paint.ascent(), paint);
        if (isBreak) {
            record = score;
            paint.setColor(Color.YELLOW);
            canvas.drawText("Record: " + (int) record, 0, Constants.SCREEN_HEIGHT / 10 + paint.descent() - paint.ascent(), paint);
        } else {
            paint.setColor(Color.MAGENTA);
            canvas.drawText("Record: " + (int) record, 0, Constants.SCREEN_HEIGHT / 10 + paint.descent() - paint.ascent(), paint);
        }


        if (gameOver) {
            drawGameOver(canvas);
        }

    }


    public void update() {
        if (!gameOver) {
            player.update();
            obstacleManager.update();
            //SystemClock.sleep(50);
            if (obstacleManager.playerCollide(player)) {
                lives--;
            } else {
                score = score + 0.05;
                if (score - 0.5 > record) {
                    record = score;
                    isBreak = true;
                }
            }
            if ((int) lives == 0) {
                gameOver = true;

            }


        }
    }

    public void drawGameOver(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(Constants.SCREEN_WIDTH / 10);
        canvas.drawText("GAME OVER", Constants.SCREEN_WIDTH / 4, Constants.SCREEN_HEIGHT / 2, paint);

    }

    public void reset() {
        lives = 3;
        score = 0;
        isBreak = false;
        obstacleManager = new ObstacleManager();
        player = new Player(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT/20*19, Constants.SCREEN_WIDTH / 20);


    }

}

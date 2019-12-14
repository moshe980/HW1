package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private Player player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private GameLoop gameLoop;
    private boolean gameOver = false;
    private int lives = 1000;
    private double score = 0;
    private double record = 0;
    private boolean isBreak = false;
    private final int POSITION_Y = Constants.SCREEN_HEIGHT / 20 * 18;
    private boolean movingPlayer = false;
    private OrientationData orientationData;
    private long frameTime;


    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game  objects
        player = new Player(new Rect(100, 0, 250, 200));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, POSITION_Y);
        obstacleManager = new ObstacleManager();
        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (gameOver) {
                    gameOver = false;
                    reset();
                    orientationData.newGame();
                } else if (player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), POSITION_Y);


                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;


        }
        return true;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Constants.INIT_TIME = System.currentTimeMillis();
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameLoop.setRunning(false);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.star_background), 0, 0, null);
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
            if (frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int) (System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();
            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];

                float xspeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;

                playerPoint.x += Math.abs(xspeed * elapsedTime) > 5 ? xspeed * elapsedTime : 0;
            }
            if (playerPoint.x < 0)
                playerPoint.x = 0;
            else if (playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;


            player.update(playerPoint);
            obstacleManager.update();
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
        playerPoint = new Point(150, 150);


    }

}

package com.example.myapplication;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private Player player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private AwardsManager awardsManager;
    private GameLoop gameLoop;
    private boolean gameOver = false;
    private int lives;
    private static double score;
    private final int POSITION_Y = Constants.SCREEN_HEIGHT / 20 * 18;
    private boolean movingPlayer = false;
    private OrientationData orientationData;
    private long frameTime;
    private Background background;
    private Explosion explosion;
    private boolean isExplode=false;


    public Game(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        Constants.CURRENT_CONTEXT = context;

        gameLoop = new GameLoop(this, surfaceHolder,context);

        score=0;
        lives=3;
        //Initialize game  objects
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.player),new Rect(100, 0, 250, 200));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, POSITION_Y);


        obstacleManager = new ObstacleManager(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid));
        awardsManager=new AwardsManager(BitmapFactory.decodeResource(getResources(),R.drawable.star_gold));

        if(MainActivity.gyroUse==true) {
            orientationData = new OrientationData();
            orientationData.register();
        }
        explosion = new Explosion(BitmapFactory.decodeResource(getResources(),R.drawable.boom),250,  25);

        frameTime = System.currentTimeMillis();
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!MainActivity.gyroUse) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (gameOver) {
                        gameOver = false;

                       // orientationData.newGame();

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
        }
        return true;

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Constants.INIT_TIME = System.currentTimeMillis();
        background=new Background(BitmapFactory.decodeResource(getResources(),R.drawable.star_background));
        background.setVactor(20);//speed
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

        background.draw(canvas);
        player.draw(canvas);
        obstacleManager.draw(canvas);
        awardsManager.draw(canvas);
        if(isExplode) {

            explosion.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(Constants.SCREEN_WIDTH / 10);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Lives: " + lives, Constants.SCREEN_WIDTH / 2 + paint.descent() - paint.ascent(), 50 + paint.descent() - paint.ascent(), paint);
        canvas.drawText("Score: " + (int) score, 0, 50 + paint.descent() - paint.ascent(), paint);


        if (getGameOver()) {
            drawGameOver(canvas);
            if (MainActivity.gyroUse)
            orientationData.pause();
        }

    }


    public void update() {
        if (!gameOver) {
            if (frameTime < Constants.INIT_TIME)
                frameTime = Constants.INIT_TIME;
            int elapsedTime = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            background.update();
            if(MainActivity.gyroUse) {
                if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                    float roll = orientationData.getOrientation()[2] - orientationData.getStartOrientation()[2];
                    float xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f;

                    playerPoint.x += Math.abs(xSpeed * elapsedTime) > 5 ? xSpeed * elapsedTime : 0;

                }
            }

            if(playerPoint.x < 0)
                playerPoint.x = 0;
            else if(playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x = Constants.SCREEN_WIDTH;
            player.update(playerPoint);

            if(player.getState()==1){
                player.setImage(BitmapFactory.decodeResource(getResources(),R.drawable.playe_right,null));
            }else if(player.getState()==2) {
                player.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.player_left, null));
            }else if(player.getState()==0){
                player.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.player, null));
            }

            obstacleManager.update();
            awardsManager.update();


            if (obstacleManager.playerCollide(player)) {
                explosion.setPosition(playerPoint.x-135,Constants.SCREEN_HEIGHT/20*17);
                explosion.update();
                isExplode=true;
                lives--;
            } else {
                score = score + 0.05;
                isExplode=false;
            }
            if(awardsManager.playerCollide(player)){
                score+=100;
            }
            if ( lives == 0) {
                gameOver = true;

            }


        }
    }

    public void drawGameOver(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setTextSize(Constants.SCREEN_WIDTH / 9);
        canvas.drawText("GAME OVER", Constants.SCREEN_WIDTH /5, Constants.SCREEN_HEIGHT/5, paint);

    }

    public boolean getGameOver(){
        return gameOver;
    }

    public static int getScore(){
        return (int)score;
    }

    public void restart(){
        lives=3;
        score=0;
        //Initialize game  objects
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.player),new Rect(100, 0, 250, 200));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, POSITION_Y);


        obstacleManager = new ObstacleManager(BitmapFactory.decodeResource(getResources(),R.drawable.asteroid));
        awardsManager=new AwardsManager(BitmapFactory.decodeResource(getResources(),R.drawable.star_gold));

       setFocusable(true);
    }
}

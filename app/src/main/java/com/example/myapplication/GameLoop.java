package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class GameLoop extends Thread {
    private int FPS = 30;
    private double averageFPS;

    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public static Canvas canvas;
    private List<User> users;
    private boolean isTopTen=false;


    public GameLoop(final Game game, SurfaceHolder surfaceHolder, Context context) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
        this.context = context;

        users = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

    }

    public void setRunning(Boolean isRunning) {
        this.isRunning = isRunning;
    }


    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000 / FPS;


        while (isRunning) {
            startTime = System.nanoTime();
            canvas = null;
            //update and render game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    game.update();
                    game.draw(canvas);
                    if (game.getGameOver()) {
                        game.surfaceDestroyed(surfaceHolder);
                        readUsers(new FirebaseCallback() {
                            @Override
                            public void onCallback(List<User> users) {
                                Collections.sort(users, new Comparator<User>() {
                                    @Override
                                    public int compare(User o1, User o2) {
                                        return Integer.compare(Integer.valueOf(o2.getScore()), Integer.valueOf(o1.getScore()));
                                    }
                                });
                                if (users.size() == 10 && Integer.valueOf(users.get(9).getScore()) < Game.getScore()){
                                    myRef.child(String.valueOf(users.get(9).getId())).removeValue();
                                    isTopTen = true;
                                } else if (users.size() < 10) {
                                    isTopTen = true;
                                }else {
                                    isTopTen=false;
                                }
                                if (isTopTen) {
                                    Intent intent =new Intent(context, Registration.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                    context.startActivity(intent);


                                } else {
                                    Intent intent =new Intent(context, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);
                                }

                            }

                        });

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
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                this.sleep(waitTime);
            } catch (Exception e) {
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void readUsers(final FirebaseCallback firebaseCallback) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    User user = keyNode.getValue(User.class);
                    users.add(user);
                }
                firebaseCallback.onCallback(users);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error in reading");
            }
        });
    }

    private interface FirebaseCallback {
        void onCallback(List<User> list);
    }
}

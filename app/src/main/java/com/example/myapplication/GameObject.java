package com.example.myapplication;

import android.graphics.Canvas;

interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}

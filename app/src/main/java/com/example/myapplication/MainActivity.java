package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageButton play_button,highScore_button;
    private Context context;
    private Switch mSwitch;
    public static boolean gyroUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        context = this;

        //English default:
        Configuration config=new Configuration();
        Locale locale = new Locale("en");

        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;


        String[] permissions={Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        ActivityCompat.requestPermissions(this,permissions,101);
        setContentView(R.layout.activity_main);

        mSwitch=(Switch)findViewById(R.id.mswitch);
        gyroUse=false;
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mSwitch.isChecked()){
                    gyroUse=true;
                }else {
                    gyroUse=false;
                }
            }
        });

        play_button =  findViewById(R.id.btn_play);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new Game(context));


            }
        });
        highScore_button=findViewById(R.id.btn_high_score);
        highScore_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,TopTen.class);
                context.startActivity(intent);
            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("onBackPressed");
        Intent intent =new Intent(context, MainActivity.class);
        context.startActivity(intent);
        finish();

    }


}

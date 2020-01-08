package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Registration extends Activity {
    private TextView textView;
    private EditText editText;
    private ImageButton save_button;
    private DatabaseReference databaseUsers;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //English default:
        Configuration config = new Configuration();
        Locale locale = new Locale("en");

        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                }
            }

        });


        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        save_button = (ImageButton) findViewById(R.id.btn_save);

        getWindow().setLayout((int) (Constants.SCREEN_WIDTH * .8), (int) (Constants.SCREEN_HEIGHT * .6));
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        textView.setText("Your score: " + Game.getScore());
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write to the database
                addUserName();

            }
        });
    }

    private void addUserName() {
        String userName = editText.getText().toString();

        if (!TextUtils.isEmpty(userName)) {
         //   if(currentLocation!=null) {

                User user = new User(userName, String.valueOf(Game.getScore()), String.valueOf(currentLocation.getLatitude() + "," + currentLocation.getLongitude()));
                databaseUsers.child(String.valueOf(user.getId())).setValue(user);

                Toast.makeText(this, "User Added", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                editText.clearComposingText();
                finish();
            }else {
                Toast.makeText(this, "Can't find your location", Toast.LENGTH_LONG).show();

            }
        //} else {
           // Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
     //   }
    }

    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("onBackPressed");
        Intent intent =new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();

    }


}

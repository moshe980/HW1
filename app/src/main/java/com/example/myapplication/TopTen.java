package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class TopTen extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TableLayout tableLayout;
    private TopTen contaxt;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private List<User> users;
    private int counter=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        contaxt=this;
        //English default:
        Configuration config=new Configuration();
        Locale locale = new Locale("en");

        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        tableLayout=(TableLayout)findViewById(R.id.table);
        tableLayout.setStretchAllColumns(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Read from the database
        users=new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        readUsers(new FirebaseCallback() {
            @Override
            public void onCallback(List<User> users) {
                Collections.sort(users, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return Integer.compare(Integer.valueOf(o2.getScore()),Integer.valueOf(o1.getScore()));
                    }
                });
                for(final User user:users){
                    TableRow tableRow=new TableRow(contaxt);
                    //Num
                    TextView c1=new TextView(contaxt);
                    c1.setText(String.valueOf(counter));
                    c1.setGravity(Gravity.CENTER);
                    tableRow.addView(c1);
                    //Name
                    TextView c2=new TextView(contaxt);
                    c2.setText(user.getName());
                    c2.setGravity(Gravity.CENTER);
                    tableRow.addView(c2);
                    //Score
                    TextView c3=new TextView(contaxt);
                    c3.setText(user.getScore());
                    c3.setGravity(Gravity.CENTER);
                    tableRow.addView(c3);
                    //Location
                    TextView c4=new TextView(contaxt);
                    c4.setText(user.getLocation());
                    c4.setGravity(Gravity.CENTER);
                    tableRow.addView(c4);
                    tableRow.setClickable(true);

                    tableRow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double lat,lng;
                            TableRow tablerow = (TableRow) v;
                            TextView sample = (TextView) tablerow.getChildAt(3);
                            String[] result=sample.getText().toString().split(",");
                            lat=Double.valueOf(result[0]);
                            lng=Double.valueOf(result[1]);

                            //move the camera
                            LatLng currentLocation = new LatLng(lat, lng);
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title(user.getName())).showInfoWindow();
                            float zoomLevel = 16.0f; //This goes up to 21
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));

                            mMap.getMaxZoomLevel();



                        }
                    });
                    tableLayout.addView(tableRow);
                    counter++;
                }
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    public void readUsers(final FirebaseCallback firebaseCallback){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String>keys =new ArrayList<>();
                for(DataSnapshot keyNode:dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    User user=keyNode.getValue(User.class);
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
    private interface FirebaseCallback{
        void onCallback(List<User> list);
    }
}

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends Activity {
    private TextView textView;
    private EditText editText;
    private ImageButton save_button;
    private DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textView=(TextView)findViewById(R.id.textView);
        editText=(EditText)findViewById(R.id.editText);
        save_button=(ImageButton)findViewById(R.id.btn_save);

        getWindow().setLayout((int) (Constants.SCREEN_WIDTH*.8), (int) (Constants.SCREEN_HEIGHT*.6));
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        textView.setText("Your score: "+Game.getScore());
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserName();
                // Write a message to the database
                addUserName();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        });
    }
    private void addUserName(){
        String userName=editText.getText().toString();

        if(!TextUtils.isEmpty(userName)){

           User user=new User(userName,"2000","location");

           databaseUsers.child(userName).setValue(user);

           Toast.makeText(this,"User Added",Toast.LENGTH_LONG).show();;
        }else {
            Toast.makeText(this,"You should enter a name",Toast.LENGTH_LONG).show();
        }
    }
}

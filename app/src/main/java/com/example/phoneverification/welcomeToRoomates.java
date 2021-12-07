package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welcomeToRoomates extends AppCompatActivity {

    ProgressDialog dialog;
    Button findRoommates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_to_roomates);
        findRoommates = findViewById(R.id.findRoommates);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);

        findRoommates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                startActivity(new Intent(getApplicationContext(), dashboard.class));
                finish();
            }
        });


    }
}

package com.example.phoneverification;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), profile.class));
                    overridePendingTransition(22, 1);
                    return true;
                case R.id.chat:
                    //startActivity(new Intent(getApplicationContext(), logIn.class));
                    overridePendingTransition(22, 0);
                    return true;
            }
            return false;
        });
    }
}

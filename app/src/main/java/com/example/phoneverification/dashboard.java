package com.example.phoneverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    RecyclerView recview;
    CardView userCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);
        recview = findViewById(R.id.recview);
        userCard = findViewById(R.id.userCard);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new Welcome()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new Welcome()).commit();
                    return true;
                case R.id.profile:
                    // getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new profileFragEmailId()).commit();
                    Intent intent = new Intent(getApplicationContext(), profile.class);
                    intent.putExtra("source", "dashboard");
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.chat:
                    startActivity(new Intent(getApplicationContext(), logIn.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;
        });

    }

}

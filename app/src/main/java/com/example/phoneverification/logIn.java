package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logIn extends AppCompatActivity {

    EditText emailTwo, password;
    Button signInTwo;
    TextView registerTwo;
    FirebaseAuth sAuth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailTwo = findViewById(R.id.uName);
        password = findViewById(R.id.confirmPassword);
        signInTwo = findViewById(R.id.register);
        registerTwo = findViewById(R.id.registerTwo);
        sAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logging in..");
        dialog.setCancelable(false);
        registerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), signUp.class));
            }
        });

        signInTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailo = emailTwo.getText().toString().trim();
                String passwordo = password.getText().toString().trim();

                if (emailo.isEmpty()) {
                    emailTwo.setError("Enter your Email");
                    return;
                } else if (passwordo.isEmpty()) {
                    password.setError("Please enter your password");
                    return;
                } else if (passwordo.length() < 6) {
                    password.setError("Please Enter Atleast 6 characters");
                    return;
                }
                dialog.show();


                sAuth.signInWithEmailAndPassword(emailo, passwordo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), dashboard.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }
}

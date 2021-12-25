package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.Objects;

public class signUp extends AppCompatActivity {

    public static String emailo;
    EditText emailOne;
    EditText passwordOne;
    EditText confirmPassword;
    Button register;
    TextView logIn;
    FirebaseAuth sAuth;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        emailOne = findViewById(R.id.uName);
        passwordOne = findViewById(R.id.passwordOne);
        confirmPassword = findViewById(R.id.confirmPassword);
        register = findViewById(R.id.register);
        logIn = findViewById(R.id.logIn);
        sAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing up..");
        dialog.setCancelable(false);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), logIn.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //register user using firebase
                emailo = emailOne.getText().toString().trim();
                String passwordo = passwordOne.getText().toString().trim();
                String confirmpasswordo = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(emailo)) {
                    emailOne.setError("Please Enter Email");
                    return;
                } else if (TextUtils.isEmpty(passwordo)) {
                    passwordOne.setError("Please Enter Password");
                    return;
                } else if (TextUtils.isEmpty(confirmpasswordo)) {
                    confirmPassword.setError("Please Confirm your password");
                    return;
                } else if (passwordo.trim().length() < 6) {
                    passwordOne.setError("Password should be atleast 6 characters");
                    return;
                } else if (!passwordo.equals(confirmpasswordo)) {
                    confirmPassword.setError("Password should match,Try again");
                    return;
                }

                dialog.show();
                sAuth.createUserWithEmailAndPassword(emailo, confirmpasswordo).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Thanks for Signing Up", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(signUp.this, profile.class);
                            intent.putExtra("source", "signup");
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }

                });
            }
        });


    }
}

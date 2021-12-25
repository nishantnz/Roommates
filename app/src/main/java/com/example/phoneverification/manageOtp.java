package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class manageOtp extends AppCompatActivity {

    EditText t2;
    Button b2;
    String phoneNumber;
    FirebaseAuth mAuth;
    String otpid;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);
        phoneNumber = getIntent().getStringExtra("mobile");
        t2 = findViewById(R.id.uName);
        b2 = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        initiateOtp();

    }

    private void initiateOtp() {
        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                otpid = s;

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (t2.getText().toString().isEmpty())
                            Toast.makeText(getApplicationContext(), "Blank Message Error", Toast.LENGTH_LONG).show();
                        else if (t2.getText().toString().length() != 6)
                            Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
                        else {
                            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, t2.getText().toString());
                            signInWithPhoneAuthCredential(credential);
                        }
                    }
                });

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(mCallbacks).build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dialog.show();
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                                dialog.dismiss();
                                Intent intent = new Intent(manageOtp.this, profile.class);
                                intent.putExtra("source", "manageotp");
                                Toast.makeText(getApplicationContext(), "Successfully Verified", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();
                            } else {
                                dialog.dismiss();
                                startActivity(new Intent(manageOtp.this, dashboard.class));
                                Toast.makeText(getApplicationContext(), "Successfully Verified", Toast.LENGTH_LONG).show();
                                finish();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Sign in Error", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

}

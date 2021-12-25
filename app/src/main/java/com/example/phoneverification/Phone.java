package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class Phone extends AppCompatActivity {

    public static String tOne;
    CountryCodePicker ccp;
    EditText t1;
    Button b1;
    ProgressDialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        t1 = findViewById(R.id.t1);
        b1 = findViewById(R.id.b1);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(t1);
        dialog1 = new ProgressDialog(this);
        dialog1.setMessage("Loading..");
        dialog1.setCancelable(false);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();
                tOne = t1.getText().toString().trim().replace(" ", "");
                if (tOne.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Please Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
                    dialog1.dismiss();
                } else {
                    Intent intent = new Intent(Phone.this, manageOtp.class);
                    intent.putExtra("mobile", ccp.getFullNumberWithPlus());
                    Toast.makeText(getApplicationContext(), "Please wait until verified do not touch anywhere", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    dialog1.dismiss();
                    finish();
                }
            }
        });
    }


}

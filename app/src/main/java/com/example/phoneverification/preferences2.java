package com.example.phoneverification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class preferences2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static String gender2 = "", occupationS = "", habitS = "", smokeS = "", locationS = "";
    public static Spinner spinner;
    static Button setPreferences, logOut;
    RadioButton prefGenderSelected, prefOccupationSelected, prefDrinkingSelected, prefSmokingSelected;
    RadioGroup prefGender, prefOccupation, prefDrinking, prefSmoking;
    RadioButton prefMale, prefFemale, prefOther, prefJob, prefStudent, prefAlcoholic, prefNonAlcoholic, prefOccasionallyDrink, prefSmoker, prefNonSmoker, prefOccasionalSmoker;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences2);
        prefGender = findViewById(R.id.prefGender);
        prefOccupation = findViewById(R.id.prefOccupation);
        prefDrinking = findViewById(R.id.prefDrinkingHabits);
        prefSmoking = findViewById(R.id.prefSmokingHabits);

        prefMale = findViewById(R.id.prefMale);
        prefFemale = findViewById(R.id.prefFemale);
        prefOther = findViewById(R.id.prefOther);
        prefJob = findViewById(R.id.prefJobPerson);
        prefStudent = findViewById(R.id.prefStudent);
        prefAlcoholic = findViewById(R.id.prefAlcoholic);
        prefNonAlcoholic = findViewById(R.id.prefNonAlcoholic);
        prefOccasionallyDrink = findViewById(R.id.prefOccasionallyDrink);
        prefSmoker = findViewById(R.id.prefSmoker);
        prefNonSmoker = findViewById(R.id.prefNonSmoker);
        prefOccasionalSmoker = findViewById(R.id.prefOccasionalSmoker);
        spinner = findViewById(R.id.spinner2);
//        logOut = findViewById(R.id.logOut);


        setPreferences = findViewById(R.id.setPref);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        setPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPrefGenderId = prefGender.getCheckedRadioButtonId();
                prefGenderSelected = findViewById(selectedPrefGenderId);

                int selectedPrefOccId = prefOccupation.getCheckedRadioButtonId();
                prefOccupationSelected = findViewById(selectedPrefOccId);

                int selectedPrefDrinkingId = prefDrinking.getCheckedRadioButtonId();
                prefDrinkingSelected = findViewById(selectedPrefDrinkingId);

                int selectedPrefSmokingId = prefSmoking.getCheckedRadioButtonId();
                prefSmokingSelected = findViewById(selectedPrefSmokingId);


                String uid = auth.getUid();
                String textGender = prefGenderSelected.getText().toString();
                String textOccupation = prefOccupationSelected.getText().toString();
                String textDrinking = prefDrinkingSelected.getText().toString();
                String textSmoking = prefSmokingSelected.getText().toString();
                String location = spinner.getSelectedItem().toString();
                String matchPref2 = prefGenderSelected.getText().toString().concat(prefOccupationSelected.getText().toString().concat(prefDrinkingSelected.getText().toString().concat(prefSmokingSelected.getText().toString().concat(location))));

                userPreferencesStore userPreferences = new userPreferencesStore(matchPref2, uid, textGender, textOccupation, textDrinking, textSmoking, location);

                database.getReference()
                        .child("users preferences")
                        .child(uid)
                        .setValue(userPreferences)
                        .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "setup done", Toast.LENGTH_SHORT).show();
                            finish();
                        });
            }
        });
//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users preferences");
        String UserID = user.getUid();

        reference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    if (snapshot.child("prefTextGender").getValue() != null) {
                        gender2 = snapshot.child("prefTextGender").getValue().toString();
                    }
                    if (snapshot.child("prefOccupation").getValue() != null) {
                        occupationS = snapshot.child("prefOccupation").getValue().toString();
                    }
                    if (snapshot.child("prefDrinking").getValue() != null) {
                        habitS = snapshot.child("prefDrinking").getValue().toString();
                    }
//                    if (snapshot.child("roomAvail").getValue() != null) {
//                        roomAvailS = snapshot.child("roomAvail").getValue().toString();
//                    }
                    if (snapshot.child("prefSmoking").getValue() != null) {
                        smokeS = snapshot.child("prefSmoking").getValue().toString();
                    }
//                    if (snapshot.child("profileImage").getValue() != null) {
//                        uriS = snapshot.child("profileImage").getValue().toString();
//                    }
//                    if (snapshot.child("details").getValue() != null) {
//                        userBioS = snapshot.child("details").getValue().toString();
//                    }

                    if (gender2.equals("Male")) {
                        prefMale.setChecked(true);
                    } else if (gender2.equals("Female")) {
                        prefFemale.setChecked(true);
                    } else {
                        prefOther.setChecked(true);
                    }

                    if (occupationS.equals("Job Person/Business Man")) {
                        prefJob.setChecked(true);
                    } else {
                        prefStudent.setChecked(true);
                    }

                    if (habitS.equals("Alcoholic")) {
                        prefAlcoholic.setChecked(true);
                    } else if (habitS.equals("Non-Alcoholic")) {
                        prefNonAlcoholic.setChecked(true);
                    } else {
                        prefOccasionallyDrink.setChecked(true);
                    }
//
                    locationS = snapshot.child("location").getValue().toString();
                    for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                        if (spinner.getItemAtPosition(i).equals(locationS)) {
                            spinner.setSelection(i);
                        }
                    }
//                    if (roomAvailS.equals("Yes")) {
//                        yes.setChecked(true);
//                    } else {
//                        no.setChecked(true);
//                    }
//
                    if (smokeS.equals("Smoker")) {
                        prefSmoker.setChecked(true);
                    } else if (smokeS.equals("Non-Smoker")) {
                        prefNonSmoker.setChecked(true);
                    } else {
                        prefOccasionalSmoker.setChecked(true);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String city = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.example.phoneverification.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Member;
import java.util.Objects;


public class profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static Uri uriSelectedImage;
    //    boolean imageUploaded = false;
    public static String name = "";
    public static String locationS = "";
    public static Spinner spinner;
    String gender2 = "";
    String occupationS = "";
    String habitS = "";
    String roomAvailS = "";
    String smokeS = "";
    String ageS = "";
    EditText uName, age, userBio, preciseLocation;
    Button setProfile;
    RadioGroup gender, occupation, habits, roomAvail, smokeGrp;
    RadioButton genderSelected, occupationSelected, habitsSelected, roomAvailSelected, smokeSelected;
    RadioButton Male, Female, Other, jobPerson, student, alcoholic, nonAlcoholic, ocassionally, yes, no, smkYes, smkNo, smkOcass;
    ActivityProfileBinding binding;
    // ActivityResultLauncher<String> mGetContent;
    FirebaseAuth auth;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog dialog, dialog2;
    Member member;
    String uriS = "";
    String userBioS = "";
    String preciseLocationS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  image = findViewById(R.id.profileImage);
        spinner = findViewById(R.id.spinner);
        preciseLocation = findViewById(R.id.preciseLocation);
        uName = findViewById(R.id.uName);
        age = findViewById(R.id.nameID);
        userBio = findViewById(R.id.userBio);
        occupation = findViewById(R.id.occupation);
        habits = findViewById(R.id.habits);
        roomAvail = findViewById(R.id.roomAvail);
        setProfile = findViewById(R.id.setProfile);
        gender = findViewById(R.id.gender);
        occupation = findViewById(R.id.occupation);
        habits = findViewById(R.id.habits);
        roomAvail = findViewById(R.id.roomAvail);
        Male = findViewById(R.id.Male);
        Female = findViewById(R.id.Female);
        Other = findViewById(R.id.Other);
        jobPerson = findViewById(R.id.jobPerson);
        student = findViewById(R.id.student);
        smokeGrp = findViewById(R.id.smokeGrp);

        alcoholic = findViewById(R.id.alcoholic);
        nonAlcoholic = findViewById(R.id.nonAlcoholic);
        ocassionally = findViewById(R.id.occasionally);

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);

        smkNo = findViewById(R.id.smkNo);
        smkYes = findViewById(R.id.smkYes);
        smkOcass = findViewById(R.id.smkOcass);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
//

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading profile please wait..");
        dialog.setCancelable(false);

//        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
//            @Override
//            public void onActivityResult(Uri result) {
//                binding.profileImage.setImageURI(result);
//            }
//        });

        binding.profileImage.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 45);
//                mGetContent.launch("image/*");
        });

        binding.setProfile.setOnClickListener(view -> {
            int selectedGenderId = gender.getCheckedRadioButtonId();
            genderSelected = findViewById(selectedGenderId);

            int selectedOccId = occupation.getCheckedRadioButtonId();
            occupationSelected = findViewById(selectedOccId);

            int selectedHabitsId = habits.getCheckedRadioButtonId();
            habitsSelected = findViewById(selectedHabitsId);

            int selectedRoomId = roomAvail.getCheckedRadioButtonId();
            roomAvailSelected = findViewById(selectedRoomId);

            int selectedSmokeId = smokeGrp.getCheckedRadioButtonId();
            smokeSelected = findViewById(selectedSmokeId);


            //obtain the entered data
            String userName = uName.getText().toString();
            String userAge = age.getText().toString();


            if (userAge.trim().equals("")) age.setText("16");
            userAge = age.getText().toString();

            int ageAsInteger = Integer.parseInt(userAge);
            String userDetails = userBio.getText().toString();
            String userPreciseLocation = preciseLocation.getText().toString();
            if (TextUtils.isEmpty(userName)) {
                uName.setError("Please enter your name");
                uName.requestFocus();
            } else if (userName.length() > 25) {
                Toast.makeText(getApplicationContext(), "User should not exceed 25 characters limit", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(userAge)) {
                age.setError("Please enter your age");
                age.requestFocus();
            } else if (TextUtils.isEmpty(userDetails)) {
                userBio.setError("You can't leave this field empty");
                userBio.requestFocus();
            } else if (userDetails.length() > 150 || userDetails.length() < 15) {
                Toast.makeText(getApplicationContext(), "Your Bio should be between 15 to 150 characters", Toast.LENGTH_SHORT).show();
            } else if (userAge.length() != 2 || ageAsInteger < 16 || ageAsInteger > 60) {
                age.setError("You are Not Eligible");
                age.requestFocus();
            } else if (userPreciseLocation.isEmpty()) {
                preciseLocation.setError("You can't leave this field empty");
                preciseLocation.requestFocus();
            } else if (userPreciseLocation.length() > 25) {
                preciseLocation.setError("User should not exceed 25 characters limit");
                preciseLocation.requestFocus();
            } else if (gender.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "please enter your gender", Toast.LENGTH_SHORT).show();
            } else if (occupation.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please select whether you are a job person or a student ", Toast.LENGTH_SHORT).show();
            } else if (habits.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please select your whether you drink", Toast.LENGTH_SHORT).show();
            } else if (roomAvail.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please select if you have a room available with you", Toast.LENGTH_SHORT).show();
            } else if (smokeGrp.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please select whether you smoke", Toast.LENGTH_SHORT).show();
            } else if (uriSelectedImage != null) {
                StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                reference.putFile(uriSelectedImage).addOnCompleteListener(task -> {
                    dialog.show();
                    if (task.isSuccessful()) {
                        reference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            String textGender = genderSelected.getText().toString();
                            String textOccupation = occupationSelected.getText().toString();
                            String textHabits = habitsSelected.getText().toString();
                            String textRoomAvail = roomAvailSelected.getText().toString();
                            String textSmoke = smokeSelected.getText().toString();
                            String userAge1 = age.getText().toString();
                            String userDetails1 = userBio.getText().toString();
                            String uid = auth.getUid();
                            String name = uName.getText().toString();
                            String email = firebaseUser.getEmail();
                            String phoneNumber = firebaseUser.getPhoneNumber();
                            String location = spinner.getSelectedItem().toString();
                            String userPreciseLocation1 = preciseLocation.getText().toString();
                            String matchPref = genderSelected.getText().toString().concat(occupationSelected.getText().toString().concat(habitsSelected.getText().toString().concat(smokeSelected.getText().toString().concat(spinner.getSelectedItem().toString()))));


                            ReadWriteUserDetails user = new ReadWriteUserDetails(matchPref, email, phoneNumber, uid, name, textGender, textOccupation, textHabits, textRoomAvail, textSmoke, imageUrl, userAge1, userDetails1, location, userPreciseLocation1);

                            database.getReference()
                                    .child("users")
                                    .child(uid)
                                    .setValue(user)
                                    .addOnSuccessListener(aVoid -> {
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "setup done", Toast.LENGTH_SHORT).show();
                                        String source = getIntent().getStringExtra("source");
                                        Intent intent = new Intent(getApplicationContext(), source.equals("signup") || source.equals("manageotp") ? welcomeToRoomates.class : dashboard.class);
                                        startActivity(intent);

                                    });
                        });
                    }
                });
//            } else if (!imageUploaded) {
//                HashMap<String, Object> map = new HashMap<>();
//                if (!name.equals(uName.getText().toString())) {
//                    map.put("name", uName.getText().toString());
//                }
//                if (!gender2.equals(genderSelected.getText().toString())) {
//                    map.put("textGender", genderSelected.getText().toString());
//                }
//                if (!habitS.equals(habitsSelected.getText().toString())) {
//                    map.put("habits", habitsSelected.getText().toString());
//                }
//                if (!occupationS.equals(occupationSelected.getText().toString())) {
//                    map.put("occupation", occupationSelected.getText().toString());
//                }
//                if (!roomAvailS.equals(roomAvailSelected.getText().toString())) {
//                    map.put("roomAvail", roomAvailSelected.getText().toString());
//                }
//                if (!smokeS.equals(smokeSelected.getText().toString())) {
//                    map.put("smokeGroup", smokeSelected.getText().toString());
//                }
//                if (!userBioS.equals(userBio.getText().toString())) {
//                    map.put("details", userBio.getText().toString());
//                }
//                if (!ageS.equals(age.getText().toString())) {
//                    map.put("age", age.getText().toString());
//                }
//                map.put("profileImage", uriS);
//                FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).updateChildren(map);
            } else {
                String textGender = genderSelected.getText().toString();
                String uid = auth.getUid();
                String name = uName.getText().toString();
                String textOccupation = occupationSelected.getText().toString();
                String textHabits = habitsSelected.getText().toString();
                String textRoomAvail = roomAvailSelected.getText().toString();
                String textSmoke = smokeSelected.getText().toString();
                String email = firebaseUser.getEmail();
                String phoneNumber = firebaseUser.getPhoneNumber();
                String location = spinner.getSelectedItem().toString();
                String userPreciseLocation1 = preciseLocation.getText().toString();
                String matchPref = genderSelected.getText().toString().concat(occupationSelected.getText().toString().concat(habitsSelected.getText().toString().concat(smokeSelected.getText().toString().concat(spinner.getSelectedItem().toString()))));
//                    String image = uriSelectedImage.toString();
                Uri imageUri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.profile);
                UploadTask uploadTask;
                StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                uploadTask = reference.putFile(imageUri);
//                    if (uriSelectedImage != null) {
//                        image = uriSelectedImage.toString();
//                        Intent intent = new Intent(getApplicationContext(), dashboard.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "select an image", Toast.LENGTH_SHORT).show();
//                    }

                ReadWriteUserDetails user = new ReadWriteUserDetails(matchPref, email, phoneNumber, uid, name, textGender, textOccupation, textHabits, textRoomAvail, textSmoke, uriS.isEmpty() ? imageUri.toString() : uriS, userAge, userDetails, location, userPreciseLocation1);

                database.getReference()
                        .child("users")
                        .child(uid)
                        .setValue(user)
                        .addOnSuccessListener(aVoid -> {
                            dialog.dismiss();
                            String source = getIntent().getStringExtra("source");
                            Intent intent = new Intent(getApplicationContext(), source.equals("signup") || source.equals("manageotp") ? welcomeToRoomates.class : dashboard.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                binding.profileImage.setImageURI(data.getData());
                uriSelectedImage = data.getData();
                StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                reference.putFile(uriSelectedImage).addOnCompleteListener(task -> {
                    reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        Glide.with(getApplicationContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(binding.profileImage);
                    });
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialog2 = new ProgressDialog(this);
        dialog2.setMessage("Setting Up..");
        dialog2.setCancelable(false);
        dialog2.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        String UserID = user.getUid();

        reference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    dialog2.dismiss();
                    if (snapshot.child("name").getValue() != null) {
                        name = snapshot.child("name").getValue().toString();
                    }
                    if (snapshot.child("textGender").getValue() != null) {
                        gender2 = snapshot.child("textGender").getValue().toString();
                    }
                    if (snapshot.child("occupation").getValue() != null) {
                        occupationS = snapshot.child("occupation").getValue().toString();
                    }
                    if (snapshot.child("habits").getValue() != null) {
                        habitS = snapshot.child("habits").getValue().toString();
                    }
                    if (snapshot.child("roomAvail").getValue() != null) {
                        roomAvailS = snapshot.child("roomAvail").getValue().toString();
                    }
                    if (snapshot.child("smokeGroup").getValue() != null) {
                        smokeS = snapshot.child("smokeGroup").getValue().toString();
                    }
                    if (snapshot.child("profileImage").getValue() != null) {
                        uriS = snapshot.child("profileImage").getValue().toString();
                    }
                    if (snapshot.child("details").getValue() != null) {
                        userBioS = snapshot.child("details").getValue().toString();
                    }
                    if (snapshot.child("preciseLocation").getValue() != null) {
                        preciseLocationS = snapshot.child("preciseLocation").getValue().toString();
                    }

                    uName.setText(Objects.requireNonNull(name));
                    if (snapshot.child("age").getValue() != null) {
                        ageS = snapshot.child("age").getValue().toString();
                        age.setText(ageS);
                    }

                    if (gender2.equals("Male")) {
                        Male.setChecked(true);
                    } else if (gender2.equals("Female")) {
                        Female.setChecked(true);
                    } else {
                        Other.setChecked(true);
                    }

                    if (occupationS.equals("Job Person/Business Man")) {
                        jobPerson.setChecked(true);
                    } else {
                        student.setChecked(true);
                    }

                    if (habitS.equals("Alcoholic")) {
                        alcoholic.setChecked(true);
                    } else if (habitS.equals("Non-Alcoholic")) {
                        nonAlcoholic.setChecked(true);
                    } else {
                        ocassionally.setChecked(true);
                    }

                    if (roomAvailS.equals("Yes")) {
                        yes.setChecked(true);
                    } else {
                        no.setChecked(true);
                    }

                    if (smokeS.equals("Smoker")) {
                        smkYes.setChecked(true);
                    } else if (smokeS.equals("Non-Smoker")) {
                        smkNo.setChecked(true);
                    } else {
                        smkOcass.setChecked(true);
                    }

                    userBio.setText(userBioS);
                    preciseLocation.setText(preciseLocationS);
                    locationS = snapshot.child("location").getValue().toString();

                    if (uriS.equals("No Image")) {
                        Uri imageUri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.profile);
                        Glide.with(getApplicationContext()).load(imageUri).into(binding.profileImage);
                    } else {
                        Glide.with(getApplicationContext()).load(Objects.requireNonNull(uriS)).into(binding.profileImage);
                    }
                    for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
                        if (spinner.getItemAtPosition(i).equals(locationS)) {
                            spinner.setSelection(i);
                        }
                    }
                }
                dialog2.dismiss();
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

package com.example.phoneverification;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.phoneverification.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.Objects;


public class profile extends AppCompatActivity {
    EditText uName, age, userBio;
    Button setProfile;
    RadioGroup gender, occupation, habits, roomAvail, smokeGrp;
    RadioButton genderSelected, occupationSelected, habitsSelected, roomAvailSelected, smokeSelected;
    RadioButton Male, Female, Other, jobPerson, student, alcoholic, nonAlcoholic, ocassionally, yes, no, smkYes, smkNo, smkOcass;

    ActivityProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri uriSelectedImage;
    ProgressDialog dialog, dialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //  image = findViewById(R.id.profileImage);
        uName = findViewById(R.id.uName);
        age = findViewById(R.id.age);
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

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading profile please wait..");
        dialog.setCancelable(false);

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 45);
            }
        });

        binding.setProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    reference.putFile(uriSelectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            dialog.show();
                            if (task.isSuccessful()) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String imageUrl = uri.toString();
                                        String textGender = genderSelected.getText().toString();
                                        String textOccupation = occupationSelected.getText().toString();
                                        String textHabits = habitsSelected.getText().toString();
                                        String textRoomAvail = roomAvailSelected.getText().toString();
                                        String textSmoke = smokeSelected.getText().toString();
                                        String userAge = age.getText().toString();
                                        String userDetails = userBio.getText().toString();
                                        String uid = auth.getUid();
                                        String name = uName.getText().toString();


                                        ReadWriteUserDetails user = new ReadWriteUserDetails(uid, name, textGender, textOccupation, textHabits, textRoomAvail, textSmoke, imageUrl, userAge, userDetails);

                                        database.getReference()
                                                .child("users")
                                                .child(uid)
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Toast.makeText(getApplicationContext(), "setup done", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                });
                            }

                        }
                    });


                } else {
                    String textGender = genderSelected.getText().toString();
                    String uid = auth.getUid();
                    String name = uName.getText().toString();
                    String textOccupation = occupationSelected.getText().toString();
                    String textHabits = habitsSelected.getText().toString();
                    String textRoomAvail = roomAvailSelected.getText().toString();
                    String textSmoke = smokeSelected.getText().toString();
                    Uri imageUri = Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.profile);
                    UploadTask uploadTask;
                    StorageReference reference = storage.getReference().child("Profiles").child(auth.getUid());
                    uploadTask = reference.putFile(imageUri);

                    ReadWriteUserDetails user = new ReadWriteUserDetails(uid, name, textGender, textOccupation, textHabits, textRoomAvail, textSmoke, "" + imageUri, userAge, userDetails);

                    database.getReference()
                            .child("users")
                            .child(uid)
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Please Select an Image", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                binding.profileImage.setImageURI(data.getData());
                uriSelectedImage = data.getData();
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
                    uName.setText(Objects.requireNonNull(snapshot.child("name").getValue()).toString());
                    age.setText(Objects.requireNonNull(snapshot.child("age").getValue()).toString());
                    String gender2 = snapshot.child("textGender").getValue().toString();
                    String occupationS = snapshot.child("occupation").getValue().toString();
                    String habitS = snapshot.child("habits").getValue().toString();
                    String roomAvailS = snapshot.child("roomAvail").getValue().toString();
                    String smokeS = snapshot.child("smokeGroup").getValue().toString();

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
                    } else if (habitS.equals("nonAlcoholic")) {
                        nonAlcoholic.setChecked(true);
                    } else {
                        ocassionally.setChecked(true);
                    }
                    if (roomAvailS.equals("Yes")) {
                        yes.setChecked(true);
                    } else {
                        no.setChecked(true);
                    }
                    if (smokeS.equals("Yes")) {
                        smkYes.setChecked(true);
                    } else if (smokeS.equals("No")) {
                        smkNo.setChecked(true);
                    } else {
                        smkOcass.setChecked(true);
                    }
                    userBio.setText(Objects.requireNonNull(snapshot.child("details").getValue()).toString());
                    Glide.with(getApplicationContext()).load(Objects.requireNonNull(snapshot.child("profileImage").getValue()).toString()).into(binding.profileImage);
                }
                dialog2.dismiss();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
